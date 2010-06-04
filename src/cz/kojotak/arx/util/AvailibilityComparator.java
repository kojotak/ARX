/**
 * 
 */
package cz.kojotak.arx.util;

import java.io.Serializable;
import java.util.Comparator;

import cz.kojotak.arx.domain.enums.Availibility;

/**
 * @date 19.5.2010
 * @author Kojotak 
 */
public class AvailibilityComparator implements Comparator<Availibility>,Serializable {
	private static final long serialVersionUID = -3789665102291322046L;

	@Override
	public int compare(Availibility o1, Availibility o2) {
		return o1.priority - o2.priority;
	}
	
}
