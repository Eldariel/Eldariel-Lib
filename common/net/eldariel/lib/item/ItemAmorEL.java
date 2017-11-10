package net.eldariel.lib.item;

import java.util.List;
import java.util.Map;

import net.eldariel.lib.registry.IRegistryObject;
import net.eldariel.lib.registry.RecipeMaker;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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

public class ItemAmorEL extends ItemArmor implements IRegistryObject, IItemEL {

	 protected String itemName;
	 protected String modId;
	 
	 public ItemAmorEL(String modId, String itemNam, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		 
		 super(materialIn, renderIndexIn, equipmentSlotIn);
		 this.modId = modId;
		 this.itemName = itemName;
		 setUnlocalizedName(itemName);
	 }
	 
	 // =======================
	 // IRegistryObject methods
	 // =======================
	 
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
		 
		 models.put(0, new ModelResourceLocation(getFullName(), "inventory"));
	 }
	 
	 @Override
	 public boolean registerModels() {
		 
		 //Let ERegistry handle model registration by default. Override if necessary.
		 return false;
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
	 
	 @SideOnly(Side.CLIENT)
	 @Override
	 public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		 
		 clGetSubItems(this, tab, subItems);
	 }
	 
	 @SideOnly(Side.CLIENT)
	 protected void clGetSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		 
		 super.getSubItems(tab, (NonNullList<ItemStack>) subItems);
	 }
	 
	 // ===========================
	 // Cross Compatibility (MC 12)
	 // ===========================
	 
	 @Override
	 public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
		 
		 clAddInformation(stack, world, list, flag == TooltipFlags.ADVANCED);
	 }
	 
	 public void clAddInformation(ItemStack stack, World world, List<String> list, boolean advanced) {
		 
	 }
}
