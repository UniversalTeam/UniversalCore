package universalcore.item;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemParts extends Item
{
	public List<Integer> itemList = new ArrayList<Integer>();
	public Map<Integer, ItemEntry> itemMap = new HashMap<Integer, ItemEntry>();
	//public IIcon[] icons = new IIcon[128];
	public Map<String, IIcon> icons = Maps.newHashMap();

	public String domain;

	public boolean hasTexture = false;

	public ItemParts(String domain, CreativeTabs creativeTab)
	{
		this.setHasSubtypes(true);
		this.setCreativeTab(creativeTab);
		this.domain = domain;
	}

	public ItemStack addItem(int meta, String name, String iconName, int rarity, boolean register, boolean hasTexture)
	{
		if (this.itemList.contains(meta))
		{
			return null;
		}

		this.itemList.add(meta);
		this.itemMap.put(meta, new ItemEntry(name, iconName, rarity));

		this.hasTexture = hasTexture;

		ItemStack stack = new ItemStack(this, 1, meta);

		if (register)
		{
			GameRegistry.registerCustomItemStack(name, stack);
		}

		return stack;
	}

	public ItemStack addItem(int meta, String name, String iconName, int rarity)
	{
		return addItem(meta, name, iconName, rarity, true, true);
	}

	public ItemStack addItem(int meta, String name, String iconName)
	{
		return addItem(meta, name, iconName, 0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage();

		if (!this.itemList.contains(meta))
		{
			return "item.invalid";
		}

		return "item." + (this.itemMap.get(meta)).name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		int meta = stack.getItemDamage();

		if (!(itemList.contains(meta)))
		{
			return EnumRarity.common;
		}

		return EnumRarity.values()[(this.itemMap.get(meta)).rarity];
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < itemList.size(); ++i)
		{
			list.add(new ItemStack(item, 1, this.itemList.get(i)));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		if (!hasTexture)
		{
			return;
		}

		for (int i = 0; i < itemList.size(); i++)
		{
			ItemEntry item = (this.itemMap.get(this.itemList.get(i)));
			icons.put(item.name, register.registerIcon(domain + ":" + item.iconName));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int damage)
	{
		if (!this.itemMap.containsKey(damage))
		{
			return null;
		}

		ItemEntry item = (this.itemMap.get(this.itemList.get(damage)));

		return icons.get(item.name);
	}

	public class ItemEntry
	{
		public String name;
		public String iconName;
		public int rarity;

		public ItemEntry(String name, String iconName, int rarity)
		{
			this.name = name;
			this.iconName = iconName;
			this.rarity = rarity;
		}
	}
}