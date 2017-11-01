package net.eldariel.lib.util;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class LocalizationHelper {
	public final String modId;
	private boolean replacesAmpersandWithSectionSign = true;
	private boolean hideFormatErrors = false;
	
	/**
	 * Constructor. The mod ID is converted to lower case.
	 * 
	 * @param modId
	 */
	public LocalizationHelper(String modId) {
		this.modId = modId.toLowerCase();
	}
	
	/**
	 * Set whether or not to replace ampersands with section signs. This allows formatting codes to easily be used in
	 * localization files.
	 */
	public LocalizationHelper setReplaceAmpersand(boolean value) {
		replacesAmpersandWithSectionSign = value;
		return this;
	}
	
	public LocalizationHelper setHideFormatErrors(boolean value) {
		hideFormatErrors = value;
		return this;
	}
	
	// ===============
	// General methods
	// ===============
	
	@SuppressWarnings("deprecation")
	public String getLocalizedString(String key, Object...parameters) {
		
		// On server, use deprecated I18n.
		if (FMLCommonHandler.instance().getSide() == Side.SERVER)
			return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(key, parameters);
		
		// On client, use the new client-side I18n.
		String str = I18n.format(key, parameters).trim();
		
		if (replacesAmpersandWithSectionSign)
			str = str.replaceAll("&", "\u00a7");
		if (hideFormatErrors)
			str = str.replaceFirst("Format error: ", "");
		
		return str;
	}
	
	public String getLocalizedString(String prefix, String key, Object...parameters) {
		return getLocalizedString(prefix + "." + modId + ":" + key, parameters);
	}
	
	
}
