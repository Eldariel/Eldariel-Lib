package net.eldariel.lib.creativetab;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * CreativeTabs with cross compatibility support.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public class CreativeTabEL extends CreativeTabs {

	Block block = null;
	Item item = null;
	int meta;
	
	public CreativeTabEL(String label) {
		
		super(label);
	}
	
	/**
	 * Create the creative tab with a block for the icon. Note that meta is ignored in 1.10.
	 * 
	 * @since 0.0.1
	 */
	public CreativeTabEL(String label, Block block, int meta) {
		
		super(label);
		
		if (Item.getItemFromBlock(block) == null)
			throw new NullPointerException("Item is null! Make certain it has been initialized before constructing the creative tab!");
		
		this.block = block;
		this.meta = meta;
	}
	
	/**
	 * Create the creative tab with an item for the icon. Note that meta is ignored in 1.10.
	 * 
	 * @since 0.0.1
	 */
	public CreativeTabEL(String label, Item item, int meta) {
		
		super(label);
		
		if (item == null)
			throw new NullPointerException("Item is null! Make certain it has been initialized before constructing the creative tab!");
		
		this.item = item;
		this.meta = meta;
	}
	
	protected ItemStack getStack() {
		
		if (block != null)
			return new ItemStack(Item.getItemFromBlock(block), 1, meta & 0xF);
		else if (item != null)
			return new ItemStack(item, 1, meta);
		else
			return null;
	}
	
	@Override
	public ItemStack getTabIconItem() {
		
		return getStack();
	}
}
