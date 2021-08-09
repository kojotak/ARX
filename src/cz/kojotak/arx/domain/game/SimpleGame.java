/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Objects;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.enums.LegacyPlatform;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class SimpleGame implements Game {

	private Category category;
	private String title;
	private String file;
	private String id;
	private Float averageRatings;
	private LegacyPlatform platform;
	
	public SimpleGame(String id, Category category, LegacyPlatform platform, String title, String file) {
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
	
	@Override
	public LegacyPlatform getPlatform() {
		return platform;
	}
	
	public void setPlatform(LegacyPlatform platform){
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
		SimpleGame other = (SimpleGame) obj;
		return Objects.equals(id, other.id);
	}
	
}
