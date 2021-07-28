/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.WithStatistics;

/**
 * How many points can a player earn?
 * 
 * @date 19.5.2010
 * @author Kojotak 
 */
public class ChanceColumn extends RightAlignedNumberColumn<WithStatistics, Integer>  {

	private static final long serialVersionUID = 1L;

	public ChanceColumn() {
		super();
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public Integer getValue(WithStatistics source) {
		GameStatistics stats = source.getStatistics();
		Integer points = stats.getPoints();
		int players = stats.getRecordsCount();
		int max = (players>0?--players*10:0) + 100;
		if(points!=null){
			max -= points;
		}
		return max;
	}
}
