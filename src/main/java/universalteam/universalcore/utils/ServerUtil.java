package universalteam.universalcore.utils;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

import java.util.Arrays;
import java.util.List;

public class ServerUtil
{
	public static MinecraftServer server()
	{
		return FMLCommonHandler.instance().getSidedDelegate().getServer();
	}

	public static ServerConfigurationManager manager()
	{
		return server().getConfigurationManager();
	}

	public static String[] getAllPlayers()
	{
		return manager().getAllUsernames();
	}

	public static List<String> getAllPlayers_list()
	{
		return Arrays.asList(getAllPlayers());
	}

	public static int getPlayerAmount()
	{
		return manager().getCurrentPlayerCount();
	}

	public static boolean isOp(String username)
	{
		GameProfile profile = getGameProfile(username);

		if (profile == null)
			return false;

		return manager().func_152596_g(profile);
	}

	public static GameProfile getGameProfile(String username)
	{
		return server().func_152358_ax().func_152655_a(username);
	}

	public static boolean isOp(EntityPlayer player)
	{
		return isOp(player.getCommandSenderName());
	}

	public static void registerCommand(ICommand command)
	{
		((CommandHandler) server().getCommandManager()).registerCommand(command);
	}

	public static EntityPlayer getPlayerForUserName(String name)
	{
		return manager().func_152612_a(name);
	}

	public static void kickPlayer(EntityPlayer player, String text)
	{
		((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer(text);
	}

	public static void kickAllPlayers(String reason)
	{
		while (!manager().playerEntityList.isEmpty())
			((EntityPlayerMP) manager().playerEntityList.get(0)).playerNetServerHandler.kickPlayerFromServer(reason);
	}
}
