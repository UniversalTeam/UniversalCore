package universalteam.universalcore.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtil
{
	public static ForgeDirection get4SidedOrientation(EntityLivingBase entity)
	{
		int dir = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

		switch (dir)
		{
			case 0:
				return ForgeDirection.SOUTH;
			case 1:
				return ForgeDirection.WEST;
			case 2:
				return ForgeDirection.NORTH;
			case 3:
				return ForgeDirection.EAST;
			default:
				return ForgeDirection.SOUTH;
		}
	}

	public static ForgeDirection get6SideOrientation(EntityLivingBase entity)
	{
		if (entity.rotationPitch < -45.5F)
			return ForgeDirection.UP;
		else if (entity.rotationPitch > 45.5F)
			return ForgeDirection.DOWN;

		return get4SidedOrientation(entity);
	}
}
