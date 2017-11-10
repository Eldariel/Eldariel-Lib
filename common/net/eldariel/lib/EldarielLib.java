package net.eldariel.lib;

import java.util.Map;

import com.google.common.collect.Maps;

// import net.eldariel.lib.debug.DataDump;
import net.eldariel.lib.event.EldarielLibClientEvents;
import net.eldariel.lib.event.EldarielLibCommonEvents;
import net.eldariel.lib.gui.GuiHandlerLibF;
import net.eldariel.lib.network.NetworkHandlerEL;
import net.eldariel.lib.network.internal.MessageLeftClick;
import net.eldariel.lib.util.LocalizationHelper;
import net.eldariel.lib.util.LogHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=EldarielLib.MOD_ID, name=EldarielLib.MOD_NAME, version=EldarielLib.VERSION, dependencies=EldarielLib.DEPENDENCIES, acceptedMinecraftVersions=EldarielLib.ACCEPTED_MC_VERSION)
public class EldarielLib {

	public static final String MOD_ID = "eldariellib";
	public static final String MOD_NAME = "Eldariel Lib";
	public static final String VERSION = "@VERSION@";
	public static final int BUILD_NUM = 1;
	public static final String DEPENDENCIES = "required-after:forge@[14.23.0.2491,);";
	public static final String ACCEPTED_MC_VERSION = "[1.12,1.12.2";
	
	public static NetworkHandlerEL network;
	public static LogHelper logHelper = new LogHelper(MOD_NAME, BUILD_NUM);	
	
	@Mod.Instance(MOD_ID)
	public static EldarielLib instance;
	
	private final Map<String, LocalizationHelper> locHelpers = Maps.newHashMap();
	
	public LocalizationHelper getLocalizationHelperForMod(String modId) {
		return locHelpers.get(modId.toLowerCase());
	}
	
	public void registerLocalizationHelperForMod(String modId, LocalizationHelper loc) {
		locHelpers.put(modId.toLowerCase(), loc);
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = new NetworkHandlerEL(MOD_ID);
		network.register(MessageLeftClick.class, Side.SERVER);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerLibF());
		
		MinecraftForge.EVENT_BUS.register(new EldarielLibCommonEvents());
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new EldarielLibClientEvents());
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		// DataDump.dumpEntityList();
		// DataDump.dumpEntchantments();
		// DataDump.dumpPotionEffects();
		//if ("EL_Version".equals(VERSION))
		//	DataDump.dumpRecipes();
	}
	
	public static int getMCVersion() {
		
		return 12;
	}
}
