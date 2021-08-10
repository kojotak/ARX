/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.ui.renderer.PositionTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class PositionColumn extends BaseColumn<Score,Integer>  {
	
	private static final long serialVersionUID = -4709385859792488464L;

	public PositionColumn() {
		super();
		this.setCellRenderer(new PositionTableCellRenderer());
	}

	@Override
	public Integer getValue(Score source) {
		return source.position();
	}
	
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
