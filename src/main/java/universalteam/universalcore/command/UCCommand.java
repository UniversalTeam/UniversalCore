package universalteam.universalcore.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
		return "<updateCapes|updateDevRender>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{

	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		return super.addTabCompletionOptions(sender, args);
	}
}
