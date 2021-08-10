/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class ScoreRecordColumn extends RightAlignedNumberColumn<Score,Long> {

	private static final long serialVersionUID = 1L;

	@Override
	public Long getValue(Score source) {
		return source.score();
	}
	
	@Override
	public Class<Long> getType() {
		return Long.class;
	}
}
