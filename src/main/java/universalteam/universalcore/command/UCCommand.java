package universalteam.universalcore.command;

import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import universalteam.universalcore.client.DevRenderEventHandler;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.utils.ServerUtil;

import java.util.Arrays;
import java.util.List;

public class UCCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "UniversalCore";
	}

	@Override
	public List getCommandAliases()
	{
		return Arrays.asList("universalcore", "uc", "UC");
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "[updateDevRender|rebuildLinks] <username|all>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (sender instanceof MinecraftServer || sender instanceof TileEntityCommandBlock)
			return;

		if (args.length > 0 && args.length < 3)
		{
			if (args[0].equalsIgnoreCase("updateDevRender"))
				updateDevRender(sender.getCommandSenderName(), args);
			else if (args[0].equalsIgnoreCase("rebuildLinks"))
				rebuildLinks();
		}
		else
			throw new SyntaxErrorException(getCommandUsage(sender));
	}

	protected void updateDevRender(String name, String[] args)
	{
		EntityPlayer player = ServerUtil.getPlayerForUserName(name);
		if (player == null)
			return;
		if (args.length == 1)
			DevRenderEventHandler.instance.refreshEntry(player);
		else if (args[1].equalsIgnoreCase("all"))
			DevRenderEventHandler.instance.refreshEntries();
		else
		{
			EntityPlayer target = ServerUtil.getPlayerForUserName(args[1]);
			if (target != null)
				DevRenderEventHandler.instance.refreshEntry(target);
		}
	}

	protected void rebuildLinks()
	{
		new PacketCustom(PacketConstants.CHANNEL, PacketConstants.UPDATE_DEV_RENDER_LINKS).sendToServer();
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> players = Lists.newArrayList(ServerUtil.getAllPlayers_list());
		players.add("all");
		String[] names = new String[players.size()];
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "updateDevRender", "rebuildLinks") : args.length == 2 ? getListOfStringsMatchingLastWord(args, players.toArray(names)) : null;
	}
}
