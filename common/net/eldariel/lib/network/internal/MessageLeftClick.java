package net.eldariel.lib.network.internal;

import net.eldariel.lib.item.IItemEL;
import net.eldariel.lib.network.MessageEL;
import net.eldariel.lib.util.StackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageLeftClick extends MessageEL {
	public int type;
	public boolean mainHand;
	
	public static enum Type {
		EMPTY, BLOCK;
	}	
		
	public MessageLeftClick() {
		type = 0;
		mainHand = true;
	}
	
	public MessageLeftClick(Type type, EnumHand hand) {
		this.type = type.ordinal();
		this.mainHand = hand == EnumHand.MAIN_HAND;
	}
	
	@Override
	public IMessage handleMessage(MessageContext context) {
		if (context.side != Side.SERVER) {
			return null;
		}
		EnumHand hand = mainHand ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		EntityPlayer player = context.getServerHandler().player;
		ItemStack heldItem = player.getHeldItem(hand);
		
		if (StackHelper.isValid(heldItem) && heldItem.getItem() instanceof IItemEL) {
			IItemEL item = (IItemEL) heldItem.getItem();
			if (type == Type.EMPTY.ordinal()) {
				item.onItemLeftClickEL(player.world, player, hand);
			} else {
				item.onItemLeftClickBlockEL(player.world, player, hand);
			}
		}
		
		return null;
	}
}
