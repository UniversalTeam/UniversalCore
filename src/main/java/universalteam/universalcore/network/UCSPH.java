package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IServerPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import universalteam.universalcore.libs.ReferenceCore;

public class UCSPH implements IServerPacketHandler
{
	public static final String CHANNEL = ReferenceCore.MODID;

	public static final int TILE_PACKET = 1;
	public static final int UPDATE_DEV_RENDER = 2;

	@Override
	public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer netHandler)
	{
		switch (packet.getType())
		{
			case UPDATE_DEV_RENDER:
				resendDevRenderPacket(packet);
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
}
