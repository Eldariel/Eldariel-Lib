package net.eldariel.lib.recipe;

import java.util.List;

import javax.annotation.Nonnull;

import net.eldariel.lib.util.StackHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public interface IRecipeEL extends IRecipe {

	@Override
	default boolean matches(InventoryCrafting inv, World world) {
		
		return StackHelper.isValid(getCraftingResult(inv));
	}
	
	// @Override
	// public default int getRecipeSize() {
	//
	// 	// Prioritize over all normal recipes.
	//	return 10;
	// }
	
	@Override
	public default @Nonnull ItemStack getRecipeOutput() {
		
		return StackHelper.empty();
	}
	
	@Override
	public default NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		
		return (NonNullList<ItemStack>) getRemainingItemsCompat(inv);
	}
	
	@Nonnull
	default List<ItemStack> getRemainingItemsCompat(InventoryCrafting inv) {
		
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (StackHelper.isValid(stack)) {
				stack = StackHelper.shrink(stack, 1);
				inv.setInventorySlotContents(i, stack);
			}
		}
		return NonNullList.create();
	}
}
