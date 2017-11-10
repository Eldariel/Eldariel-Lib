/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.page;

import java.util.List;
import java.util.Map;

import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.internal.GuiGuideBase;
import net.eldariel.lib.util.StackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageFurnace extends GuidePage {

	private final ItemStack input;
	private final ItemStack output;
	
	public PageFurnace(GuideBook book, int localizationKey, ItemStack output) {
		this(book, localizationKey, output, 0);
	}
	
	public PageFurnace(GuideBook book, int localizationKey, ItemStack output, int priority) {
		super(book, localizationKey, priority);
		this.output = output;
		this.input = getInputForOutput(output);
	}
	
	private static ItemStack getInputForOutput(ItemStack output) {
		
		for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
			ItemStack stack = entry.getValue();
			if (StackHelper.isValid(stack)) {
				if (stack.isItemEqual(output)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawScreenPre(GuiGuideBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
		
		gui.mc.getTextureManager().bindTexture(book.getResourceGadgets());
		GuiUtils.drawTexturedModalRect(startX + 23, startY + 10, 0, 146, 80, 26, 0);
		
		gui.renderScaledAsciiString("(" + book.loc.getLocalizedString("guide.eldariellib:furnaceRecipe") + ")", startX + 32, startY + 42, 0, false, gui.getMediumFontSize());
		
		PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 57);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initGui(GuiGuideBase gui, int startX, int startY) {
		
		super.initGui(gui, startX, startY);
		
		gui.addOrModifyItemRenderer(this.input, startX + 23 + 1, startY + 10 + 5, 1F, true);
		gui.addOrModifyItemRenderer(this.output, startX + 23 + 59, startY + 10 + 5, 1F, false);
	}
	
	@Override
	public void getItemStacksForPage(List<ItemStack> list) {
		
		super.getItemStacksForPage(list);
		
		list.add(this.output);
	}
}
