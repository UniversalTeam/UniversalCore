package universalteam.universalcore.utils.inventory;

import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.List;
import java.util.Map;

import static universalteam.universalcore.UniversalCore.logger;

public class InventorySimple implements IInventory
{
	protected ItemStack[] items;
	protected String name;
	protected int stackLimit;
	protected String customName;

	protected boolean useBlackList = false;
	protected boolean useWhiteList = false;
	protected Multimap<Integer, ItemStack> whiteList = ArrayListMultimap.create();
	protected Multimap<Integer, ItemStack> blackList = ArrayListMultimap.create();

	protected TileEntity tile;

	public InventorySimple(String name, int size, int stackLimit)
	{
		this.items = new ItemStack[size];
		this.name = name;
		this.stackLimit = stackLimit;
	}

	public InventorySimple(String name, int size)
	{
		this(name, size, 64);
	}

	public InventorySimple useWhiteList()
	{
		this.useWhiteList = true;

		if (this.useBlackList)
		{
			this.useBlackList = false;
			logger.severe("The inventory: %s tried to enable a whiteList while a blackList was already enabled, blackList will be deactivated!", this.name);
		}

		return this;
	}

	public InventorySimple useBlackList()
	{
		this.useBlackList = true;

		if (this.useWhiteList)
		{
			this.useWhiteList = false;
			logger.severe("The inventory: %s tried to enable a blackList while a whiteList was already enabled, whiteList will be deactivated!", this.name);
		}

		return this;
	}

	public InventorySimple setTile(TileEntity tile)
	{
		this.tile = tile;
		return this;
	}

	@Override
	public int getSizeInventory()
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);

		if (stack == null)
			return null;

		if (stack.stackSize <= amount)
			setInventorySlotContents(slot, null);
		else
		{
			stack = stack.splitStack(amount);
			if (stack.stackSize == 0)
				setInventorySlotContents(slot, null);
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (items[slot] != null)
		{
			ItemStack itemStack = items[slot];
			items[slot] = null;
			return itemStack;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		items[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		this.markDirty();
	}

	@Override
	public String getInventoryName()
	{
		return this.hasCustomName() ? this.getCustomName() : this.name;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return stackLimit;
	}

	@Override
	public void markDirty()
	{
		tile.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (useWhiteList)
			return whiteList.containsEntry(slot, stack);
		if (useBlackList)
			return !blackList.containsEntry(slot, stack);

		return true;
	}

	public boolean hasCustomName()
	{
		return !Strings.isNullOrEmpty(this.customName);
	}

	public String getCustomName()
	{
		return this.customName;
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	public ItemStack[] getItems()
	{
		return this.items;
	}

	public void addToWhiteList(int slot, ItemStack stack)
	{
		if (this.useWhiteList)
			whiteList.put(slot, stack);
		else
			logger.severe("Inventory: %s tried to add an item to a whiteList while the whiteList wasn't enabled, the item will not be registered!", this.name);
	}

	public void addToWhiteList(int slot, List<ItemStack> stacks)
	{
		if (this.useWhiteList)
			whiteList.putAll(slot, stacks);
		else
			logger.severe("Inventory: %s tried to add an item(s) to a whiteList while the whiteList wasn't enabled, the item will not be registered!", this.name);
	}

	public void addToBlackList(int slot, ItemStack stack)
	{
		if (this.useBlackList)
			blackList.put(slot, stack);
		else
			logger.severe("Inventory: %s tried to add an item to a blackList while the blackList wasn't enabled, the item will not be registered!", this.name);
	}

	public void addToBlackList(int slot, List<ItemStack> stacks)
	{
		if (this.useBlackList)
			blackList.putAll(slot, stacks);
		else
			logger.severe("Inventory: %s tried to add an item(s) to a blackList while the blackList wasn't enabled, the item will not be registered!", this.name);
	}
}