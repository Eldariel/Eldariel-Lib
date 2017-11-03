/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.eldariel.lib.gui.TexturedButton;
import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.IGuideEntry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Main page GUI for the guide book system.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
@SideOnly(Side.CLIENT)
public class GuiMainPage extends GuiGuide {

	private @Nullable TexturedButton achievementButton;
	private @Nullable TexturedButton configButton;
	private @Nullable TexturedButton patreonButton;
	
	private GuiButton tutorialButton;
	private boolean showTutorial;
	
	private String bookletName;
	private String bookletEdition;
	
	private List<String> quote;
	private String quoteGuy;
	
	public GuiMainPage(GuideBook book, GuiScreen previousScreen) {
		
		super(book, previousScreen, null);
	}
	
	private List<IGuideEntry> getDisplayedEntries() {
		
		List<IGuideEntry> displayed = new ArrayList<IGuideEntry>();
		
		for (IGuideEntry entry : book.getEntries()) {
			if (entry.visibleOnFrontPage()) {
				displayed.add(entry);
			}
		}
		
		return displayed;
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		
		this.bookletName = "guide." + book.getModId() + ":manualName.1";
		
		// Select a random quote here, if there is any available.
		String usedQuote = book.selectQuote(mc.world.rand);
		if (!usedQuote.isEmpty()) {
			String[] quoteSplit = usedQuote.split("@");
			if (quoteSplit.length > 0) {
				this.quote = this.fontRenderer.listFormattedStringToWidth(quoteSplit[0], 120);
			}
			if (quoteSplit.length >1) {
				this.quoteGuy = quoteSplit[1];
			} else {
				this.quoteGuy = null;
			}
		}
		
		// TODO: Player name edition substitutions?
		String playerName = this.mc.player.getName();
		if (playerName.equalsIgnoreCase("derp")) {
			this.bookletEdition = "derp edition";
		} else {
			this.bookletEdition = book.getEditionString(this.mc.player);
		}
		
		// Buttons
		int xPos = this.guiLeft - 4;
		
		// Config button?
		List<String> configText = new ArrayList<String>();
		configText.add(TextFormatting.GOLD + book.loc.getLocalizedString("guide", "configButton.name"));
		if (book.getConfigScreen(this) != null) {
			xPos += 20;
			this.configButton = new TexturedButton(book.getResourceGadgets(), -388, xPos,
					this.guiTop + this.ySize -30, 188, 14, 16, 16, configText);
			this.buttonList.add(this.configButton);
		}
		
		// Achievements button?
		
	}
}
