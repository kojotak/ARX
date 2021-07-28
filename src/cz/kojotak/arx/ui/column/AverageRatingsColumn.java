/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.game.SimpleGame;
import cz.kojotak.arx.ui.renderer.PercentTableCellRenderer;

/**
 * @date 7.2.2010
 * @author Kojotak
 */
public class AverageRatingsColumn extends BaseColumn<SimpleGame,Float> {

	private static final long serialVersionUID = 1L;
	
	public AverageRatingsColumn() {
		super();
		this.setCellRenderer(new PercentTableCellRenderer());
	}

	@Override
	public Float getValue(SimpleGame game) {
		return game.getAverageRatings();
	}
	
	@Override
	public Class<Float> getType() {
		return Float.class;
	}
	
}
