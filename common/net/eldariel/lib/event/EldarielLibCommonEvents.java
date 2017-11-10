package net.eldariel.lib.event;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.item.ItemGuideBookEL;
import net.eldariel.lib.util.EntityHelper;
import net.eldariel.lib.util.PlayerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * Eldariel Lib's common event handler. Do not call any functions of this class.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public final class EldarielLibCommonEvents {

	private static final String NBT_ROOT_GUIDE_BOOKS = "eldariellib_guide_book";
	
	/**
	 * Called when a player logs in. Used to give guide books to the player.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		
		EntityPlayer player = event.player;
		NBTTagCompound forgeData = player.getEntityData();
		if (!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		
		NBTTagCompound persistedData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (!persistedData.hasKey(NBT_ROOT_GUIDE_BOOKS))
			persistedData.setTag(NBT_ROOT_GUIDE_BOOKS, new NBTTagCompound());
		
		NBTTagCompound guideData = persistedData.getCompoundTag(NBT_ROOT_GUIDE_BOOKS);
		
		int id = 0;
		ItemGuideBookEL item = ItemGuideBookEL.getBookById(id);
		while (item != null && item.giveBookOnFirstLogin) {
			if (!guideData.getBoolean(item.getFullName())) {
				guideData.setBoolean(item.getFullName(), true);
				PlayerHelper.giveItem(player, new ItemStack(item));
				EldarielLib.logHelper.info("Player has been given guide book " + item.getFullName());
			}
			
			item = ItemGuideBookEL.getBookById(++id);
		}
	}
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		if (event.phase == Phase.START) {
			// World tick pre
			EntityHelper.handleSpawns();
		}
	}
}
