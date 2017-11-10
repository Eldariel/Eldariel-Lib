package net.eldariel.lib.gui;

import net.eldariel.lib.guidebook.gui.GuiGuide;
import net.eldariel.lib.guidebook.gui.GuiMainPage;
import net.eldariel.lib.guidebook.misc.GuideBookUtils;
import net.eldariel.lib.item.ItemGuideBookEL;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public final class GuiHandlerLibF implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
	//Using the ID variable to index into the book list... Sneaky!
	ItemGuideBookEL item = ItemGuideBookEL.getBookById(ID);
		
	if (item != null) {
		if (item.forcedPage != null) {
			GuiGuide gui = GuideBookUtils.createBookletGuiFromPage(item.book, null, item.forcedPage);
			item.forcedPage = null;
			return gui;
		} else {
			if (item.book.lastViewedPage != null) 
				return item.book.lastViewedPage;
			return new GuiMainPage(item.book, null);
			}
		}
	return null;
	}
}
