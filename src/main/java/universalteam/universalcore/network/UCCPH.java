package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import universalteam.universalcore.nick.NicknameData;

import static universalteam.universalcore.network.PacketConstants.TILE_PACKET;
import static universalteam.universalcore.network.PacketConstants.UPDATE_NICK_NAME;

public class UCCPH implements IClientPacketHandler
{
	@Override
	public void handlePacket(PacketCustom packet, Minecraft mc, INetHandlerPlayClient netHandler)
	{
		switch (packet.getType())
		{
			case TILE_PACKET:
				break; //TODO: implement
			case UPDATE_NICK_NAME:
				NicknameData.setNickname(packet.readString(), packet.readString());
				break;
		}
	}
}
