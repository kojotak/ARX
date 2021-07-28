/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;

/**
 * @date 19.4.2010
 * @author Kojotak 
 */
public class PointsColumn extends RightAlignedNumberColumn<WithStatistics, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public Integer getValue(WithStatistics source) {
		return source.getStatistics().getPoints();
	}

	public PointsColumn() {
		super();
		this.setVisible(false);
	}
	
	
}
