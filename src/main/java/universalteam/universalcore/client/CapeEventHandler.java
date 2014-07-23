package universalteam.universalcore.client;

import com.google.common.base.Strings;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraftforge.client.event.RenderPlayerEvent;
import universalteam.universalcore.libs.environment.EnvironmentChecks;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public class CapeEventHandler
{
	private final String serverLocation = "https://dl.dropboxusercontent.com/u/169269665/UniversalTeam/Capes/capes.txt";
	private final int timeout = 1000;

	private static final Graphics TEST_GRAPHICS = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB).getGraphics();
	private HashMap<String, String> cloaks = new HashMap<String, String>();
	private ArrayList<AbstractClientPlayer> capePlayers = new ArrayList<AbstractClientPlayer>();

	public static CapeEventHandler instance;

	public CapeEventHandler()
	{
		buildCloakURLDatabase();
		instance = this;
	}

	@SubscribeEvent
	public void onPreRenderSpecials(RenderPlayerEvent.Specials.Pre event)
	{
		if (Loader.isModLoaded("shadersmod") || EnvironmentChecks.hasOptifine)
			return;

		if (event.entityPlayer instanceof AbstractClientPlayer)
		{
			AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) event.entityPlayer;

			if (!capePlayers.contains(abstractClientPlayer))
			{
				String cloakURL = cloaks.get(event.entityPlayer.getDisplayName());

				if (cloakURL == null)
				{
					return;
				}

				capePlayers.add(abstractClientPlayer);

				ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class, abstractClientPlayer.getTextureCape(), false, new String[]{"textureUploaded", "field_110559_g"});
				new Thread(new CloakThread(abstractClientPlayer, cloakURL)).start();
				event.renderCape = true;
			}
		}
	}

	public void buildCloakURLDatabase()
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
						new Thread(new CloakPreload(link)).start();
						cloaks.put(nick, link);
					}
					else
						FMLLog.warning("[UniversalCore] capes.txt: Syntax error on line: " + linetracker + ": " + str);
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

	private class CloakThread implements Runnable
	{

		AbstractClientPlayer abstractClientPlayer;
		String cloakURL;

		public CloakThread(AbstractClientPlayer player, String cloak)
		{
			abstractClientPlayer = player;
			cloakURL = cloak;
		}

		@Override
		public void run()
		{
			try
			{
				Image cape = new ImageIcon(new URL(cloakURL)).getImage();
				BufferedImage bo = new BufferedImage(cape.getWidth(null), cape.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				bo.getGraphics().drawImage(cape, 0, 0, null);
				ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class, abstractClientPlayer.getTextureCape(), bo, new String[]{"bufferedImage", "field_110560_d"});
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private class CloakPreload implements Runnable
	{
		String cloakURL;

		public CloakPreload(String link)
		{
			cloakURL = link;
		}

		@Override
		public void run()
		{
			try
			{
				TEST_GRAPHICS.drawImage(new ImageIcon(new URL(cloakURL)).getImage(), 0, 0, null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void refreshCapes()
	{
		cloaks.clear();
		capePlayers.clear();
		buildCloakURLDatabase();
	}
}
