/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.List;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public interface Competetive<T extends Record> {
	
	/**
	 * 
	 * @return ordered list of records for given game
	 */
	List<T> getRecords();
	
	void setRecords(List<T> list);
}
