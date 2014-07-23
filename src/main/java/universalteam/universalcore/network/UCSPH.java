package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IServerPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;

import static universalteam.universalcore.network.PacketConstants.*;

public class UCSPH implements IServerPacketHandler
{
	@Override
	public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer netHandler)
	{
		switch (packet.getType())
		{
			case TILE_PACKET:
				break; //TODO: implement
			case UPDATE_DEV_RENDER:
				resendDevRenderPacket(packet);
				break;
			case UPDATE_DEV_RENDER_LINKS:
				resendDevRenderLinksPacket(packet);
				break;
		}
	}

	protected void resendDevRenderPacket(PacketCustom packet)
	{
		PacketCustom p = new PacketCustom(CHANNEL, UPDATE_DEV_RENDER);
		p.writeString(packet.readString());
		p.writeFloat(packet.readFloat());
		p.writeFloat(packet.readFloat());
		p.writeFloat(packet.readFloat());
		p.writeFloat(packet.readFloat());
		p.writeBoolean(packet.readBoolean());
		p.sendToClients();
	}

	protected void resendDevRenderLinksPacket(PacketCustom packet)
	{
		new PacketCustom(CHANNEL, UPDATE_DEV_RENDER_LINKS).sendToClients();
	}
}
