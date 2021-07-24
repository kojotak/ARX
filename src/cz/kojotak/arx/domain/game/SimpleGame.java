/**
 * 
 */
package cz.kojotak.arx.domain.game;

import java.util.Objects;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public abstract class SimpleGame implements Game {

	private Category category;
	private String title;
	private String file;
	private String id;
	private Float averageRatings;
	
	public SimpleGame(String id,Category category, String title, String file) {
		super();
		this.category = category;
		this.file = file;
		this.title = title;
		this.id = id;
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
