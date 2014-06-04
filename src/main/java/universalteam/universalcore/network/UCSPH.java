package universalteam.universalcore.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import universalteam.universalcore.libs.ReferenceCore;

public class UCSPH implements PacketCustom.IServerPacketHandler
{
	public static final String channel = ReferenceCore.MODID;

	@Override
	public void handlePacket(PacketCustom packetCustom, EntityPlayerMP entityPlayerMP, INetHandlerPlayServer iNetHandlerPlayServer)
	{

	}
}
