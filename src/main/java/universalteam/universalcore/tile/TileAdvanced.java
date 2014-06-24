package universalteam.universalcore.tile;

import codechicken.lib.packet.ICustomPacketTile;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

abstract public class TileAdvanced extends TileEntity implements ICustomPacketTile
{
	private boolean isRedstonePowered;
	private int ticker;

	public abstract void save(NBTTagCompound compound);

	public abstract void load(NBTTagCompound compound);

	public abstract void writeDesc(PacketCustom packet);

	public abstract void readDesc(PacketCustom packet);

	public abstract String getChannelName();

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		isRedstonePowered = compound.getBoolean("IsRedstonePowered");
		save(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		compound.setBoolean("IsRedstonePowered", isRedstonePowered);
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

	@Override
	public void updateEntity()
	{
		if (ticker == 0)
			onTileLoaded();

		super.updateEntity();
		ticker++;
	}

	protected void onTileLoaded()
	{
		onBlockNeighbourChanged();
	}

	public void onBlockNeighbourChanged()
	{
		checkRedstonePower();
	}

	public void checkRedstonePower()
	{
		boolean isIndirectlyPowered = getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

		if (isIndirectlyPowered && !getIsRedstonePowered())
			redstoneChanged(true);
		else if (getIsRedstonePowered() && !isIndirectlyPowered)
			redstoneChanged(false);
	}

	protected void redstoneChanged(boolean newValue)
	{
		isRedstonePowered = newValue;
	}

	public boolean getIsRedstonePowered()
	{
		return isRedstonePowered;
	}

	public int getTicker()
	{
		return ticker;
	}

	public List<ItemStack> getDrops()
	{
		return new ArrayList<ItemStack>();
	}

	public ForgeDirection getOrientation()
	{
		return ForgeDirection.getOrientation(getBlockMetadata());
	}
}
