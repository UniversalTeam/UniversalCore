package universalteam.universalcore.version;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import universalteam.universalcore.libs.ReferenceCore;

import java.util.List;

public class UCVersionChecker
{
	public static List<UCVersion> modVersions = Lists.newArrayList();
	public static List<NewVersionInstance> newVersionInstances = Lists.newArrayList();

	protected static boolean ownCheck = false;
	protected static boolean checked = false;
	protected static boolean reported = false;

	public static void registerModVersion(UCVersion version)
	{
		modVersions.add(version);
	}

	public static void execute()
	{
		if (Loader.isModLoaded("VersionChecker"))
			notifyVersionCheckerkMod();
		else
			ownCheck();
	}

	protected static void notifyVersionCheckerkMod()
	{
		for (UCVersion version : modVersions)
		{
			if (check(version) && version.isRead)
				FMLInterModComms.sendRuntimeMessage(ReferenceCore.MODID, "VersionChecker", "addupdate", version.toNBT());
		}
	}

	protected static void ownCheck()
	{
		ownCheck = true;

		for (UCVersion version : modVersions)
		{
			if (!check(version))
				continue;

			List<String> changelog = Lists.newArrayList(Splitter.on('\n').split(version.changelog));
			newVersionInstances.add(new NewVersionInstance(version.modName, version.newVersion, changelog));
		}

		checked = true;
	}

	protected static boolean check(UCVersion version)
	{
		version.fromJson();

		return version.isRead && isNewVersion(version.currentVersion, version.newVersion);
	}

	protected static boolean isNewVersion(String oldVersion, String newVersion)
	{
		if (oldVersion.equals("@VERSION@")) //Check if this is in a dev environment or not.
			return false;

		String[] oldVersionTokens = oldVersion.trim().split("\\.");
		String[] newVersionTokens = newVersion.trim().split("\\.");

		for (int i = 0; i < oldVersionTokens.length; ++i)
		{
			int ver = Integer.valueOf(oldVersionTokens[i]);
			int tar = Integer.valueOf(newVersionTokens[i]);

			if (ver < tar)
				return true;
			else if (ver > tar)
				return false;
		}

		return false;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (!checked || !ownCheck || event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || reported)
			return;

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		int count = 0;

		for (NewVersionInstance version : newVersionInstances)
		{
			count++;
			player.addChatComponentMessage(new ChatComponentTranslation("universalcore.version.outdated", version.name, version.newVersion).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));

			for (String line : version.changelog)
				player.addChatComponentMessage(new ChatComponentText("-" + line.replaceAll("\\t", "")));

			if (count != newVersionInstances.size())
			player.addChatComponentMessage(new ChatComponentText("")); // add empty line to split different mods
		}

		reported = true;
	}

	public static class NewVersionInstance
	{
		public String name;
		public String newVersion;
		public List<String> changelog;

		public NewVersionInstance(String name, String newVersion, List<String> changelog)
		{
			this.name = name;
			this.newVersion = newVersion;
			this.changelog = changelog;
		}
	}
}
