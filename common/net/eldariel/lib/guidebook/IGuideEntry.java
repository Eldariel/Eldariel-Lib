/*
 * Inspired by the Actually Additions booklet by Ellpeck
 */

package net.eldariel.lib.guidebook;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuideEntry {

	List<IGuideChapter> getAllChapters();
	
	String getIdentifier();
	
	@SideOnly(Side.CLIENT)
	String getLocalizedName();
	
	@SideOnly(Side.CLIENT)
	String getLocalizedNameWithFormatting();
	
	void addChapter(IGuideChapter chapter);
	
	@SideOnly(Side.CLIENT)
	List<IGuideChapter> getChaptersForDisplay(String searchBarText);
	
	int getSortingPriority();
	
	@SideOnly(Side.CLIENT)
	boolean visibleOnFrontPage();
}
