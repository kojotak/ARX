/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.game.MameGameDouble;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class CoplayerColumn extends BaseColumn<MameGameDouble,String> {

	private static final long serialVersionUID = -6622361025347639519L;

	@Override
	public String getValue(MameGameDouble game) {
		return game.getSecondPlayerSign();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
