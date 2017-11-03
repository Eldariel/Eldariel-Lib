package net.eldariel.lib.guidebook.gui;

import java.util.Arrays;
import java.util.List;

import net.eldariel.lib.gui.TexturedButton;
import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.button.BookmarkButton;
import net.eldariel.lib.guidebook.internal.GuiGuideBase;
import net.eldariel.lib.guidebook.misc.GuideBookUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;


public abstract class GuiGuide extends GuiGuideBase {

	public static final int BUTTONS_PER_PAGE = 12;
	protected final BookmarkButton[] bookmarkButtons = new BookmarkButton[12];
	public GuiScreen previousScreen;
	public GuiGuideBase parentPage;
	public GuiTextField searchField;
	public GuideBook book;
	
	protected int xSize;
	protected int ySize;
	protected int guiLeft;
	protected int guiTop;
	
	private GuiButton buttonLeft;
	private GuiButton buttonRight;
	private GuiButton buttonBack;
	
	private float smallFontSize = 0.5f;
	private float mediumFontSize = 0.75f;
	private float largeFontSize = 0.8f;
	
	public GuiGuide(GuideBook book, GuiScreen previousScreen, GuiGuideBase parentPage) {
		
		this.book = book;
		this.previousScreen = previousScreen;
		this.parentPage = parentPage;
		
		this.xSize = 281;
		this.ySize = 180;
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		
		// Sometimes we don't get a book somehow? Get it from client player's hand...
		if (this.book == null) {
			this.book = GuideBookUtils.getBookFromClientPlayerHand();
		}
		
		this.book.lastViewedPage = this;
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		if (this.hasPageLeftButton()) {
			List<String> hoverText = Arrays.asList(TextFormatting.GOLD + "Previous Page", TextFormatting.ITALIC + "Or scroll up");
			this.buttonLeft = new TexturedButton(book.getResourceGadgets(), -2000, this.guiLeft - 12, this.guiTop + this.ySize -8, 18, 54, 18, 10, hoverText);
			this.buttonList.add(this.buttonLeft);
		}
		
		if (this.hasPageRightButton()) {
			List<String> hoverText = Arrays.asList(TextFormatting.GOLD + "Next Page", TextFormatting.ITALIC + "Or scroll down");
			this.buttonRight = new TexturedButton(book.getResourceGadgets(), -2001, this.guiLeft + this.xSize -6, this.guiTop + this.ySize - 8, 0, 54, 18, 10, hoverText);
			this.buttonList.add(this.buttonRight);
		}
		
		if (this.hasBackButton()) {
			
		}
	}
	
	public boolean hasPageLeftButton() {
		
		return false;
	}
	
	public void onPageLeftButtonPressed() {
		
	}
	
	public boolean hasPageRightButton() {
		
		return false;
	}
	
	public void onPageRightButtonPressed() {
		
	}
	
	public boolean hasBackButton() {
		
		return false;
	}
	
	public void onBackButtonPressed() {
		
		this.mc.displayGuiScreen(new GuiMainPage(book, this.previousScreen));
	}
}
