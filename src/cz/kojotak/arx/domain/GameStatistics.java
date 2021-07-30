/**
 * 
 */
package cz.kojotak.arx.domain;

import cz.kojotak.arx.domain.enums.FinishedStatus;
import cz.kojotak.arx.domain.impl.User;

/**
 * @date 7.2.2010
 * @author Kojotak 
 */
public interface GameStatistics {
	
	Integer getOponentDiff();
	User getOponent();
	Integer getPoints();
	
	FinishedStatus getFinishedStatus();
	
	/**
	 * @return player position or null, if not played yet
	 */
	Integer getPlayerPosition();
	
	/**
	 * @return player's rating or null, if not rated yet
	 */
	Float getPlayerRating();
	
	/**
	 * @return player's relative position in range of 0-100%, where 100% means first place a 0% last place
	 */
	Float getPlayerRelativePosition();
	
	/**
	 * @return player's sign or null, if nobody was selected
	 */
	String getPlayerSign();
	
	/**
	 * @return average game rating or null, if no ratings was performed
	 */
	Float getAverageRating();
	
	/**
	 * @return number of rated records
	 */
	int getRatingsCount();
	
	/**
	 * @return number of records
	 */
	int getRecordsCount();
	
	/**
	 * 
	 * @return true/false if player has given game finished or null, if not played yet
	 */
	Boolean getPlayerFinished();
	
	/**
	 * @return true if some player has finished given game, false otherwise
	 */
	Boolean getSomebodyFinished();
	
	/**
	 * @return best player's score
	 */
	Long getHighestScore();
	
	/**
	 * @return player's score or null if not played
	 */
	Long getUserScore();
	
	/**
	 * @return best player's sign
	 */
	String getBestPlayer();

}
