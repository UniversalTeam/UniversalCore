package universalteam.universalcore.command;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.nick.NicknameData;

import java.util.Arrays;
import java.util.List;

public class CommandNick extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "nick";
	}

	@Override
	public List getCommandAliases()
	{
		return Arrays.asList("nickname");
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "command.nick.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 1 || !(sender instanceof EntityPlayer))
			throwWrongUsage();

		EntityPlayer player = (EntityPlayer) sender;

		if (args.length == 1 && args[0].equalsIgnoreCase("reset"))
		{
			updateNick(player.getGameProfile().getName(), player.getGameProfile().getName());
			sender.addChatMessage(new ChatComponentTranslation("command.nick.reset.success"));
		}
		else if (args.length == 1)
		{
			updateNick(player.getGameProfile().getName(), args[0]);
			sender.addChatMessage(new ChatComponentTranslation("command.nick.success"));
		}
		else
			throwWrongUsage();
	}

	private void updateNick(String username, String nickname)
	{
		NicknameData.setNickname(username, nickname);

		PacketCustom packet = new PacketCustom(PacketConstants.CHANNEL, PacketConstants.UPDATE_NICK_NAME);
		packet.writeString(username);
		packet.writeString(nickname);
		packet.sendToServer();
	}

	private void throwWrongUsage()
	{
		throw new WrongUsageException("command.nick.usage", new Object[0]);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "reset") : null;
	}
}
