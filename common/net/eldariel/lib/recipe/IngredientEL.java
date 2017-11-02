package net.eldariel.lib.recipe;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.eldariel.lib.util.StackHelper;

/**
 * An Ingredient wrapper for cross compatibility. By passing instances of IngredientEL into the RecipeMaker, you can add
 * ingredient-based recipes without writing version-specific code. Note that only the first stack is considered in 
 * pre-1.12 versions, so it's not a perfect solution.
 * 
 * @author Eldariel
 * 
 */
public class IngredientEL extends Ingredient {
	
	public static final IngredientEL EMPTY = new IngredientEL(new ItemStack[0]) {
		
		public boolean apply(@Nullable ItemStack p_apply_1_) {
			
			return p_apply_1_.isEmpty();
		}
	};
	
	protected IngredientEL(ItemStack...stacks) {
		
		super(stacks);
	}
	
	public static IngredientEL from(Item...items) {
		
		ItemStack[] aitemstack = new ItemStack[items.length];
		
		for (int i = 0; i < items.length; ++i) {
			aitemstack[i] = new ItemStack(items[i]);
		}
		
		return from(aitemstack);
	}
	
	public static IngredientEL from(ItemStack...stacks) {
		
		if (stacks.length > 0) {
			for (ItemStack stack : stacks) {
				if (StackHelper.isValid(stack)) {
					return new IngredientEL(stacks);
				}
			}
		}
		
		return EMPTY;
	}
}
