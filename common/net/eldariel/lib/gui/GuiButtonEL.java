package net.eldariel.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonEL extends GuiButton {

	public GuiButtonEL(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public GuiButtonEL(int buttonId, int x, int y, String buttonText) {
		
		super(buttonId, x, y, buttonText);
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		
		clDrawButton(minecraft, mouseX, mouseY, partialTicks);
	}
	
	public void clDrawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		
	}
}
