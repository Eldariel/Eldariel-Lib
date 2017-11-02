package net.eldariel.lib.gui;

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
	}
}
