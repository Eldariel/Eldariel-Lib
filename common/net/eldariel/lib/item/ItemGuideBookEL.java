package net.eldariel.lib.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.eldariel.lib.guidebook.GuideBook;
import net.eldariel.lib.guidebook.IGuidePage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGuideBookEL extends ItemEL {

	private static final List<ItemGuideBookEL> bookList = Lists.newArrayList();
	
	//Feel free to change any of these.
	public boolean giveBookOnFirstLogin = true;
	
	// Do not set yourself.
	@SideOnly(Side.CLIENT)
	public IGuidePage forcedPage;
	public final GuideBook book;
	public final int bookId;
	
	public ItemGuideBookEL(GuideBook book) {
		
		this(book, "guide_book");
	}
	
	public ItemGuideBookEL(GuideBook book, String name) {
		
		super(1, book.getModId(), name);
		this.book = book;
		this.bookId = bookList.size();
		
		bookList.add(this);
	}
	
	
}
