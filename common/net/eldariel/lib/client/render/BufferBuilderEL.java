package net.eldariel.lib.client.render;

import java.nio.ByteBuffer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;

/**
 * Version-independent wrapper for BufferBuilder (1.12) and VertexBuffer (1.10/1.11).
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public class BufferBuilderEL {

	public static final BufferBuilderEL INSTANCE = new BufferBuilderEL();
	
	private BufferBuilder buffer;
	
	public BufferBuilderEL acquireBuffer(Tessellator tess) {
		
		buffer = tess.getBuffer();
		return this;
	}
	
	/** Minecraft 1.12 only! Internal use only, do not call! */
	public BufferBuilderEL internalAcquireMC12(BufferBuilder bufferBuilder) {
		
		buffer = bufferBuilder;
		return this;
	}
	
	/** Minecraft 1.12 only! Internal use only, do not call! */
	public BufferBuilder internalGetBufferMC12() {
		
		return buffer;
	}
	
	public void sortVertexData(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
		
		buffer.sortVertexData(p_181674_1_, p_181674_2_, p_181674_3_);
	}
	
	//	public BufferBuilder.State getVertexState() {
	//
	//		return buffer.getVertexState();
	//	}
	//
	// 	public void setVertexState(BufferBuilder.State state) {
	//
	//		buffer.setVertexState(state);
	//	}
	
	public void reset() {
		
		buffer.reset();
	}
	
	public void begin(int glMode, VertexFormat format) {
		
		buffer.begin(glMode, format);
	}
	
	public BufferBuilderEL tex(double u, double v) {
		
		buffer.tex(u, v);
		return this;
	}
	
	public BufferBuilderEL lightmap(int p_187314_1_, int p_187314_2_) {
		
		buffer.lightmap(p_187314_1_, p_187314_2_);
		return this;
	}
	
	public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
		
		buffer.putBrightness4(p_178962_1_, p_178962_2_, p_178962_3_, p_178962_4_);
	}
	
	public void putPosition(double x, double y, double z) {
		
		buffer.putPosition(x, y, z);
	}
	
	public int getColorIndex(int vertexIndex) {
		
		return buffer.getColorIndex(vertexIndex);
	}
	
	public void putColorMultiplier(float red, float green, float blue, int vertexIndex) {
		
		buffer.putColorMultiplier(red, green, blue, vertexIndex);
	}
	
	public void putColorRGB_F(float red, float green, float blue, int vertexIndex) {
		
		buffer.putColorRGB_F(red, green, blue, vertexIndex);
	}
	
	public void putColorRGBA(int index, int red, int green, int blue) {
		
		buffer.putColorRGBA(index, red, green, blue);
	}
	
	public void noColor() {
		
		buffer.noColor();
	}
	
	public BufferBuilderEL color(float red, float green, float blue, float alpha) {
		
		buffer.color(red, green, blue, alpha);
		return this;
	}
	
	public BufferBuilderEL color(int red, int green, int blue, int alpha) {
		
		buffer.color(red, green, blue, alpha);
		return this;
	}
	
	public void addVertexData(int[] vertexData) {
		
		buffer.addVertexData(vertexData);
	}
	
	public void endVertex() {
		
		buffer.endVertex();
	}
	
	public BufferBuilderEL pos(double x, double y, double z) {
		
		buffer.pos(x, y, z);
		return this;
	}
	
	public void putNormal(float x, float y, float z) {
		
		buffer.putNormal(x, y, z);
	}
	
	public BufferBuilderEL normal(float x, float y, float z) {
		
		buffer.normal(x, y, z);
		return this;
	}
	
	public void setTranslation(double x, double y, double z) {
		
		buffer.setTranslation(x, y, z);
	}
	
	public void finishDrawing() {
		
		buffer.finishDrawing();
	}
	
	public ByteBuffer getByteBuffer() {
		
		return buffer.getByteBuffer();
	}
	
	public VertexFormat getVertexFormat() {
		
		return buffer.getVertexFormat();
	}
	
	public int getVertexCount() {
		
		return buffer.getVertexCount();
	}
	
	public int getDrawMode() {
		
		return buffer.getDrawMode();
	}
	
	public void putColor4(int argb) {
		
		buffer.putColor4(argb);
	}
	
	public void putColorRGB_F4(float red, float green, float blue) {
		
		buffer.putColorRGB_F4(red, green, blue);
	}
	
	public void putColorRGBA(int index, int red, int green, int blue, int alpha) {
		
		buffer.putColorRGBA(index, red, green, blue, alpha);
	}
	
	public boolean isColorDisabled() {
		
		return buffer.isColorDisabled();
	}
}
