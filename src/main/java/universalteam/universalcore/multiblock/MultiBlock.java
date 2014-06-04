package universalteam.universalcore.multiblock;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.CuboidCoord;
import net.minecraft.nbt.NBTTagCompound;

abstract public class MultiBlock
{
	public CuboidCoord bounds;

	public void save(NBTTagCompound compound)
	{
		bounds.set(compound.getIntArray("bounds"));
	}

	public void load(NBTTagCompound compound)
	{
		compound.setIntArray("bounds", bounds.intArray());
	}

	public void writeDesc(PacketCustom packet)
	{
		packet.writeCoord(bounds.min);
		packet.writeCoord(bounds.max);
	}

	public void readDesc(PacketCustom packet)
	{
		bounds.set(packet.readCoord(), packet.readCoord());
	}

	public abstract void update();
}