package net.eldariel.lib.item;

import java.util.Map;

import net.eldariel.lib.registry.IRegistryObject;
import net.eldariel.lib.registry.RecipeMaker;
import net.eldariel.lib.util.ItemHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEL extends Item implements IRegistryObject, IItemEL {

	protected int subItemCount;
	protected String itemName;
	protected String modId;
	
	public ItemEL(int subItemCount, String modId, String name) {
		
		this.subItemCount = subItemCount;
		this.modId = modId;
		setHasSubtypes(subItemCount > 1);
		setUnlocalizedName(name);
	}
	
	// ========================
	// IRegistryObjects methods
	// ========================
	
	@Override
	public void addRecipes(RecipeMaker recipes) {
		
	}
	
	@Override
	public void addOreDict() {
		
	}
	
	@Override
	public String getName() {
		
		return itemName;
	}
	
	@Override
	public String getFullName() {
		
		return modId + ":" + getName();
	}
	
	@Override
	public String getModId() {
		
		return modId;
	}
	
	@Override
	public void getModels(Map<Integer, ModelResourceLocation> models) {
		
		if (hasSubtypes) {
			for (int i = 0; i < subItemCount; ++i) {
				models.put(i, new ModelResourceLocation(getFullName() + i, "inventory"));
			}
		} else {
			models.put(0, new ModelResourceLocation(getFullName(), "inventory"));
		}
	}
	
	// ==============
	// Item overrides
	// ==============
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		return "item." + modId + ":" + getNameForStack(stack);
	}
	
	public String getNameForStack(ItemStack stack) {
		
		return itemName + (hasSubtypes ? stack.getItemDamage() : "");
	}
	
	@Override
	public Item setUnlocalizedName(String name) {
		
		this.itemName = name;
		return super.setUnlocalizedName(name);
	}
	
	// ==============================
	// Cross Compatibility (MC 10/11)
	// inspired by CompatLayer
	// ==============================
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		return clOnItemRightClick(world, player, hand);
	}
	
	protected ActionResult<ItemStack> clOnItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		return super.onItemRightClick(world, player, hand);
	}
	
	
}
