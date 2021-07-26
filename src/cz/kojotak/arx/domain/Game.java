/**
 * 
 */
package cz.kojotak.arx.domain;

import cz.kojotak.arx.domain.enums.Platform;

/**
 * Minimal game contract
 * @date 14.10.2009
 * @author Kojotak 
 */
public interface Game extends Identifiable<String>{
	
	Category getCategory();
	
	/**
	 * 
	 * @return game name
	 */
	String getTitle();
	
	/**
	 * 
	 * @return game's relative file name (rom for mame, ...)
	 */
	String getFile();
	
	/**
	 * 
	 * @return game's platform
	 */
	Platform getPlatform();
		
}
