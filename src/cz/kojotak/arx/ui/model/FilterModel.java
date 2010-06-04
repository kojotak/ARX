/**
 * 
 */
package cz.kojotak.arx.ui.model;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.enums.Availibility;
import cz.kojotak.arx.domain.enums.Platform;
import lombok.Data;

/**
 * @date 2.6.2010
 * @author Kojotak 
 */
@Data
public class FilterModel {
	
	private Category category;
	private Platform platform;
	private Availibility availibility;
	private String search;

}
