package net.eldariel.lib.event;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.item.IItemEL;
import net.eldariel.lib.network.internal.MessageLeftClick;
import net.eldariel.lib.util.StackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Eldariel Lib's client event handler. Do not call any functions of this class.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public class EldarielLibClientEvents {

	@SubscribeEvent
	public void onLeftClickEmpty(LeftClickEmpty event) {
		
		ItemStack stack = event.getItemStack();
		if (StackHelper.isValid(stack) && stack.getItem() instanceof IItemEL) {
			// Client-Side call
			ActionResult<ItemStack> result = ((IItemEL) stack.getItem()).onItemLeftClickEL(event.getWorld(), event.getEntityPlayer(), event.getHand());
			// Server-Side call
			if (result.getType() == EnumActionResult.SUCCESS) {
				EldarielLib.network.wrapper.sendToServer(new MessageLeftClick(MessageLeftClick.Type.EMPTY, event.getHand()));
			}
		}
	}
	
	@SubscribeEvent
	public void onLeftClickBlock(LeftClickBlock event) {
		
		ItemStack stack = event.getItemStack();
		if (StackHelper.isValid(stack) && stack.getItem() instanceof IItemEL) {
			// Client-Side call
			ActionResult<ItemStack> result = ((IItemEL) stack.getItem()).onItemLeftClickBlockEL(event.getWorld(), event.getEntityPlayer(), event.getHand());
			//Server-side call
			if (result.getType() == EnumActionResult.SUCCESS) {
				EldarielLib.network.wrapper.sendToServer(new MessageLeftClick(MessageLeftClick.Type.BLOCK, event.getHand()));
			}
		}
	}
}
