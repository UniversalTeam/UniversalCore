package universalteam.universalcore.tile.implement;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IPlaceAware
{
	public void onPlace(EntityLivingBase entity, ItemStack stack);
}
