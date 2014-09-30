package universalteam.universalcore.command;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;
import universalteam.universalcore.nick.NicknameData;

import java.util.List;
import java.util.Set;

public class CommandRealName extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "realname";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "command.realname.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length == 1)
		{
			if (NicknameData.nicknames.containsValue(args[0]))
				sender.addChatMessage(new ChatComponentTranslation("command.realname.success", args[0], NicknameData.getUsername(args[0])));
			else
				sender.addChatMessage(new ChatComponentTranslation("command.realname.fail"));
		}
		else
			throw new WrongUsageException("command.realname.usage", new Object[0]);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		Set<String> usernames = NicknameData.nicknames.values();
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, usernames.toArray(new String[usernames.size()])) : null;
	}
}
