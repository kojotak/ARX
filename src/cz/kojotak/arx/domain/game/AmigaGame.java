/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import cz.kojotak.arx.domain.AmigaMetaData;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.Platform;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class AmigaGame extends LegacyCompetitiveGame implements Competetive<Record>,AmigaMetaData,WithStatistics {

	@Getter
	@Setter
	protected List<Record> records;
	
	@Getter
	@Setter
	private String md5Disk1;
	
	@Getter
	@Setter
	private String md5Cfg;
	
	@Getter
	@Setter
	private String md5Start;
	
	public AmigaGame(String id,Category category, String title, String file) {
		super(id,category, title, file);
		records = Collections.emptyList();
	}

	@Override
	public Platform getPlatform() {
		return Platform.AMIGA;
	}	
}
