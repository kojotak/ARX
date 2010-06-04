/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
	
	@Getter
	@Setter
	protected List<T> records;
	
	@Getter
	@Setter
	protected Integer coins;
		
	public BaseMameGame(String id,Category category, String title, String file) {
		super(id,category,title,file);
		this.records = Collections.emptyList();
	}

	@Override
	public Platform getPlatform() {
		return Platform.MAME;
	}
	
	
}
