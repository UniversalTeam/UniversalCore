package universalteam.universalcore.tweaks.block

import java.util.Random

import codechicken.lib.vec.BlockCoord
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.world.World

class BlockSpreadingMossyCobblestone extends Block(Material.rock) with TSpreadMoss
{
    setTickRandomly(true)
    setStepSound(Block.soundTypeStone)
    setBlockName("stoneMoss")
    setBlockTextureName("cobblestone_mossy")
    setCreativeTab(CreativeTabs.tabBlock)
    setHardness(2.0F)
    setResistance(10.0F)

    override def updateTick(world: World, x: Int, y: Int, z: Int, rand: Random)
    {
        spreadMoss(world, x, y, z, rand)
    }

    override def registerBlockIcons(register: IIconRegister)
    {
        super.registerBlockIcons(register)
        Blocks.mossy_cobblestone.registerBlockIcons(register)
    }
}

class BlockSpreadingStoneBrick extends Block(Material.rock) with TSpreadMoss
{
    setTickRandomly(true)
    setStepSound(Block.soundTypeStone)
    setBlockName("stonebricksmooth")
    setBlockTextureName("stonebrick")
    setCreativeTab(CreativeTabs.tabBlock)
    setHardness(1.5F)
    setResistance(10.0F)

    override def updateTick(world: World, x: Int, y: Int, z: Int, rand: Random)
    {
        world.getBlockMetadata(x, y, z) match
        {
            case 0 => crackBlock(world, x, y, z, rand)
            case 1 => spreadMoss(world, x, y, z, rand)
            case _ =>
        }
    }

    private def crackBlock(world: World, x: Int, y: Int, z: Int, rand: Random)
    {
        val bc = new BlockCoord(x, y, z)
        if (isWarm(world, bc) && isWet(world, bc) && rand.nextInt(3) == 0)
            world.setBlock(x, y, z, Block.getBlockFromName("stonebrick"), 2, 3)
    }

    override def registerBlockIcons(register: IIconRegister)
    {
        Blocks.stonebrick.registerBlockIcons(register)
    }

    @SideOnly(Side.CLIENT)
    override def getIcon(side: Int, meta: Int) =
    {
        Blocks.stonebrick.getIcon(side, meta)
    }
}

sealed trait TSpreadMoss
{
    protected def spreadMoss(world: World, x: Int, y: Int, z: Int, rand: Random)
    {
        if (!world.isAirBlock(x, y + 1, z) || world.canBlockSeeTheSky(x, y, z))
            return

        for (i <- 0 until 6)
        {
            val bc = new BlockCoord(x, y, z).offset(i)
            val block = world.getBlock(x, y, z)
            val meta = world.getBlockMetadata(x, y, z)

            val cobble = Block.getBlockFromName("cobblestone")
            val mossyCobble = Block.getBlockFromName("mossy_cobblestone")
            val brick = Block.getBlockFromName("stonebrick")

            if (world.isAirBlock(bc.x, bc.y, bc.z) && !world.canBlockSeeTheSky(bc.x, bc.y, bc.z))
            {
                if (block == cobble)
                    if (isWet(world, bc) && rand.nextInt(3) == 0)
                        world.setBlock(bc.x, bc.y, bc.z, mossyCobble, 0, 3)

                if (block == brick)
                    if (isWet(world, bc) && rand.nextInt(3) == 0)
                        world.setBlock(bc.x, bc.y, bc.z, brick, 1, 3)
            }
        }
    }

    protected def isWarm(world: World, bc: BlockCoord) = check(world, bc, bc => bc == Blocks.lava || bc == Blocks.flowing_lava)

    protected def isWet(world: World, bc: BlockCoord) = check(world, bc, bc => bc == Blocks.water || bc == Blocks.flowing_water)

    private def check(world: World, bc: BlockCoord, block: Block => Boolean): Boolean =
    {
        for (i <- 0 until 6)
        {
            val offset = bc.copy.offset(i)
            val block2 = world.getBlock(offset.x, offset.y, offset.z)
            if (block(block2))
                return true
        }
        false
    }
}
