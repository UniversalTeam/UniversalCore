package universalteam.universalcore.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.universalcore.tile.TileAdvanced;
import universalteam.universalcore.tile.implement.IActivateAware;
import universalteam.universalcore.tile.implement.IBreakAware;
import universalteam.universalcore.tile.implement.IClickAware;
import universalteam.universalcore.tile.implement.IHasDrops;
import universalteam.universalcore.tile.implement.IHasGUI;
import universalteam.universalcore.tile.implement.INeighbourChangeAware;
import universalteam.universalcore.tile.implement.IPlaceAware;
import universalteam.universalcore.utils.RotationUtil;

import java.util.ArrayList;
import java.util.List;

abstract public class BlockAdvanced extends Block
{
	protected BlockRotation blockRotation = BlockRotation.NONE;
	protected ForgeDirection inventoryRenderDirection = ForgeDirection.WEST;
	protected Class<? extends TileEntity> tileClass = null;
	protected String modID;
	protected int guiID = -1;

	protected BlockAdvanced(Material material)
	{
		super(material);
		this.tileClass = getAdvancedTile();
		this.modID = getModID();
	}

	protected BlockAdvanced setBlockRotation(BlockRotation blockRotation)
	{
		this.blockRotation = blockRotation;
		return this;
	}

	protected BlockAdvanced setInventoryRenderDirection(ForgeDirection inventoryRenderDirection)
	{
		this.inventoryRenderDirection = inventoryRenderDirection;
		return this;
	}

	public BlockRotation getBlockRotation()
	{
		return blockRotation;
	}

	public ForgeDirection getInventoryRenderDirection()
	{
		return inventoryRenderDirection;
	}

	public abstract String getModID();

	public abstract Object getModInstace();

	public abstract Class<? extends TileAdvanced> getAdvancedTile();

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return tileClass != null;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		try
		{
			return tileClass.newInstance();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta;

		switch (blockRotation)
		{
			case FOUR_DIRECTIONS:
				meta = RotationUtil.get4SidedOrientation(entity).ordinal();
				break;
			case SIX_DIRECTIONS:
				meta = RotationUtil.get6SideOrientation(entity).ordinal();
				break;
			default:
				meta = 0;
		}

		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof IPlaceAware)
			((IPlaceAware) tile).onPlace(entity, stack);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof IHasGUI && ((IHasGUI) tile).canPlayerOpenGUI(player, side, hitX, hitY, hitZ) && !world.isRemote)
			openGUI(player, world, x, y, z);

		if (tile instanceof IActivateAware)
			return ((IActivateAware) tile).onActivate(player, side, hitX, hitY, hitZ);

		return false;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof IClickAware)
			((IClickAware) tile).onClicked(player);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof INeighbourChangeAware)
			((INeighbourChangeAware) tile).onNeigbourChange(block);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof IBreakAware)
			((IBreakAware) tile).onBreak();

		world.removeTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!player.capabilities.isCreativeMode)
		{
			TileAdvanced tile = getTile(world, x, y, z);
			List<ItemStack> drops = Lists.newArrayList();
			for (ItemStack drop : drops)
				dropBlockAsItem(world, x, y, z, drop);
		}

		return super.removedByPlayer(world, player, x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> drops = Lists.newArrayList();
		drops.addAll(super.getDrops(world, x, y, z, meta, fortune));
		TileAdvanced tile = getTile(world, x, y, z);

		if (tile instanceof IHasDrops)
			((IHasDrops) tile).addDrops(drops, fortune);

		return drops;
	}

	public void getTileDrops(TileAdvanced tile, List<ItemStack> drops, int fortune)
	{
		if (tile instanceof IHasDrops)
			((IHasDrops) tile).addDrops(drops, fortune);
	}

	public void openGUI(EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiID == -1)
			return;

		player.openGui(getModInstace(), guiID, world, x, y, z);
	}

	public boolean rotateSideIcons(int meta)
	{
		return false;
	}

	public TileAdvanced getTile(World world, int x, int y, int z)
	{
		return (TileAdvanced) world.getTileEntity(x, y, z);
	}

	public enum BlockRotation
	{
		NONE(),
		FOUR_DIRECTIONS(ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST),
		SIX_DIRECTIONS(ForgeDirection.VALID_DIRECTIONS);

		private BlockRotation(ForgeDirection... directions)
		{
			this.directions = directions;
		}

		private ForgeDirection[] directions;
	}
}
