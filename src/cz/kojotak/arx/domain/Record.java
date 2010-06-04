/**
 * 
 */
package cz.kojotak.arx.domain;

/**
 * @date 14.10.2009
 * @author Kojotak 
 */
public interface Record{
	/**
	 * 
	 * @return total score, must not be null
	 */
	Long getScore();
	
	/**
	 * 
	 * @return user's game ratings in 0..1, can be null
	 */
	Float getRating();
	
	/**
	 * 
	 * @return true if played to the very end, cannot be null
	 */
	Boolean isFinished();
	
	/**
	 * 
	 * @return user record owner
	 */
	String getPlayer();
	
	/**
	 * 
	 * @return game play duration
	 */
	Integer getDuration();
	
	Integer getPosition();

}
