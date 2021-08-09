/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.Objects;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class Game {

	private Category category;
	private String title;
	private String file;
	private String id;
	private Float averageRatings;
	private Platform platform;
	
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
