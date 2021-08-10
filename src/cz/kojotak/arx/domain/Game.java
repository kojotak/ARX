/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cz.kojotak.arx.domain.impl.GameStatistics;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class Game implements LegacyGameMetaData, WithStatistics {

	private Category category;
	private String title;
	private String file;
	private String id;
	private Float averageRatings;
	private Platform platform;
	
	private String rules;
	private Integer playerCount;
	private String firstPlayerSign;
	private String secondPlayerSign;
	protected GameStatistics statistics;
	protected List<Score> records = Collections.emptyList();

	public Game(String id, Category category, Platform platform, String title, String file) {
		super();
		this.category = category;
		this.file = file;
		this.title = title;
		this.id = id;
		this.platform = platform;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+title;
	}

	public List<Score> getRecords() {
		return records;
	}
	public void setRecords(List<Score> records) {
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

	public Float getAverageRatings() {
		return averageRatings;
	}

	public void setAverageRatings(Float averageRatings) {
		this.averageRatings = averageRatings;
	}

	public Category getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getFile() {
		return file;
	}

	public String getId() {
		return id;
	}
	
	public Platform getPlatform() {
		return platform;
	}
	
	public void setPlatform(Platform platform){
		this.platform=platform;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(id, other.id);
	}
	
}
