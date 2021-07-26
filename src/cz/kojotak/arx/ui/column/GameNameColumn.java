/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Game;

/**
 * @date 25.3.2010
 * @author Kojotak 
 */
public class GameNameColumn extends BaseColumn<Game,String> {

	private static final long serialVersionUID = -5814822950510821115L;

	@Override
	public String getValue(Game source) {
		return source.getTitle();
	}
	@Override
	public Class<String> getType() {
		return String.class;
	}

		
}
