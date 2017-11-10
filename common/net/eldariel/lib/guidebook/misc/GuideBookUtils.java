/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.misc;

import java.util.ArrayList;
import java.util.List;

import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.IGuideChapter;
import net.eldariel.lib.guidebook.IGuidePage;
import net.eldariel.lib.guidebook.gui.GuiEntry;
import net.eldariel.lib.guidebook.gui.GuiMainPage;
import net.eldariel.lib.guidebook.gui.GuiPage;
import net.eldariel.lib.guidebook.internal.GuiGuideBase;
import net.eldariel.lib.item.ItemGuideBookEL;
import net.eldariel.lib.util.StackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuideBookUtils {
	
	public static IGuidePage findFirstPageForStack(GuideBook book, ItemStack stack) {
		
		for (IGuidePage page : book.getPageWithItemOrFluidData()) {
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			page.getItemStacksForPage(stacks);
			if (stack != null && !stack.isEmpty()) {
				for (ItemStack pageStack : stacks) {
					if (pageStack.isItemEqual(stack)) {
						return page;
					}
				}
			}
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static GuiPage createBookletGuiFromPage(GuideBook book, GuiScreen previousScreen, IGuidePage page) {
		
		GuiMainPage mainPage = new GuiMainPage(book, previousScreen);
		
		IGuideChapter chapter = page.getChapter();
		GuiEntry entry = new GuiEntry(book, previousScreen, mainPage, chapter.getEntry(), chapter, "", false);
		
		return createPageGui(book, previousScreen, entry, page);
	}
	
	@SideOnly(Side.CLIENT)
	public static GuiPage createPageGui(GuideBook book, GuiScreen previousScreen, GuiGuideBase parentPage, IGuidePage page) {
		
		IGuideChapter chapter = page.getChapter();
		
		IGuidePage[] allPages = chapter.getAllPages();
		int pageIndex = chapter.getPageIndex(page);
		IGuidePage page1;
		IGuidePage page2;
		
		if (page.shouldBeOnLeftSide()) {
			page1 = page;
			page2 = pageIndex >= allPages.length - 1 ? null : allPages[pageIndex + 1];
		} else {
			page1 = pageIndex <= 0 ? null : allPages[pageIndex - 1];
			page2 = page;
		}
		
		return new GuiPage(book, previousScreen, parentPage, page1, page2);
	}
	
	public static IGuidePage getBookletPageById(GuideBook book, String id) {
		
		if (id != null) {
			for (IGuideChapter chapter : book.getChapters()) {
				for (IGuidePage page : chapter.getAllPages()) {
					if (id.equals(page.getIdentifier())) {
						return page;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Sometimes the GUIs don't get the GuideBook they are supposed  to be initialized with them. In those cases, we can get the
	 * book from the client player's held stack.
	 */
	@SideOnly(Side.CLIENT)
	public static GuideBook getBookFromClientPlayerHand() {
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player != null) {
			ItemStack stack = player.getHeldItemMainhand();
			if (StackHelper.isEmpty(stack))
				stack = player.getHeldItemOffhand();
			
			if (stack.getItem() instanceof ItemGuideBookEL) {
				ItemGuideBookEL itemBook = (ItemGuideBookEL) stack.getItem();
				return itemBook.book;
			}
		}
		
		return null;
	}

}
