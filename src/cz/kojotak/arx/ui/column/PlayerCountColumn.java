/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class PlayerCountColumn extends
		RightAlignedNumberColumn<WithStatistics,Integer> {

	private static final long serialVersionUID = 8570607640759853418L;

	@Override
	public Integer getValue(WithStatistics game) {
		return game.getStatistics().getRecordsCount();
	}
	
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

}
