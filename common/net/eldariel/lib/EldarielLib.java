package net.eldariel.lib;

import java.util.Map;

import com.google.common.collect.Maps;

import net.eldariel.lib.network.NetworkHandlerEL;
import net.eldariel.lib.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=EldarielLib.MOD_ID, name=EldarielLib.MOD_NAME, version=EldarielLib.VERSION, dependencies=EldarielLib.DEPENDENCIES, acceptedMinecraftVersions=EldarielLib.ACCEPTED_MC_VERSION)
public class EldarielLib {

	public static final String MOD_ID = "eldariellib";
	public static final String MOD_NAME = "Eldariel Lib";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.0.2491,);";
	public static final String ACCEPTED_MC_VERSION = "[1.12,1.12.2";
	
	public static NetworkHandlerEL network;
	public static LogHelper logHelper = new LogHelper(MOD_NAME);	
	
	@Mod.Instance(MOD_ID)
	public static EldarielLib instance;
	
	private final Map<String, LocalizationHelper> locHelpers = Maps.newHashMap();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
