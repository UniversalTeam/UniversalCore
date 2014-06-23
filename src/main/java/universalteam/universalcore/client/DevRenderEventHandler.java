package universalteam.universalcore.client;

import codechicken.lib.packet.PacketCustom;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;
import universalteam.universalcore.libs.environment.EnvironmentChecks;
import universalteam.universalcore.network.UCSPH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import static universalteam.universalcore.UniversalCore.logger;

public class DevRenderEventHandler
{
	protected final String serverLocation = "https://dl.dropboxusercontent.com/u/169269665/UniversalTeam/devRender/devRender.txt";
	protected final int timeout = 1000;

	public Map<String, String> links = Maps.newHashMap();
	public Map<String, DevRenderEntry> renderEntries = Maps.newHashMap();
	public boolean resetRender = false;

	public static DevRenderEventHandler instance;

	public DevRenderEventHandler()
	{
		buildFileDatabase();
		instance = this;
	}

	public void buildFileDatabase()
	{
		URL url;
		try
		{
			url = new URL(serverLocation);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			InputStream io = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(io));

			String str;
			int linetracker = 1;
			while ((str = br.readLine()) != null)
			{
				if (!str.startsWith("--") || !Strings.isNullOrEmpty(str))
				{
					if (str.contains(":"))
					{
						String nick = str.substring(0, str.indexOf(":"));
						String link = str.substring(str.indexOf(":") + 1);
						links.put(nick, link);
						readFile(nick, link);
					}
					else
						logger.warning("[UniversalCore] devRender.txt: Syntax error on line: %d: %S", linetracker, str);
				}
				linetracker++;
			}

			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void readFile(String name, String link) throws Exception
	{
		URL url = new URL(link);
		URLConnection con = url.openConnection();
		con.setConnectTimeout(timeout);
		con.setReadTimeout(timeout);
		InputStream io = con.getInputStream();
		renderEntries.put(name, readJson(io));
	}

	public DevRenderEntry readJson(InputStream stream) throws IOException
	{
		DevRenderEntry entry = new DevRenderEntry();
		InputStreamReader reader = new InputStreamReader(stream);
		JsonObject node = new JsonParser().parse(reader).getAsJsonObject();

		if (node.has("renderUpsideDown"))
			entry.setRenderUpsideDown(node.get("renderUpsideDown").getAsBoolean());
		if (node.has("color"))
		{
			JsonObject colorNode = node.getAsJsonObject("color");
			if (colorNode.has("red"))
				entry.setRed(colorNode.get("red").getAsFloat());
			if (colorNode.has("green"))
				entry.setGreen(colorNode.get("green").getAsFloat());
			if (colorNode.has("blue"))
				entry.setBlue(colorNode.get("blue").getAsFloat());
			if (colorNode.has("alpha"))
				entry.setAlpha(colorNode.get("alpha").getAsFloat());
		}

		return entry;
	}

	protected void updateEntry(String username)
	{
		if (!links.containsKey(username) || !renderEntries.containsKey(username))
			return;

		try
		{
			URL url = new URL(links.get(username));
			URLConnection con = url.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			InputStream io = con.getInputStream();
			renderEntries.remove(username);
			renderEntries.put(username, readJson(io));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		sendUpdatePacket(username);
	}

	public void refreshEntry(EntityPlayer player)
	{
		if (player != null)
			refreshEntry(EnumChatFormatting.getTextWithoutFormattingCodes(player.getCommandSenderName()));
	}

	public void refreshEntry(String username)
	{
		if (username == null)
		{
			for (String alt : renderEntries.keySet())
				updateEntry(alt);
			return;
		}

		updateEntry(username);
	}

	public void refreshEntries()
	{
		refreshEntry((String) null);
	}

	public void rebuildLinks()
	{
		links.clear();
		renderEntries.clear();
		buildFileDatabase();
	}

	@SubscribeEvent
	public void onPlayerRenderPre(RenderLivingEvent.Pre event)
	{
		if (Loader.isModLoaded("shadermod") || EnvironmentChecks.hasOptifine)
			return;

		String username = EnumChatFormatting.getTextWithoutFormattingCodes(event.entity.getCommandSenderName());

		if (renderEntries.containsKey(username))
		{
			DevRenderEntry entry = renderEntries.get(username);
			GL11.glColor4f(entry.red, entry.green, entry.blue, entry.alpha);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (entry.renderUpsideDown())
			{
				GL11.glTranslatef(0.0F, event.entity.height + 0.1F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			}
			this.resetRender = true;
		}
	}

	@SubscribeEvent
	public void onPlayerRenderPost(RenderLivingEvent.Post event)
	{
		if (this.resetRender)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	protected void sendUpdatePacket(String username)
	{
		PacketCustom packet = new PacketCustom(UCSPH.CHANNEL, UCSPH.UPDATE_DEV_RENDER);
		DevRenderEntry entry = renderEntries.get(username);
		packet.writeString(username);
		packet.writeFloat(entry.getRed());
		packet.writeFloat(entry.getGreen());
		packet.writeFloat(entry.getBlue());
		packet.writeFloat(entry.getAlpha());
		packet.writeBoolean(entry.renderUpsideDown());
		packet.sendToServer();
	}

	public void handleUpdatePacket(PacketCustom packet)
	{
		String username = packet.readString();
		DevRenderEntry entry = renderEntries.get(username);
		entry.setRed(packet.readFloat());
		entry.setGreen(packet.readFloat());
		entry.setBlue(packet.readFloat());
		entry.setAlpha(packet.readFloat());
		entry.setRenderUpsideDown(packet.readBoolean());
		renderEntries.remove(username);
		renderEntries.put(username, entry);
	}

	public static class DevRenderEntry
	{
		float red = 1.0F;
		float green = 1.0F;
		float blue = 1.0F;
		float alpha = 1.0F;
		boolean renderUpsideDown = false;

		public void setRed(float red)
		{
			this.red = check(red);
		}

		public void setGreen(float green)
		{
			this.green = check(green);
		}

		public void setBlue(float blue)
		{
			this.blue = check(blue);
		}

		public void setAlpha(float alpha)
		{
			this.alpha = check(alpha);
		}

		public void setRenderUpsideDown(boolean renderUpsideDown)
		{
			this.renderUpsideDown = renderUpsideDown;
		}

		public float getRed()
		{
			return red;
		}

		public float getGreen()
		{
			return green;
		}

		public float getBlue()
		{
			return blue;
		}

		public float getAlpha()
		{
			return alpha;
		}

		public boolean renderUpsideDown()
		{
			return renderUpsideDown;
		}

		protected float check(float number)
		{
			if (number > 1.0F)
				number = 1.0F;
			if (number < 0.0F)
				number = 0.0F;

			return number;
		}
	}
}
