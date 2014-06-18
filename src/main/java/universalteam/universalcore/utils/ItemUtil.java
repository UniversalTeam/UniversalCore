package universalteam.universalcore.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemUtil
{
	public static String getUniqueItemName(Item item)
	{
		return GameRegistry.findUniqueIdentifierFor(item).name;
	}

	public static String getModFromItem(Item item)
	{
		return GameRegistry.findUniqueIdentifierFor(item).modId;
	}

	public static Item getItemFromUniqueName(String name)
	{
		return name.contains(":") ? getItemFromUniqueName(name.substring(0, name.indexOf(':')), name.substring(name.indexOf(':') + 1)) : null;
	}

	public static Item getItemFromUniqueName(String modID, String name)
	{
		return GameRegistry.findItem(modID, name);
	}
}
