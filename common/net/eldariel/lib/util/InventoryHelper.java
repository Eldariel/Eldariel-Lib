package net.eldariel.lib.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHelper {

	public static boolean matchesOreDict(ItemStack stack, String oreName) {
		
		for (ItemStack stackInDict : OreDictionary.getOres(oreName)) {
			if (stackInDict.isItemEqual(stack)) {
				return true;
			}
		}
		return false;
	}
}
