/**
 * 
 */
package cz.kojotak.arx.domain;

import cz.kojotak.arx.ui.event.FilterModel;

/**
 * @date 30.5.2010
 * @author Kojotak 
 */
public interface Searchable {
	public FilterModel getFilter();
	public void setFilter(FilterModel str);
}
