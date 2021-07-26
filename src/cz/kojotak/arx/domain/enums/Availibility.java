/**
 * 
 */
package cz.kojotak.arx.domain.enums;

/**
 * @date 19.5.2010
 * @author Kojotak 
 */
public enum Availibility {
	
	/**
	 * unchecked
	 */
	UNKNOWN(0),
	
	/**
	 * checked - missing some parts
	 */
	MISSING(1),
	
	/**
	 * checked and present, but not working
	 */
	INCORRECT(2),
	
	/**
	 * checked and present, but not sure, if working
	 */
	PRESENT(3),
	
	/**
	 * present and working
	 */
	WORKING(4);
	
	public final int priority;

	private Availibility(int priority) {
		this.priority = priority;
	}
	
}
