/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.page;

import java.awt.Desktop;
import java.net.URI;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.internal.GuiGuideBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageLinkButton extends GuidePage {

	public static int nextButtonId = 23782;
	private final int buttonId;
	
	private final String link;
	
	public PageLinkButton(GuideBook book, int localizationKey, String link) {
		
		super(book, localizationKey);
		this.link = link;
		
		this.buttonId = nextButtonId;
		nextButtonId++;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initGui(GuiGuideBase gui, int startX, int startY) {
		
		super.initGui(gui, startX, startY);
		
		gui.getButtonList().add(new GuiButton(this.buttonId, startX + 125 / 2 - 50, startY + 130, 100, 20, book.loc.getLocalizedString("guide", "chapter." + this.chapter.getIdentifier() + ".button." + this.getLocalizationKey())));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawScreenPre(GuiGuideBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
		PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 5);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void actionPerformed(GuiGuideBase gui, GuiButton button) {
		
		if (button.id == this.buttonId) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI(this.link));
				} catch (Exception e) {
					EldarielLib.logHelper.warning("Couldn't open website from Link Button page!");
					e.printStackTrace();
				}
			}
		} else {
			super.actionPerformed(gui, button);
		}
	}
}
