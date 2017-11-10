package net.eldariel.lib.item;

import java.util.List;
import java.util.Map;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.registry.IRegistryObject;
import net.eldariel.lib.registry.RecipeMaker;
import net.eldariel.lib.util.LocalizationHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFoodEL extends ItemFood implements IRegistryObject, IItemEL {

	protected int subItemCount;
	protected String itemName;
	protected String modId;
	
	public ItemFoodEL(int subItemCount, String modId, String name, int amount, float saturation, boolean isWolfFood) {
		
		super(amount, saturation, isWolfFood);
		this.subItemCount = subItemCount;
		this.modId = modId;
		setHasSubtypes(subItemCount > 1);
		setUnlocalizedName(name);
	}
	
	public ItemFoodEL(int subItemCount, String modId, String name) {
		
		this(subItemCount, modId, name, 0, 0F, false);
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
			models.put(0,  new ModelResourceLocation(getFullName(), "inventory"));
		}
	}
	
	@Override
	public boolean registerModels() {
		
		// Lets ERegister handle model registration by default. Override if necessary.
		return false;
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		
		return clOnItemRightClick(worldIn, playerIn, hand);
	}
	
	protected ActionResult<ItemStack> clOnItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return clOnItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	protected EnumActionResult clOnItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		
		clGetSubItems(this, tab, subItems);
	}
	
	protected void clGetSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		
		super.getSubItems(tab, (NonNullList<ItemStack>) subItems);
	}
	
	// ==============================
	// Cross Compatibility (MC 12)
	// inspired by CompatLayer
	// ==============================
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
		
		clAddInformation(stack, world, list, flag == TooltipFlags.ADVANCED);
	}
	
	public void clAddInformation(ItemStack stack, World world, List<String> list, boolean advanced) {
		
		LocalizationHelper loc = EldarielLib.instance.getLocalizationHelperForMod(modId);
		if (loc != null) {
			String name = getNameForStack(stack);
			list.addAll(loc.getItemDescriptionLines(name));
		}
	}
}
