/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.ui.renderer.PercentTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class RelativePositionColumn extends BaseColumn<WithStatistics,Float>{

	private static final long serialVersionUID = 1L;

	public RelativePositionColumn() {
		super();
		this.setCellRenderer(new PercentTableCellRenderer());
	}

	@Override
	public Float getValue(WithStatistics source) {
		return source.getStatistics().getPlayerRelativePosition();
	}
	
	@Override
	public Class<Float> getType() {
		return Float.class;
	}
}
