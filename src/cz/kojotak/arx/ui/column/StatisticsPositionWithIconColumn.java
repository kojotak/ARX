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

	private static final long serialVersionUID = 1L;

	public StatisticsPositionWithIconColumn() {
		super();
		this.setCellRenderer(new PositionTableCellRenderer());
	}

	@Override
	public Integer getValue(WithStatistics source) {
		//interpret missing position as the worst possible
		Integer position = source.getStatistics().getPlayerPosition();
		return position !=null && position > 0 ? position : Integer.MAX_VALUE;
	}
	
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
