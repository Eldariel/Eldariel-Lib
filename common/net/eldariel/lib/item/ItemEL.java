package net.eldariel.lib.item;

import java.util.List;
import java.util.Map;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.registry.IRegistryObject;
import net.eldariel.lib.registry.RecipeMaker;
import net.eldariel.lib.util.ItemHelper;
import net.eldariel.lib.util.LocalizationHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return clOnItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	protected EnumActionResult clOnItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		
		return clOnItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	public EnumActionResult clOnItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		
		clGetSubItems(this, tab, list);
	}
	
	protected void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		
		if (!ItemHelper.isInCreativeTab(item, tab))
			return;
		
		if (hasSubtypes) {
			for (int i = 0; i <subItemCount; ++i) {
				list.add(new ItemStack(item, 1, i));
			}
		} else {
			list.add(new ItemStack(this));
		}
	}
	
	// ===========================
	// Cross Compatibility (MC 12)
	// ===========================
	
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
