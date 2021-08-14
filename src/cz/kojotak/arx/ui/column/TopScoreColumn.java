/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class TopScoreColumn extends RightAlignedNumberColumn<WithStatistics,Long> {

	private static final long serialVersionUID = 1L;

	@Override
	public Long getValue(WithStatistics source) {
		return source.getStatistics().getHighestScore();
	}
	
	@Override
	public Class<Long> getType() {
		return Long.class;
	}

}
