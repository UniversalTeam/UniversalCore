package universalteam.universalcore.nick;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.utils.ServerUtil;

public class NicknameHandler
{
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		String joinPlayerName = event.player.getGameProfile().getName();

		if (!NicknameData.nicknames.containsKey(joinPlayerName))
			NicknameData.nicknames.put(joinPlayerName, joinPlayerName);

		NicknameData.updateNickname(joinPlayerName);

		for (String name : ServerUtil.getAllPlayers_list())
		{
			EntityPlayer player = ServerUtil.getPlayerForUserName(name);

			if (player == null)
				return;

			String username = player.getCommandSenderName();
			String nickname = NicknameData.getNickname(username);

			if (!nickname.equals(username))
				sendNickPacket(username, nickname, event.player);
		}

		sendNickPacketToAll(joinPlayerName, NicknameData.getNickname(joinPlayerName));
	}

	@SubscribeEvent
	public void onNameFormat(net.minecraftforge.event.entity.player.PlayerEvent.NameFormat event)
	{
		event.displayname = NicknameData.getNickname(event.entityPlayer.getCommandSenderName());
	}

	private void sendNickPacket(String username, String nickname, EntityPlayer player)
	{
		PacketCustom packet = new PacketCustom(PacketConstants.CHANNEL, PacketConstants.UPDATE_NICK_NAME);
		packet.writeString(username);
		packet.writeString(nickname);
		packet.sendToPlayer(player);
	}

	private void sendNickPacketToAll(String username, String nickname)
	{
		PacketCustom packet = new PacketCustom(PacketConstants.CHANNEL, PacketConstants.UPDATE_NICK_NAME);
		packet.writeString(username);
		packet.writeString(nickname);
		packet.sendToClients();
	}
}
