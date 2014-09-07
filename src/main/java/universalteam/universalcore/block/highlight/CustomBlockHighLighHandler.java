package universalteam.universalcore.block.highlight;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;

public class CustomBlockHighLighHandler
{
	@SubscribeEvent
	public void onBlockHighLight(DrawBlockHighlightEvent event)
	{
		if (event.target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
			return;
		int x = event.target.blockX;
		int y = event.target.blockY;
		int z = event.target.blockZ;
		Block block = event.player.worldObj.getBlock(x, y, z);

		if (!(block instanceof IHasCustomHighlight))
			return;

		AxisAlignedBB[] aabbs = ((IHasCustomHighlight) block).getBoxes(event.player.worldObj, x, y, z, event.player);
		Vec3 pos = event.player.getPosition(event.partialTicks);

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);

		double exp = ((IHasCustomHighlight) block).getExpansion();

		for (AxisAlignedBB aabb : aabbs)
			RenderGlobal.drawOutlinedBoundingBox(aabb.copy().expand(exp, exp, exp).offset(x, y, z).offset(-pos.xCoord, -pos.yCoord, -pos.zCoord), -1);

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		event.setCanceled(true);
	}
}
