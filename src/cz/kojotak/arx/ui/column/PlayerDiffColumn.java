/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.ui.renderer.PlayerDiffRenderer;

/**
 * @date 2.4.2010
 * @author Kojotak 
 */
public class PlayerDiffColumn extends BaseColumn<WithStatistics, Integer> {

	private static final long serialVersionUID = -3022319649724475810L;

	public PlayerDiffColumn() {
		super();
		this.setCellRenderer(new PlayerDiffRenderer());
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public Integer getValue(WithStatistics source) {
		return source.getStatistics().getOponentDiff();
	}

}
