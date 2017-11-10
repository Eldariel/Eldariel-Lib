package net.eldariel.lib.guidebook.page;



import java.util.List;

import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.IGuidePage;
import net.eldariel.lib.guidebook.gui.GuiGuide;
import net.eldariel.lib.guidebook.gui.GuiPage;
import net.eldariel.lib.guidebook.misc.GuideBookUtils;
import net.eldariel.lib.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDisplay {

	public final int x;
	public final int y;
	public final float scale;
	private final GuiPage gui;
	private final IGuidePage page;
	public ItemStack stack;
	
	public ItemDisplay(GuideBook book, GuiPage gui, int x, int y, float scale, ItemStack stack, boolean shouldTryTransfer) {
		
		this.gui = gui;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.stack = stack;
		this.page = shouldTryTransfer ? GuideBookUtils.findFirstPageForStack(book, stack) : null;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawPre(GuideBook book) {
		
		AssetUtil.renderStackToGui(this.stack, this.x, this.y, this.scale);
	}
	
	@SideOnly(Side.CLIENT)
	public void drawPost(GuideBook book, int mouseX, int mouseY) {
		
		if (this.isHovered(book, mouseX, mouseY)) {
			Minecraft mc = this.gui.mc;
			boolean flagBefore = mc.fontRenderer.getUnicodeFlag();
			mc.fontRenderer.setUnicodeFlag(false);
			
			ITooltipFlag tooltipFlag = mc.gameSettings.advancedItemTooltips ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL;
			List<String> list = this.stack.getTooltip(mc.player, tooltipFlag);
			int maxWidth = 0;
			
			for (int k = 0; k < list.size(); ++k) {
				maxWidth = Math.max(mc.fontRenderer.getStringWidth(list.get(k)), maxWidth);
				if (k == 0) {
					list.set(k, this.stack.getRarity().rarityColor + list.get(k));
				} else {
					list.set(k, TextFormatting.GRAY + list.get(k));
				}
			}
			
			if (this.page != null && this.page != this.gui.pages[0] && this.page != this.gui.pages[1]) {
				list.add(TextFormatting.GOLD + book.loc.getLocalizedString("guide.eldariellib:clickToSeeRecipe"));
			}
			
			ScaledResolution res = new ScaledResolution(mc);
			int posX = mouseX;
			
			if (posX + maxWidth > res.getScaledWidth() - 15)
				posX -= maxWidth + 20;
			
			GuiUtils.drawHoveringText(list, posX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
			
			mc.fontRenderer.setUnicodeFlag(flagBefore);
		}
	}
	
	public void onMousePress(GuideBook book, int button, int mouseX, int mouseY) {
		
		if (button == 0 && this.isHovered(book, mouseX, mouseY)) {
			if (this.page != null && this.page != this.gui.pages[0] && this.page != this.gui.pages[1]) {
				this.gui.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				
				GuiGuide gui = GuideBookUtils.createPageGui(book, this.gui.previousScreen, this.gui, this.page);
				this.gui.mc.displayGuiScreen(gui);
			}
		}
	}
	
	public boolean isHovered(GuideBook book, int mouseX, int mouseY) {
		
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + 16 * this.scale && mouseY < this.y + 16 * this.scale;
	}
}
