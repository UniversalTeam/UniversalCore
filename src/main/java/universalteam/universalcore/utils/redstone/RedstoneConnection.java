package universalteam.universalcore.utils.redstone;

import net.minecraft.nbt.NBTTagCompound;

public class RedstoneConnection
{
	private IRedstoneConnectionUpdatable device;
	private String id = "";
	private boolean isInput = true;
	private boolean isEnabled = false;
	private int power = 0;

	public RedstoneConnection(IRedstoneConnectionUpdatable device, String id)
	{
		this.device = device;
		this.id = id;
	}

	public RedstoneConnection(IRedstoneConnectionUpdatable device, String id, boolean isInput)
	{
		this(device, id);
		this.isInput = isInput;
	}

	public RedstoneConnection(IRedstoneConnectionUpdatable device, String id, boolean isInput, boolean isEnabled)
	{
		this(device, id, isInput);
		this.isEnabled = isEnabled;
	}

	public void setInput()
	{
		isInput = true;
		device.notifyUpdate();
	}

	public void setOutput()
	{
		isInput = false;
		device.notifyUpdate();
	}

	public void enable()
	{
		boolean was = isEnabled;
		isEnabled = true;

		if (!was)
			device.notifyUpdate();
	}

	public void disable()
	{
		boolean was = isEnabled;
		isEnabled = false;

		if (was)
			device.notifyUpdate();
	}

	public void setPower(int power)
	{
		int last = this.power;
		this.power = power;

		if (last != power)
			device.notifyUpdate();
	}

	public void setID(String id)
	{
		this.id = id;
	}

	public boolean isInput()
	{
		return isInput;
	}

	public boolean isOutput()
	{
		return !isInput;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public int getPower()
	{
		return power;
	}

	public String getID()
	{
		return id;
	}

	public NBTTagCompound getNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("IsInput", isInput);
		compound.setBoolean("IsEnabled", isEnabled);
		compound.setInteger("Power", power);
		compound.setString("ID", id);
		return compound;
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		compound.setBoolean("IsInput", isInput);
		compound.setBoolean("IsEnabled", isEnabled);
		compound.setInteger("Power", power);
		compound.setString("ID", id);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		isInput = compound.getBoolean("IsInput");
		isEnabled = compound.getBoolean("IsEnabled");
		power = compound.getInteger("Power");
		id = compound.getString("ID");
	}
}
