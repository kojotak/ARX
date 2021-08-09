/**
 * 
 */
package cz.kojotak.arx.domain.game;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.LegacyGameMetaData;
import cz.kojotak.arx.domain.enums.LegacyPlatform;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public class MameGame extends CompetitiveGame implements Game, LegacyGameMetaData {
	
	private Integer coins;
		
	public MameGame(String id,Category category, String title, String file) {
		super(id, category, LegacyPlatform.MAME, title, file);
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	
}
