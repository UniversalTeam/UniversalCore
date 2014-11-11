package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IServerPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;

import static universalteam.universalcore.network.PacketConstants.CHANNEL;
import static universalteam.universalcore.network.PacketConstants.TILE_PACKET;
import static universalteam.universalcore.network.PacketConstants.UPDATE_NICK_NAME;

public class UCSPH implements IServerPacketHandler
{
	@Override
	public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer netHandler)
	{
		switch (packet.getType())
		{
			case TILE_PACKET:
				break; //TODO: implement
			case UPDATE_NICK_NAME:
				resendNickNameUpdate(packet);
				break;
		}
	}

	protected void resendNickNameUpdate(PacketCustom packet)
	{
		PacketCustom p = new PacketCustom(CHANNEL, UPDATE_NICK_NAME);
		p.writeString(packet.readString());
		p.writeString(packet.readString());
		p.sendToClients();
	}
}
