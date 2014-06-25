package universalteam.universalcore.tile.implement

import net.minecraft.inventory.IInventory
import universalteam.universalcore.utils.inventory.InventorySimple
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer

trait TInventoryTile extends IInventory
{
    var inventory: InventorySimple = createInventory

    def createInventory: InventorySimple

    def getInventory: InventorySimple = inventory

    def getSizeInventory: Int = inventory.getSizeInventory

    def getStackInSlot(slot: Int): ItemStack = inventory.getStackInSlot(slot)

    def decrStackSize(slot: Int, amount: Int): ItemStack = inventory.decrStackSize(slot, amount)

    def getStackInSlotOnClosing(slot: Int): ItemStack = inventory.getStackInSlotOnClosing(slot)

    def setInventorySlotContents(slot: Int, stack: ItemStack)
    {
        inventory.setInventorySlotContents(slot, stack)
    }

    def getInventoryName = inventory.getInventoryName

    def hasCustomInventoryName: Boolean = inventory.hasCustomName

    def getInventoryStackLimit = inventory.getInventoryStackLimit

    def isUseableByPlayer(player: EntityPlayer) = inventory.isUseableByPlayer(player)

    def openInventory()

    def closeInventory()

    def isItemValidForSlot(slot: Int, stack: ItemStack) = inventory.isItemValidForSlot(slot, stack)
}
