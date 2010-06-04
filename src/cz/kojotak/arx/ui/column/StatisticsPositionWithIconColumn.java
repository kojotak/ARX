/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.ui.renderer.PositionTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class StatisticsPositionWithIconColumn extends BaseColumn<WithStatistics,Integer>{

	private static final long serialVersionUID = -5048419876304225270L;

	public StatisticsPositionWithIconColumn() {
		super();
		this.setCellRenderer(new PositionTableCellRenderer());
	}

	@Override
	public Integer getValue(WithStatistics source) {
		return source.getStatistics().getPlayerPosition();
	}
	
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
