package net.eldariel.lib.util;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;

public class EntityHelper {

	public static void moveSelf(Entity entity, double x, double y, double z) {
		
		entity.move(MoverType.SELF, x, y, z);
	}
	
	/**
	 * Simulates the 1.10.2 behavior of EntityList#getEntityNameList.
	 * 
	 * @since 0.0.1
	 */
	public static List<String> getEntityNameList() {
		
		List<String> list = Lists.newArrayList();
		EntityList.getEntityNameList().forEach(res -> list.add(EntityList.getTranslationName(res)));
		return list;
	}
	
	private static Queue<Entity> entitiesToSpawn = Queues.newArrayDeque();
	
	/**
	 * Thread-safe spawning of entities. The queued entities wiil be spawned in the START (pre) phase of WorldTickEvent.
	 * 
	 * @since 0.0.1
	 */
	public static void safeSpawn(Entity entity) {
		
		entitiesToSpawn.add(entity);
	}
	
	/**
	 * Called by EldarielLibCommonEvents#onWorldTick. Calling this yourself is not recommanded.
	 * 
	 * @since 0.0.1
	 */
	public static void handleSpawns() {
		
		Entity entity;
		while ((entity = entitiesToSpawn.poll()) != null) {
			entity.world.spawnEntity(entity);
		}
	}
	
	/**
	 * Heals the player by the given amount. If cancelable is true, this calls the heal method (which fires a cancelable
	 * event). If cancelable is false, this uses setHealth instead.
	 * 
	 * @since 0.0.1
	 */
	public static void heal(EntityLivingBase entityLiving, float healAmount, boolean cancelable) {
		
		if (cancelable) {
			entityLiving.heal(healAmount);
		} else {
			entityLiving.setHealth(entityLiving.getHealth() + healAmount);
		}
	}
}
