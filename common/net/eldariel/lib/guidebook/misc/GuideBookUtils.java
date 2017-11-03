/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook.misc;

import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.item.ItemGuideBookEL;
import net.eldariel.lib.util.StackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuideBookUtils {
	
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
