package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import universalteam.universalcore.client.DevRenderEventHandler;
import universalteam.universalcore.libs.ReferenceCore;

public class UCCPH implements IClientPacketHandler
{
	public static final String CHANNEL = ReferenceCore.MODID;

	public static final int TILE_PACKET = 1;
	public static final int UPDATE_DEV_RENDER = 2;
	public static final int UPDATE_DEV_RENDER_LINKS = 3;

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
