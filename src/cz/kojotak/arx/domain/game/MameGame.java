/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Collections;
import java.util.List;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.LegacyGameMetaData;
import cz.kojotak.arx.domain.impl.Record;
import cz.kojotak.arx.domain.enums.LegacyPlatform;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public class MameGame extends LegacyCompetitiveGame implements Game, Competetive, LegacyGameMetaData {
	
	private List<Record> records;
	private Integer coins;
		
	public MameGame(String id,Category category, String title, String file) {
		super(id, category, LegacyPlatform.MAME, title, file);
		this.records = Collections.emptyList();
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	
	
}
