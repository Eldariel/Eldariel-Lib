package net.eldariel.lib.registry;

/**
 * Implement on classes like ModBlocks/ModItems. Register them with your ERegistry to have them automatically called for
 * the appropriate event. Creating blocks/items/etc should probably be done statically or in a constructor.
 * 
 * @author Eldariel
 * @since 0.0.1
 */
public interface IRegistrationHandler<T> {

	public void registerAll(ERegistry reg);
}
