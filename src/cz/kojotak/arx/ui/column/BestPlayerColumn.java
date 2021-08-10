/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.WithStatistics;

/**
 * @date 25.3.2010
 * @author Kojotak 
 */
public class BestPlayerColumn extends BaseColumn<WithStatistics,String> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getValue(WithStatistics source) {
		User player = source.getStatistics().getBestPlayer();
		return player!=null ? player.nick() : "";
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	public BestPlayerColumn() {
		super();
	}
}
