package net.eldariel.lib.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpecialRendererEL<T extends TileEntity> extends TileEntitySpecialRenderer<T> {

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		clRender(te, x, y, z, partialTicks, destroyStage, alpha);
	}
	
	public void clRender(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
