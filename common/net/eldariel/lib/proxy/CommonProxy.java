package net.eldariel.lib.proxy;

import net.eldariel.lib.registry.ERegistry;

public class CommonProxy {

	public CommonProxy() {
		
	}
	
	public void preInit(ERegistry registry) {
		
		registry.preInit();
	}
	
	public void init(ERegistry registry) {
		
		registry.init();
	}
	
	public void postInit(ERegistry registry) {
		
		registry.postInit();
	}
}
