package universalteam.universalcore.client;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.FMLLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class DevRenderEventHandler
{
	protected final String serverLocation = "";
	protected final int timeout = 1000;

	public Map<String, String> links = Maps.newHashMap();
	public Map<String, DevRenderEntry> renderEntries = Maps.newHashMap();

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
						renderEntries.put(nick, readFile(io));
					}
					else
						FMLLog.warning("[UniversalCore] devRender.txt: Syntax error on line: " + linetracker + ": " + str);
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

	public DevRenderEntry readFile(InputStream stream) throws IOException
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
				entry.setRed(colorNode.getAsJsonObject("red").getAsFloat());
			if (colorNode.has("green"))
				entry.setRed(colorNode.getAsJsonObject("green").getAsFloat());
			if (colorNode.has("blue"))
				entry.setRed(colorNode.getAsJsonObject("blue").getAsFloat());
			if (colorNode.has("alpha"))
				entry.setRed(colorNode.getAsJsonObject("alpha").getAsFloat());
		}

		return entry;
	}

	public static class DevRenderEntry
	{
		protected float red = 1.0F;
		protected float green = 1.0F;
		protected float blue = 1.0F;
		protected float alpha = 1.0F;
		protected boolean renderUpsideDown = false;

		public void setRed(float red)
		{
			this.red = check(red);
		}

		public void setGreen(int green)
		{
			this.green = check(green);
		}

		public void setBlue(int blue)
		{
			this.blue = check(blue);
		}

		public void setAlpha(int alpha)
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

		public boolean isRenderUpsideDown()
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