package universalteam.universalcore.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalteam.universalcore.tile.TileAdvanced;

abstract public class BlockAdvanced extends Block
{
	protected BlockAdvanced(Material material)
	{
		super(material);
	}

	public abstract TileAdvanced getAdvancedTile(World world, int meta);

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return getAdvancedTile(world, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		getTile(world, x, y, z).onPlaced(entity, stack);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		return getTile(world, x, y, z).activate(player, side, player.getHeldItem());
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		getTile(world, x, y, z).click(player, player.getHeldItem());
	}

	public TileAdvanced getTile(World world, int x, int y, int z)
	{
		return (TileAdvanced) world.getTileEntity(x, y, z);
	}
}
