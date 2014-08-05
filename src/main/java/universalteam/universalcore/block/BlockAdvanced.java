package universalteam.universalcore.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.universalcore.client.render.block.BlockAdvancedRenderingHandler;
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
	protected String blockName;
	public IIcon[] icons = new IIcon[6];

	protected BlockAdvanced(Material material)
	{
		super(material);
		this.tileClass = getAdvancedTile();
		this.modID = getModID();
		this.blockName = blockName;
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

	protected BlockAdvanced setDefaultIcon(IIcon icon)
	{
		this.blockIcon = icon;
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

	public abstract String getBlockName();

	public abstract Class<? extends TileAdvanced> getAdvancedTile();

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return tileClass != null;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return createTile();
	}

	public TileEntity getTileForRendering()
	{
		TileEntity tile = createTile();
		tile.blockType = this;
		tile.blockMetadata = 0;
		return tile;
	}

	public TileEntity createTile()
	{
		if (tileClass == null)
			return null;

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
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon(modID + ":" + blockName);
	}

	public void setIcon(IIcon icon, ForgeDirection... dirs)
	{
		for (ForgeDirection dir : dirs)
			icons[dir.ordinal()] = icon;
	}

	public IIcon getUnmodifiedIcon(ForgeDirection dir)
	{
		if (dir != ForgeDirection.UNKNOWN)
			if (icons[dir.ordinal()] != null)
				return icons[dir.ordinal()];

		return blockIcon;
	}

	public IIcon getUnmodifiedIcon(ForgeDirection dir, IBlockAccess world, int x, int y, int z)
	{
		return getUnmodifiedIcon(dir);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		return getUnmodifiedIcon(rotateSideByMetadata(side, world.getBlockMetadata(x, y, z)), world, x, y, z);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return getUnmodifiedIcon(rotateSideByMetadata(side, meta));
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return isOpaqueCube();
	}

	@Override
	public int getRenderType()
	{
		return rotateSideIcons() ? BlockAdvancedRenderingHandler.RENDER_ID : 0;
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
				meta = RotationUtil.get6SidedOrientation(entity).ordinal();
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

	public boolean rotateSideIcons()
	{
		return false;
	}

	//Credits to OpenModsLib
	public ForgeDirection rotateSideByMetadata(int side, int metadata)
	{
		ForgeDirection rotation = ForgeDirection.getOrientation(metadata);
		ForgeDirection dir = ForgeDirection.getOrientation(side);

		switch (getBlockRotation())
		{
			case FOUR_DIRECTIONS:
			case NONE:
				switch (rotation)
				{
					case EAST:
						dir = dir.getRotation(ForgeDirection.DOWN);
						break;
					case SOUTH:
						dir = dir.getRotation(ForgeDirection.UP);
						dir = dir.getRotation(ForgeDirection.UP);
						break;
					case WEST:
						dir = dir.getRotation(ForgeDirection.UP);
						break;
					default:
						break;
				}
				return dir;
			default:
				switch (rotation)
				{
					case DOWN:
						dir = dir.getRotation(ForgeDirection.SOUTH);
						dir = dir.getRotation(ForgeDirection.SOUTH);
						break;
					case EAST:
						dir = dir.getRotation(ForgeDirection.NORTH);
						break;
					case NORTH:
						dir = dir.getRotation(ForgeDirection.WEST);
						break;
					case SOUTH:
						dir = dir.getRotation(ForgeDirection.EAST);
						break;
					case WEST:
						dir = dir.getRotation(ForgeDirection.SOUTH);
						break;
					default:
						break;

				}
		}

		return dir;
	}

	public <T> T getTile(World world, int x, int y, int z, Class<T> clazz)
	{
		TileAdvanced tile = getTile(world, x, y, z);
		return !clazz.isInstance(tile) ? null : (T)tile;
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
