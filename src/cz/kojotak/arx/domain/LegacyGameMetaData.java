/**
 * 
 */
package cz.kojotak.arx.domain;

/**
 * @date 24.1.2010
 * @author Kojotak 
 */
public interface LegacyGameMetaData {
	
	/**
	 * 
	 * @return rules as string or null there are none
	 */
	String getRules();
		
	/**
	 * 
	 * @return average game ratings
	 */
	Float getAverageRatings();
	
	/**
	 * 
	 * @return sign of the best player in this game or null, if nobody played the game yet
	 */
	String getFirstPlayerSign();
	
	/**
	 * 
	 * @return number of players in this game, never null
	 */
	Integer getPlayerCount();

}
