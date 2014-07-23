package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import universalteam.universalcore.client.DevRenderEventHandler;

import static universalteam.universalcore.network.PacketConstants.*;

public class UCCPH implements IClientPacketHandler
{
	@Override
	public void handlePacket(PacketCustom packet, Minecraft mc, INetHandlerPlayClient netHandler)
	{
		switch (packet.getType())
		{
			case TILE_PACKET:
				break; //TODO: implement
			case UPDATE_DEV_RENDER:
				DevRenderEventHandler.instance.handleUpdatePacket(packet);
				break;
			case UPDATE_DEV_RENDER_LINKS:
				DevRenderEventHandler.instance.rebuildLinks();
				break;
		}
	}
}
