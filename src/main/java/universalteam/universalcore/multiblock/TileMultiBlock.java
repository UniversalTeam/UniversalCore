package universalteam.universalcore.multiblock;

import codechicken.lib.packet.ICustomPacketTile;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

abstract public class TileMultiBlock<T extends MultiBlock> extends TileEntity implements ICustomPacketTile
{
	private BlockCoord parent = new BlockCoord();
	private T multiBlock;

	public abstract void save(NBTTagCompound compound);

	public abstract void load(NBTTagCompound compound);

	public abstract void writeDesc(PacketCustom packet);

	public abstract void readDesc(PacketCustom packet);

	public abstract void onPlaced(EntityLivingBase entity, ItemStack stack);

	public abstract void activate(EntityPlayer player, int side, ItemStack stack);

	public abstract void click(EntityPlayer player, ItemStack stack);

	public abstract String getChannelName();

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		save(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		load(compound);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		PacketCustom packet = new PacketCustom(getChannelName(), 1);
		writeDesc(packet);
		return packet.toPacket();
	}

	@Override
	public void handleDescriptionPacket(PacketCustom packet)
	{
		readDesc(packet);
	}
}
