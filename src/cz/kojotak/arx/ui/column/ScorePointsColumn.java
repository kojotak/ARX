/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;

public class ScorePointsColumn extends RightAlignedNumberColumn<Score, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer getValue(Score source) {
		return source.points();
	}
	
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
