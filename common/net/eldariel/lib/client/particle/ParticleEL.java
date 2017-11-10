package net.eldariel.lib.client.particle;

import net.eldariel.lib.client.render.BufferBuilderEL;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleEL extends Particle {

	protected ParticleEL(World worldIn, double posXIn, double posYIn, double posZIn) {
		
		super(worldIn, posXIn, posYIn, posZIn);
	}
	
	public ParticleEL(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
		BufferBuilderEL bufferBuilderE1 = BufferBuilderEL.INSTANCE.internalAcquireMC12(buffer);
		clRenderParticle(bufferBuilderE1, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}
	
	public void clRenderParticle(BufferBuilderEL buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
		super.renderParticle(buffer.internalGetBufferMC12(), entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}
}
