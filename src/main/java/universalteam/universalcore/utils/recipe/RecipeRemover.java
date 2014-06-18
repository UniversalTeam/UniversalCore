package universalteam.universalcore.utils.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.ChestGenHooks;

import java.util.List;
import java.util.Map;

public class RecipeRemover
{
	public static void removeShapedRecipe(ItemStack stack)
	{
		List recipes = CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); ++i)
		{
			IRecipe tmp = (IRecipe) recipes.get(i);
			if (!(tmp instanceof ShapedRecipes))
				continue;

			ShapedRecipes recipe = (ShapedRecipes) tmp;
			ItemStack result = recipe.getRecipeOutput();

			if (ItemStack.areItemStacksEqual(stack, result))
				recipes.remove(i--);
		}
	}

	public static void removeShapelessRecipe(ItemStack stack)
	{
		List recipes = CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); ++i)
		{
			IRecipe tmp = (IRecipe) recipes.get(i);
			if (!(tmp instanceof ShapelessRecipes))
				continue;

			ShapelessRecipes recipe = (ShapelessRecipes) tmp;
			ItemStack result = recipe.getRecipeOutput();

			if (ItemStack.areItemStacksEqual(stack, result))
				recipes.remove(i--);
		}
	}

	public static void removeAnyRecipe(ItemStack stack)
	{
		List recipes = CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); ++i)
		{
			IRecipe recipe = (IRecipe) recipes.get(i);
			ItemStack result = recipe.getRecipeOutput();

			if (ItemStack.areItemStacksEqual(stack, result))
				recipes.remove(i--);
		}
	}

	public static void removeShapedRecipes(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			removeShapedRecipe(stack);
	}

	public static void removeShapeLessRecipes(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			removeShapelessRecipe(stack);
	}

	public static void removeAnyRecipes(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			removeAnyRecipe(stack);
	}

	public static void removeFurnaceRecipe(ItemStack stack)
	{
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
		recipes.remove(stack);
	}

	public static void removeFurnaceRecipes(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			removeFurnaceRecipe(stack);
	}

	public static void removeFromChests(ItemStack stack)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).removeItem(stack);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).removeItem(stack);
	}

	public static void removeFromChests(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			removeFromChests(stack);
	}

	public static void makeItemUngettable(ItemStack stack)
	{
		removeAnyRecipe(stack);
		removeFurnaceRecipe(stack);
		removeFromChests(stack);
	}

	public static void makeItemsUngettable(List<ItemStack> stacks)
	{
		for (ItemStack stack : stacks)
			makeItemUngettable(stack);
	}
}
