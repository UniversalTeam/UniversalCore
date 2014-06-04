package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import universalteam.universalcore.libs.ReferenceCore;

public class UCSPH implements PacketCustom.IServerPacketHandler
{
	public static final String CHANNEL = ReferenceCore.MODID;

	public static final int UPDATE_DEV_RENDER = 1;

	@Override
	public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer netHandler)
	{
		switch (packet.getType())
		{
			case UPDATE_DEV_RENDER:
				packet.sendToClients();
		}
	}
}
