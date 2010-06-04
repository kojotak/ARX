/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.enums.FinishedStatus;

/**
 * @date 25.3.2010
 * @author Kojotak 
 */
public class NullStatistics implements GameStatistics {
	
	public static final NullStatistics INSTANCE= new NullStatistics();

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getAverageRating()
	 */
	@Override
	public Float getAverageRating() {
		return null;
	}
	
	@Override
	public Integer getPoints() {
		return null;
	}

	@Override
	public User getOponent() {
		return null;
	}

	@Override
	public Integer getOponentDiff() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getBestPlayer()
	 */
	@Override
	public String getBestPlayer() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getFinishedStatus()
	 */
	@Override
	public FinishedStatus getFinishedStatus() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getHighestScore()
	 */
	@Override
	public Long getHighestScore() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerPosition()
	 */
	@Override
	public Integer getPlayerPosition() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerRating()
	 */
	@Override
	public Float getPlayerRating() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerRelativePosition()
	 */
	@Override
	public Float getPlayerRelativePosition() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getPlayerSign()
	 */
	@Override
	public String getPlayerSign() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getRatingsCount()
	 */
	@Override
	public int getRatingsCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getRecordsCount()
	 */
	@Override
	public int getRecordsCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#getUserScore()
	 */
	@Override
	public Long getUserScore() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#hasPlayerFinished()
	 */
	@Override
	public Boolean hasPlayerFinished() {
		return false;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.GameStatistics#hasSomebodyFinished()
	 */
	@Override
	public Boolean hasSomebodyFinished() {
		return false;
	}

}
