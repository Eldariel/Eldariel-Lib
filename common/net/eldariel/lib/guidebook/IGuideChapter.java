/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuideChapter {

	IGuidePage[] getAllPages();
	
	@SideOnly(Side.CLIENT)
	String getLocalizedName();
	
	@SideOnly(Side.CLIENT)
	String getLocalizedNameWithFormatting();
	
	IGuideEntry getEntry();
	
	ItemStack getDisplayItemStack();
	
	String getIdentifier();
	
	int getPageIndex(IGuidePage page);
	
	int getSortingPriority();
}
