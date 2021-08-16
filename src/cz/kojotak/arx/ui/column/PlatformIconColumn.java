/**
 * 
 */
package cz.kojotak.arx.ui.column;

import javax.swing.table.TableCellRenderer;

import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.ui.renderer.NamedWithIdTableCellRenderer;

/**
 * @date 3.6.2010
 * @author Kojotak 
 */
public class PlatformIconColumn extends BaseColumn<Game, Platform> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Platform> getType() {
		return Platform.class;
	}

	@Override
	public Platform getValue(Game source) {
		return source.getPlatform();
	}

	@Override
	public TableCellRenderer getCellRenderer() {
		return new NamedWithIdTableCellRenderer<Platform>(Platform.class);
	}
	
}
