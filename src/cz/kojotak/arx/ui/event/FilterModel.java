/**
 * 
 */
package cz.kojotak.arx.ui.event;

import java.util.Objects;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.enums.Availibility;
import cz.kojotak.arx.domain.enums.Platform;

/**
 * Contains filtering information
 * @date 2.6.2010
 * @author Kojotak 
 */
public class FilterModel {
	
	private Category category;
	private Platform platform;
	private Availibility availibility;
	private String search;

	public static FilterModel updateWith(FilterModel original,FilterModel update){
		if(original==null||update==null){
			return new FilterModel();
		}
		if(update.getAvailibility()!=null){
			original.setAvailibility(update.getAvailibility());
		}
		if(update.getPlatform()!=null){
			original.setPlatform(update.getPlatform());
		}
		if(update.getSearch()!=null){
			original.setSearch(update.getSearch());			
		}
		if(update.getCategory()!=null){
			original.setCategory(update.getCategory());
		}
		return original;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Availibility getAvailibility() {
		return availibility;
	}

	public void setAvailibility(Availibility availibility) {
		this.availibility = availibility;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public int hashCode() {
		return Objects.hash(availibility, category, platform, search);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilterModel other = (FilterModel) obj;
		return availibility == other.availibility && category == other.category && platform == other.platform
				&& Objects.equals(search, other.search);
	}
	
}
