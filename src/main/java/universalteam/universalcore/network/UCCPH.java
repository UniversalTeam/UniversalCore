package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import universalteam.universalcore.client.DevRenderEventHandler;
import universalteam.universalcore.libs.ReferenceCore;

public class UCCPH implements PacketCustom.IClientPacketHandler
{
	public static final String CHANNEL = ReferenceCore.MODID;

	public static final int UPDATE_DEV_RENDER = 1;

	@Override
	public void handlePacket(PacketCustom packet, Minecraft mc, INetHandlerPlayClient netHandler)
	{
		switch (packet.getType())
		{
			case UPDATE_DEV_RENDER:
				DevRenderEventHandler.instance.handleUpdatePacket(packet);
		}
	}
}
