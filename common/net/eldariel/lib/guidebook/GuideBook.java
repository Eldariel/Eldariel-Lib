/*
 * Inspired by the Actually Additions booklet by Ellpeck.
 */

package net.eldariel.lib.guidebook;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.eldariel.lib.EldarielLib;
import net.eldariel.lib.guidebook.entry.GuideEntry;
import net.eldariel.lib.guidebook.entry.GuideEntryAllItems;
import net.eldariel.lib.guidebook.gui.GuiGuide;
import net.eldariel.lib.util.LocalizationHelper;
import net.eldariel.lib.util.LogHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Holds all the unique data for your guide book. Extend this class and create an instance of ItemGuideBookEL to add a
 * guide book for your mod. Call your GuideBook's preInit and postInit methods during your pre/post-init phases. A 
 * single mod can have multiple guide books, in theory.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public abstract class GuideBook {

	protected final String modId;
	protected final List<IGuideEntry> entries = Lists.newArrayList();
	protected final List<IGuideChapter> chapters = Lists.newArrayList();
	protected final List<IGuidePage> pagesWithItemOrFluidData = Lists.newArrayList();
	protected ResourceLocation resourceGui;
	protected ResourceLocation resourceGadgets;
	public GuideEntry entryAllAndSearch = new GuideEntryAllItems(this, "allAndSearch").setImportant();
	public final LocalizationHelper loc;
	public int edition = -1;
	public boolean showPatreonButton = false;
	
	@SideOnly(Side.CLIENT)
	public GuiGuide lastViewedPage;
	
	public GuideBook(String modId) {
		
		this.modId = modId;
		this.showPatreonButton = modId.equals("witchery");
		this.resourceGui = new ResourceLocation(EldarielLib.instance.MOD_ID,
				"texture/gui/gui_guide.png");
		this.resourceGadgets = new ResourceLocation(EldarielLib.instance.MOD_ID,
				"textures/gui/gui_guide.png");
		LocalizationHelper locForMod = EldarielLib.instance.getLocalizationHelperForMod(modId);
		
		// Does the mod have a registered localization helper? If not, just create one.
		if (locForMod == null) {
			EldarielLib.logHelper.warning(String.format(
					"<Guide Book> Mod \"%s\" has no localization helper! A new one will be created, but not registered for use with blocks/items.", 
					modId));
			loc = new LocalizationHelper(modId).setReplaceAmpersand(true);
		} else {
			loc = locForMod;
		}
	}
	
	// ==========================================================
	// = Initializers. Call them during the appropriate phases! =
	// ==========================================================
	
	/**
	 * <b>Call during your pre-init phase!</b> Calls initEntries to register entries.
	 */
	public final void preInit() {
		
		initEntries();
	}
	
	/**
	 * <b>Call during your post-init phase!</b> Calls initChapter to populate entries, sorts everything, and spits out
	 * some stats.
	 */
	public final void postInit() {
		
		initChapters();
		
		int countChapter = 0;
		int countPage = 0;
		int countInfo = 0;
		
		for (IGuideEntry entry : entries) {
			for (IGuideChapter chapter : entry.getAllChapters()) {
				if (!chapters.contains(chapter)) {
					chapters.add(chapter);
					++countChapter;
					
					for (IGuidePage page : chapter.getAllPages()) {
						++countPage;
						
						List<ItemStack> items = Lists.newArrayList();
						page.getItemStacksForPage(items);
						List<FluidStack> fluids = Lists.newArrayList();
						page.getFluidStacksForPage(fluids);
						
						if ((items != null && !items.isEmpty()) || (fluids != null && !fluids.isEmpty())) {
							if (!pagesWithItemOrFluidData.contains(page)) {
								pagesWithItemOrFluidData.add(page);
								countInfo++;
							}
						}
					}
				}
			}
		}
		
		Collections.sort(entries, new Comparator<IGuideEntry>() {
			
			@Override
			public int compare(IGuideEntry entry1, IGuideEntry entry2) {
				
				Integer p1 = entry1.getSortingPriority();
				Integer p2 = entry2.getSortingPriority();
				return p2.compareTo(p1);
			}
		});
		Collections.sort(chapters, new Comparator<IGuideChapter>() {
			
			@Override
			public int compare(IGuideChapter chapter1, IGuideChapter chapter2) {
				
				Integer p1 = chapter1.getSortingPriority();
				Integer p2 = chapter2.getSortingPriority();
				return p2.compareTo(p1);
			}
		});
		Collections.sort(pagesWithItemOrFluidData, new Comparator<IGuidePage>() {
			
			@Override
			public int compare(IGuidePage page1, IGuidePage page2) {
				
				Integer p1 = page1.getSortingPriority();
				Integer p2 = page2.getSortingPriority();
				return p2.compareTo(p1);
			}
		});
		
		LogHelper log = EldarielLib.logHelper;
		log.info(String.format("Guide book for mod \"%s\" initialized!", modId));
		log.info("    Entries:  " + entries.size());
		log.info("    Chapters: " + countChapter);
		log.info("    Pages:    " + countPage);
		log.info("    Info:     " + countInfo);
	}
	
	// =====================================================================
	// = Override the methods to control guide book contents/behavior/etc. =
	// =====================================================================
	
	/**
	 * Initialize your entries here. DO NOT populate the entries!
	 */
	public abstract void initEntries();
	
	/**
	 * Initialize your chapters/pages here.
	 */
	public abstract void initChapters();
	
	/**
	 * Gets "quotes" to display on the main page. Each quote can optionally end with "@username" to attach a name/source
	 * to the quote. A quote is selected at random each time the main page displays.
	 * 
	 * @return Array of all possible quotes.
	 * @since 0.0.1
	 */
	public abstract String[] getQuotes();
	
	public @Nonnull String selectQuote(Random rand) {
		
		String[] quotes = getQuotes();
		if (quotes.length > 0) {
			return quotes[rand.nextInt(quotes.length)];
		}
		return "";
	}
	
	/**
	 * Gets a screen for the config button to display. Null may be returned if you do not have one.
	 * 
	 * @return The config GUI screen or null.
	 * @since 0.0.1
	 */
	@SideOnly(Side.CLIENT)
	public abstract @Nullable GuiScreen getConfigScreen(GuiScreen parent);
	
	/**
	 * Gets a screen for the achievement button to display. Null may be returned if you do not have one.
	 * 
	 * @return The achievement GUI screen or null.
	 * @since 0.0.1
	 */
	@SideOnly(Side.CLIENT)
	public abstract @Nullable GuiScreen getAchievementScreen(GuiScreen parent);
	
	/**
	 * Gets a resource location to use for the book/pages background texture. Override to use a customized texture.
	 * 
	 * @return ResourceLocation for the book texture.
	 */
	public ResourceLocation getResourceGui() {
		
		return resourceGui;
	}
	
	/**
	 * Gets a resource location to use for the GUI elements of the book. Override to use a custom texture.
	 * 
	 * @return
	 */
	public ResourceLocation getResourceGadgets() {
		
		return resourceGadgets;
	}
	
	/**
	 * Gets a String to display for the edition. You can optionally override this.
	 * 
	 * @return The displayed edition String.
	 * @since 0.0.1
	 */
	public String getEditionString(EntityPlayer player) {
		
		String str = getEditionNumberString();
		return loc.getLocalizedString("guide.eldariellib:edition", (Object) str);
	}
	
	protected String getEditionNumberString() {
		
		if (edition >= 11 && edition <= 13)
			return edition + "th";
		int mod10 = edition % 10;
		String str = edition + (mod10 == 1 ? "st" : mod10 == 2 ? "nd" : mod10 == 3 ? "rd" : "th");
		return str;
	}
	
	// ==============================================
	// = Some random methods you likely won't need. =
	// ==============================================
	
	/**
	 * @return The mod ID the guide book is associated with.
	 */
	public String getModId() {
		
		return modId;
	}
	
	/**
	 * @return A List of all chapters.
	 */
	public List<IGuideChapter> getChapters() {
		
		return chapters;
	}
	
	/**
	 * @return A list of all entries.
	 */
	public List<IGuideEntry> getEntries() {
		
		return entries;
	}
	
	/**
	 * @return A list of all pages with items/fluids displayed on them.
	 */
	public List<IGuidePage> getPageWithItemOrFluidData() {
		
		return pagesWithItemOrFluidData;
	}
	
	/**
	 * <b>Do not call!</b> GuideEntry will call this automatically in its constructor.
	 * 
	 * @param entry
	 */
	public void addEntry(IGuideEntry entry) {
		
		entries.add(entry);
	}
}
