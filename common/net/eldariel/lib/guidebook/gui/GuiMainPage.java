/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.gui.TexturedButton;
import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.IGuideEntry;
import net.eldariel.lib.guidebook.button.EntryButton;
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
		List<String> achievementText = new ArrayList<String>();
		achievementText.add(TextFormatting.GOLD + book.loc.getLocalizedString("guide", "achievementButton.name"));
		if (book.getAchievementScreen(this) != null) {
			xPos += 20;
			this.achievementButton = new TexturedButton(book.getResourceGadgets(), -389, xPos,
					this.guiTop + this.ySize - 30, 204, 14, 16, 16, achievementText);
			this.buttonList.add(this.achievementButton);
		}
		
		// Patreon button
		if (book.showPatreonButton) {
			xPos += 20;
			List<String> patreonText = new ArrayList<String>();
			patreonText.add(TextFormatting.GOLD + "Support the mod on Patreon!");
			patreonText.add("(Opens a link in your web browser)");
			this.patreonButton = new TexturedButton(book.getResourceGadgets(), -340, xPos,
					this.guiTop + this.ySize - 30, 16, 172, 16, 16, patreonText);
			this.buttonList.add(this.patreonButton);
		}
		
		for (int i = 0; i < BUTTONS_PER_PAGE; i++) {
			List<IGuideEntry> displayed = getDisplayedEntries();
			if (displayed.size() > i) {
				IGuideEntry entry = displayed.get(i);
				this.buttonList.add(new EntryButton(this, i, this.guiLeft + 156, this.guiTop + 11 + i * 13,
						115, 10, "- " + entry.getLocalizedNameWithFormatting(), null));
			} else {
				return;
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if (button instanceof EntryButton) {
			List<IGuideEntry> displayed = getDisplayedEntries();
			if (displayed.size() > button.id) {
				IGuideEntry entry = displayed.get(button.id);
				if (entry != null) {
					this.mc.displayGuiScreen(new GuiEntry(book, this.previousScreen, this, entry, 0, "", false));
				}
			}
		} else if (button == this.achievementButton) {
			GuiScreen achievements = book.getAchievementScreen(this);
			if (achievements != null)
				this.mc.displayGuiScreen(achievements);
		} else if (button == this.configButton) {
			GuiScreen config = book.getConfigScreen(this);
			if (config != null)
				this.mc.displayGuiScreen(config);
		} else if (this.showTutorial && button == this.tutorialButton) {
			if (this.hasBookmarkButtons()) {
				if (!isShiftKeyDown()) {
					// for (int i = 0; i < InitBooklet.chaptersIntroduction.length; i++) {
					// this.bookmarkButtons[i].assignedPage = InitBooklet.chaptersIntroduction[i]
					// .getAllPages()[0];
					// }
				}
				this.showTutorial = false;
				this.tutorialButton.visible = false;
				
				this.configButton.visible = true;
				this.achievementButton.visible = true;
				
				// FIXME?
				// PlayerSave data = PlayerData.getDataFromPlayer(this.mc.player);
				// data.didBookTutorial = true;
				// PacketHandlerHelper.sendPlayerDataToServer(false, 1);
			}
		} else if (button == this.patreonButton) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.patreon.com/Test"));
				} catch (Exception ex) {
					EldarielLib.logHelper.warning("Could not open web page. \n" + ex);
				}
			}
		} else {
			super.actionPerformed(button);
		}
	}
	
	@Override
	public void drawScreenPre(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreenPre(mouseX, mouseY, partialTicks);
		
		String strg = TextFormatting.DARK_GREEN + book.loc.getLocalizedString(this.bookletName);
		this.fontRenderer.drawString(strg, this.guiLeft + 72 - this.fontRenderer.getStringWidth(strg) / 2 - 3, this.guiTop + 19, 0);
		strg = TextFormatting.DARK_GREEN + book.loc.getLocalizedString("guide", "manualName.2");
		this.fontRenderer.drawString(strg, this.guiLeft + 72 - this.fontRenderer.getStringWidth(strg) / 2 - 3, this.guiTop + 19 + this.fontRenderer.FONT_HEIGHT, 0);
		
		strg = TextFormatting.GOLD + TextFormatting.ITALIC.toString() + this.bookletEdition;
		this.fontRenderer.drawString(strg, this.guiLeft + 72 - this.fontRenderer.getStringWidth(strg) / 2 - 3, this.guiTop + 40, 0);
		
		if (this.showTutorial) {
			String text = TextFormatting.BLUE + "It looks like this is the first time you are using this manual. \nIf you click the button below, some useful bookmarks will be stored at the bottom of the GUI. You should definitely check them out to get started with " + book.getModId() + "! \nIf you don't want this, shift-click the button.";
			this.renderSplitScaledAsciiString(text, this.guiLeft + 11, this.guiTop + 55, 0, false, this.getMediumFontSize(), 120);
		} else if (this.quote != null && !this.quote.isEmpty()) {
			int quoteSize = this.quote.size();
			for (int i  = 0; i < quoteSize; i++) {
				this.renderScaledAsciiString(TextFormatting.ITALIC + this.quote.get(i), this.guiLeft + 25, this.guiTop + 90 + (i * 8), 0, false, this.getMediumFontSize());
			}
			if (this.quoteGuy != null)
				this.renderScaledAsciiString("- " + this.quoteGuy, this.guiLeft + 60, this.guiTop + 93 + quoteSize * 8, 0, false, this.getLargeFontSize());
		}
	}
	
	@Override
	public void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer) {
		
	}
}
