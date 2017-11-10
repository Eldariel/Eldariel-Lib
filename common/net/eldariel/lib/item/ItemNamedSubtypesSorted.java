package net.eldariel.lib.item;

import java.util.Arrays;
import java.util.List;

import net.eldariel.lib.util.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Same as ItemNamedSubtypes, but the order the subitems are listed can be changed with a sorted names list. You should 
 * not change the order of the names list! Always add new items to the end of names, then add them in the desired 
 * position in sortedNames.
 * 
 * @author Eldariel
 *
 */
public class ItemNamedSubtypesSorted extends ItemNamedSubtypes {

	public final String[] sortedNames;
	
	public ItemNamedSubtypesSorted(String[] names, String[] sortedNames, String modId, String baseName) {
		
		super(names, modId, baseName);
		this.sortedNames = sortedNames;
		checkArrays();
	}
	
	protected void checkArrays() {
		
		String[] array1 = (String[]) Arrays.copyOf(names, names.length);
		String[] array2 = (String[]) Arrays.copyOf(sortedNames, sortedNames.length);
		Arrays.sort(array1);
		Arrays.sort(array2);
		
		for (int i = 0; i < array1.length; ++i) {
			if (!array1[i].equals(array2[i])) {
				throw new IllegalArgumentException("names and sortedNames don't contain the same elements!");
			}
		}
	}
	
	@Override
	public void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		
		if (!ItemHelper.isInCreativeTab(item, tab))
			return;
		
		for (String name : sortedNames) {
			list.add(getStack(name));
		}
	}
}
