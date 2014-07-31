package universalteam.universalcore.version;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import universalteam.universalcore.libs.ReferenceCore;

import java.util.List;

public class UCVersionChecker
{
	public static List<UCVersion> modVersions = Lists.newArrayList();

	protected static boolean ownCheck = false;
	protected static boolean checked = false;

	public static void registerModVersion(UCVersion version)
	{
		modVersions.add(version);
	}

	public static void execute()
	{
		if (Loader.isModLoaded("VersionChecker"))
			notifyVersionCheckerkMod();
		else
			ownCheck = true;
	}

	protected static void notifyVersionCheckerkMod()
	{
		for (UCVersion version : modVersions)
			FMLInterModComms.sendRuntimeMessage(ReferenceCore.MODID, "VersionChecker", "addupdate", version.fromJson().toNBT());
	}

	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event)
	{
		if (!(event.entity instanceof EntityPlayer) || checked || !ownCheck)
			return;

		EntityPlayer player = (EntityPlayer) event.entity;

		//TODO IMPLEMENT
	}
}
