/**
 * 
 */
package cz.kojotak.arx.domain;

/**
 * @date 7.2.2010
 * @author Kojotak 
 */
public interface WithStatistics {
	GameStatistics getStatistics();
	void setStatistics(GameStatistics stats);
}
