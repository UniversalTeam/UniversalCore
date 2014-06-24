package universalteam.universalcore.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.universalcore.block.BlockAdvanced;
import universalteam.universalcore.utils.render.RenderUtil;

public class BlockAdvancedRenderingHandler implements ISimpleBlockRenderingHandler
{
	public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		BlockAdvanced blockAdvanced = (block instanceof BlockAdvanced) ? (BlockAdvanced) block : null;
		if (blockAdvanced == null)
			return;

		ForgeDirection dir = blockAdvanced.getInventoryRenderDirection();
		RenderUtil.rotateIconsForRender(blockAdvanced, dir, renderer);
		renderer.renderBlockAsItem(blockAdvanced, 1, 1.0F);
		RenderUtil.resetFaces(renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		BlockAdvanced blockAdvanced = (block instanceof BlockAdvanced) ? (BlockAdvanced) block : null;

		if (blockAdvanced == null)
			return false;

		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		RenderUtil.rotateIconsForRender(blockAdvanced, dir, renderer);
		renderer.renderStandardBlock(block, x, y, z);
		RenderUtil.resetFaces(renderer);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return RENDER_ID;
	}
}
