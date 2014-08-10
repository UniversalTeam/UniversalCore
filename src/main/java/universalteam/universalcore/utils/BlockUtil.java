package universalteam.universalcore.utils;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockUtil
{
	public static String getUniqueBlockName(Block block)
	{
		return GameRegistry.findUniqueIdentifierFor(block).name;
	}

	public static String getModFromBlock(Block block)
	{
		return GameRegistry.findUniqueIdentifierFor(block).modId;
	}

	public static Block getBlockForUniqueName(String name)
	{
		return GameData.getBlockRegistry().getObject(name);
	}

	public static Block getBlockForUniqueName(String modID, String name)
	{
		return getBlockForUniqueName(String.format("%s:%s", modID, name));
	}

	public static Block getBlockFromItem(Item item)
	{
		return Block.getBlockFromItem(item);
	}

	public static Block getBlockFromItemStack(ItemStack stack)
	{
		return getBlockFromItem(stack.getItem());
	}
}
