/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class Game implements WithStatistics {

	private final Category category;
	private final String title;
	private final String file;
	private final String id;
	private final Platform platform;
	private final String rules;
	protected final List<Score> records = new ArrayList<>();
	
	protected GameStatistics statistics;

	public Game(String id, Category category, Platform platform, String title, String file, String rules) {
		super();
		this.category = category;
		this.file = file;
		this.title = title;
		this.id = id;
		this.platform = platform;
		this.rules = rules;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+title;
	}

	public List<Score> getRecords() {
		return records;
	}
	public String getRules() {
		return rules;
	}
	public Integer getPlayerCount() {
		return records.size();
	}
	public GameStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(GameStatistics statistics) {
		this.statistics = statistics;
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

	public Float getAverageRatings() {
		Float sum = 0F;
		int cnt = 0;
		for(Score s: records) {
			if(s.rating()!=null) {
				cnt++;
				sum+=s.rating();
			}
		}
		return cnt > 0 ? sum / cnt : null;
	}
	
}
