/**
 * 
 */
package cz.kojotak.arx.domain.game;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.enums.Platform;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class NoncompetitiveGame extends SimpleGame {

	public NoncompetitiveGame(String id,Category category, String title, String file) {
		super(id,category, title, file);
	}

	Platform platform;
	
	@Override
	public Platform getPlatform() {
		return platform;
	}
	
	public void setPlatform(Platform platform){
		this.platform=platform;
	}
	
		
}
