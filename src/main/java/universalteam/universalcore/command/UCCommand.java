package universalteam.universalcore.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import universalteam.universalcore.client.DevRenderEventHandler;
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
		return "[updateCapes|updateDevRender] <username|all>";
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
			else if (args[0].equalsIgnoreCase("updateCapes"))
				updateCapes(sender.getCommandSenderName(), args);
		}
		else
			throw new SyntaxErrorException(getCommandUsage(sender));
	}

	protected void updateCapes(String name, String[] args)
	{
		//TODO: NO-OP
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

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		List<String> players = ServerUtil.getAllPlayers_list();
		players.add("all");
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "updateCapes", "updateDevRender") : args.length == 2 ? getListOfStringsMatchingLastWord(args, (String[]) players.toArray()) : null;
	}
}
