/**
 * 
 */
package cz.kojotak.arx.domain.game;

import lombok.Getter;
import lombok.Setter;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.LegacyGameMetaData;
import cz.kojotak.arx.domain.WithStatistics;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public abstract class LegacyCompetitiveGame extends SimpleGame implements LegacyGameMetaData,WithStatistics {

	public LegacyCompetitiveGame(String id,Category category, String title, String file) {
		super(id,category, title, file);
	}
	
	@Getter
	@Setter
	private String rules;
	
	@Getter
	@Setter
	private Integer playerCount;
	
	@Getter
	@Setter
	private String firstPlayerSign;
	
	@Getter
	@Setter
	protected GameStatistics statistics;

}
