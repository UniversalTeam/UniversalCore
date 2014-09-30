package universalteam.universalcore.utils.math;

import codechicken.lib.math.MathHelper;

public class MathUtil
{
	public static final double PI = 3.14159265358979323846;
	public static final double PHI = 1.618033988749894;
	public static final double TO_DEGREE = 57.29577951308232;
	public static final double TO_RADIAN = 0.017453292519943;
	public static final double SQRT_2 = 1.414213562373095;
	public static final double SQRT_3 = 1.732050807568877;

	public static double sin(double d)
	{
		return MathHelper.sin(d);
	}

	public static double cos(double d)
	{
		return MathHelper.cos(d);
	}

	public static int compare(double a, double b)
	{
		return MathHelper.compare(a, b);
	}

	public static int compare(int a, int b)
	{
		return MathHelper.compare(a, b);
	}

	public static int roundAway(double d)
	{
		return MathHelper.roundAway(d);
	}

	public static boolean between(double a, double x, double b)
	{
		return MathHelper.between(a, x, b);
	}

	public static double clip(double value, double min, double max)
	{
		return MathHelper.clip(value, min, max);
	}

	public static float abs(float f)
	{
		return net.minecraft.util.MathHelper.abs(f);
	}

	public static int abs(int i)
	{
		return net.minecraft.util.MathHelper.abs_int(i);
	}
}
