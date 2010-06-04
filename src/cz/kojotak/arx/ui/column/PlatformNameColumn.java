/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Game;

/**
 * @date 3.6.2010
 * @author Kojotak 
 */
public class PlatformNameColumn extends BaseColumn<Game, String> {

	private static final long serialVersionUID = 6437538101215929826L;

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String getValue(Game source) {
		return Application.getInstance().getLocalization().getString(source.getPlatform());
	}

}
