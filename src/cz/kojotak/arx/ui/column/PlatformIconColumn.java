/**
 * 
 */
package cz.kojotak.arx.ui.column;

import javax.swing.table.TableCellRenderer;

import cz.kojotak.arx.domain.game.Game;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.ui.renderer.EnumIconTableCellRenderer;

/**
 * @date 3.6.2010
 * @author Kojotak 
 */
public class PlatformIconColumn extends BaseColumn<Game, LegacyPlatform> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<LegacyPlatform> getType() {
		return LegacyPlatform.class;
	}

	@Override
	public LegacyPlatform getValue(Game source) {
		return source.getPlatform();
	}

	@Override
	public TableCellRenderer getCellRenderer() {
		return new EnumIconTableCellRenderer<LegacyPlatform>();
	}
	
}
