package universalelectricity.core.asm.template.tile;

import java.util.WeakHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.CompatibilityType;
import universalelectricity.api.electricity.IVoltageInput;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * @author Calclavia
 * 
 */
public class StaticTileForwarder
{
	/**
	 * Adds electricity to an block. Returns the quantity of electricity that was accepted. This
	 * should always return 0 if the block cannot be externally charged.
	 * 
	 * @param from Orientation the electricity is sent in from.
	 * @param receive Maximum amount of electricity (joules) to be sent into the block.
	 * @param doReceive If false, the charge will only be simulated.
	 * @return Amount of energy that was accepted by the block.
	 */
	public static long onReceiveEnergy(IEnergyInterface handler, ForgeDirection from, long receive, boolean doReceive)
	{
		return handler.onReceiveEnergy(from, receive, doReceive);
	}

	/**
	 * Adds electricity to an block. Returns the ElectricityPack, the electricity provided. This
	 * should always return null if the block cannot be externally discharged.
	 * 
	 * @param from Orientation the electricity is requested from.
	 * @param energy Maximum amount of energy to be sent into the block.
	 * @param doReceive If false, the charge will only be simulated.
	 * @return Amount of energy that was given out by the block.
	 */
	public static long onExtractEnergy(IEnergyInterface handler, ForgeDirection from, long request, boolean doProvide)
	{
		return handler.onExtractEnergy(from, request, doProvide);
	}

	/**
	 * Gets the voltage of this TileEntity.
	 * 
	 * @return The amount of volts. E.g 120v or 240v
	 */
	public static long getVoltage(IVoltageInput handler, ForgeDirection direction)
	{
		return handler.getVoltageInput(direction);
	}

	public static long getElectricityStored(IEnergyContainer handler, ForgeDirection from)
	{
		return handler.getEnergy(from);
	}

	public static long getMaxElectricity(IEnergyContainer handler, ForgeDirection from)
	{
		return handler.getEnergyCapacity(from);
	}

	public static boolean canConnect(IEnergyInterface handler, ForgeDirection from, Object source)
	{
		return handler.canConnect(from, source);
	}

	public static void validateTile(Object obj)
	{
		if (obj instanceof TileEntity)
		{
			TileEntity tileEntity = (TileEntity) obj;
			if (tileEntity.isInvalid())
			{
				try
				{
					ReflectionHelper.setPrivateValue(TileEntity.class, tileEntity, false, "tileEntityInvalid", "field_70328_o");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void invalidateTile(Object obj)
	{
		if (obj instanceof TileEntity)
		{
			TileEntity tileEntity = (TileEntity) obj;

			if (!tileEntity.isInvalid())
			{
				try
				{
					ReflectionHelper.setPrivateValue(TileEntity.class, tileEntity, true, "tileEntityInvalid", "field_70328_o");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static final WeakHashMap<IEnergyInterface, PowerHandler> powerProviderMap = new WeakHashMap<IEnergyInterface, PowerHandler>();

	public static PowerReceiver getPowerReceiver(IEnergyInterface handler, ForgeDirection side)
	{
		if (!powerProviderMap.containsKey(handler))
		{
			PowerHandler powerHandler = new PowerHandler((IPowerReceptor) handler, Type.MACHINE);

			if (handler instanceof IEnergyContainer)
			{
				float capacity = (float) (((IEnergyContainer) handler).getEnergyCapacity(ForgeDirection.UNKNOWN) * CompatibilityType.BUILDCRAFT.ratio);
				powerHandler.configure(0, capacity, 1, capacity);
			}

			powerHandler.configurePowerPerdition(0, 0);
			powerProviderMap.put(handler, powerHandler);
		}

		if (handler instanceof TileEntity)
		{
			TileEntity incomingTile = new Vector3((TileEntity) handler).translate(side).getTileEntity(((TileEntity) handler).getWorldObj());

			if (handler.canConnect(side, incomingTile))
				return powerProviderMap.get(handler).getPowerReceiver();
		}
		else
		{
			if (handler.canConnect(side, null))
				return powerProviderMap.get(handler).getPowerReceiver();
		}

		return null;
	}

	public static void doWork(IEnergyInterface handler, PowerHandler workProvider)
	{
		long energyToInject = (long) (workProvider.useEnergy(0, workProvider.getEnergyStored(), false) * CompatibilityType.BUILDCRAFT.reciprocal_ratio);
		long energyUsed = handler.onReceiveEnergy(ForgeDirection.UNKNOWN, energyToInject, true);
		workProvider.useEnergy(0, (float) (energyUsed * CompatibilityType.BUILDCRAFT.ratio), true);
	}

	public static World getWorld(IEnergyInterface handler)
	{
		if (handler instanceof TileEntity)
		{
			return ((TileEntity) handler).getWorldObj();
		}

		return null;
	}

}
