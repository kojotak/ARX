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
import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.enums.Platform;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public abstract class BaseMameGame<T extends Record> extends LegacyCompetitiveGame implements Game, Competetive<T>, LegacyGameMetaData {
	
	private List<T> records;
	private Integer coins;
		
	public BaseMameGame(String id,Category category, String title, String file) {
		super(id,category,title,file);
		this.records = Collections.emptyList();
	}

	@Override
	public Platform getPlatform() {
		return Platform.MAME;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	
	
}
