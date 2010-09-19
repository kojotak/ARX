/**
 * 
 */
package cz.kojotak.arx.domain;

import cz.kojotak.arx.ui.event.FilterEvent;

/**
 * @date 30.5.2010
 * @author Kojotak 
 */
public interface Searchable {
	public FilterEvent getFilter();
	public void setFilter(FilterEvent str);
}
