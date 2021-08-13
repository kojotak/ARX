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
	private final int id;
	private final Platform platform;
	private final String rules;
	private final Game parent;
	private final int modeMax;
	protected final List<Score> records1P = new ArrayList<>();
	protected final List<Score> records2P = new ArrayList<>();
	
	protected GameStatistics statistics;

	public Game(int id, Game parent, Category category, Platform platform, String title, String file, String rules, int modeMax) {
		super();
		this.parent = parent;
		this.category = category;
		this.file = file;
		this.title = title;
		this.id = id;
		this.platform = platform;
		this.rules = rules;
		this.modeMax = modeMax;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + "]";
	}

	public int getModeMax() {
		return modeMax;
	}
	public Game getParent() {
		return parent;
	}
	public List<Score> getRecords1P() {
		return records1P;
	}
	public List<Score> getRecords2P() {
		return records2P;
	}

	public String getRules() {
		return rules;
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

	public int getId() {
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
		for(Score s: records1P) {
			if(s.rating()!=null) {
				cnt++;
				sum+=s.rating();
			}
		}
		return cnt > 0 ? sum / cnt : null;
	}
	
}
