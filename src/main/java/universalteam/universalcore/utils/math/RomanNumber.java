package universalteam.universalcore.utils.math;

public class RomanNumber
{
	protected static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
	protected static final String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

	public static String toRomanNumber(int number)
	{
		StringBuilder builder = new StringBuilder();
		int remaining = number;

		for (int i = 0; i < values.length; ++i)
			remaining = appendRomanNumber(remaining, values[i], symbols[i], builder);

		return builder.toString();
	}

	protected static int appendRomanNumber(int remaining, int value, String symbol, StringBuilder builder)
	{
		while (remaining >= value)
		{
			builder.append(symbol);
			remaining -= value;
		}

		return remaining;
	}
}
