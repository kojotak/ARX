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
import cz.kojotak.arx.domain.enums.Platform;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public abstract class BaseMameGame extends LegacyCompetitiveGame implements Game, Competetive, LegacyGameMetaData {
	
	private List<Record> records;
	private Integer coins;
		
	public BaseMameGame(String id,Category category, String title, String file) {
		super(id,category,title,file);
		this.records = Collections.emptyList();
	}

	@Override
	public Platform getPlatform() {
		return Platform.MAME;
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
