package universalteam.universalcore.tile.implement;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IHasDrops
{
	public List<ItemStack> addDrops(List<ItemStack> drops, int fortune);
}
