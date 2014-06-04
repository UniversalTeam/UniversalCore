package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import universalteam.universalcore.libs.ReferenceCore;

public class UCCPH implements PacketCustom.IClientPacketHandler
{
	public static final String channel = ReferenceCore.MODID;

	@Override
	public void handlePacket(PacketCustom packetCustom, Minecraft minecraft, INetHandlerPlayClient iNetHandlerPlayClient)
	{

	}
}
