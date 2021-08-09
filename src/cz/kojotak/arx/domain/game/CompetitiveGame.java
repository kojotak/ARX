/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Collections;
import java.util.List;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.LegacyGameMetaData;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.impl.Record;
/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class CompetitiveGame extends Game implements LegacyGameMetaData, WithStatistics, Competetive {

	public CompetitiveGame(String id, Category category, LegacyPlatform platform, String title, String file) {
		super(id, category, platform, title, file);
	}
	
	private String rules;
	private Integer playerCount;
	private String firstPlayerSign;
	private String secondPlayerSign;
	protected GameStatistics statistics;
	protected List<Record> records = Collections.emptyList();

	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public Integer getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(Integer playerCount) {
		this.playerCount = playerCount;
	}
	public String getFirstPlayerSign() {
		return firstPlayerSign;
	}
	public void setFirstPlayerSign(String firstPlayerSign) {
		this.firstPlayerSign = firstPlayerSign;
	}
	public GameStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(GameStatistics statistics) {
		this.statistics = statistics;
	}
	public String getSecondPlayerSign() {
		return secondPlayerSign;
	}
	public void setSecondPlayerSign(String secondPlayerSign) {
		this.secondPlayerSign = secondPlayerSign;
	}	
}
