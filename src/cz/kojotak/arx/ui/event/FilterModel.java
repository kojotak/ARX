/**
 * 
 */
package cz.kojotak.arx.ui.event;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.enums.Availibility;
import cz.kojotak.arx.domain.enums.Platform;
import lombok.Data;

/**
 * Contains filtering information
 * @date 2.6.2010
 * @author Kojotak 
 */
@Data
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
}
