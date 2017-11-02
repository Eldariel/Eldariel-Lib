package net.eldariel.lib.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 * A block/item intended to be registered through an ERegistry instance.
 * 
 * @author Eldariel
 *
 */
public interface IRegistryObject {

	public void addRecipes(RecipeMaker recipes);
	
	public void addOreDict();
	
	public String getModId();
	
	public String getName();
	
	public default String getFullName() {
		return getModId() + ":" + getName();
	}
	
	/**
	 * Gets a set of models (paired with metadata for each model). Replaces getVariants. The inclusion of metadata means
	 * you no longer need to fill unused metadata values with nulls and the model map should NEVER contain null values.
	 * 
	 * @param models An empty map to be filled with metadata/model pairs.
	 */
	public void getModels(Map<Integer, ModelResourceLocation> models);
	
	/**
	 * Return true and handle model registration here if you do not want Eldariel Lib to handle model registration. If false
	 * is returned, Eldariel Lib will register the models obtained via getModels.
	 */
	public default boolean registerModels() {
		return false;
	}
}
