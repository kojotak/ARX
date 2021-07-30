/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.List;
import cz.kojotak.arx.domain.impl.Record;
/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public interface Competetive {
	
	/**
	 * 
	 * @return ordered list of records for given game
	 */
	List<Record> getRecords();
	
	void setRecords(List<Record> list);
}
