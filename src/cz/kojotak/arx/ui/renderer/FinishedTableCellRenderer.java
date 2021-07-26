/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.enums.FinishedStatus;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class FinishedTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -7003738666878968437L;

	@Override
	protected void setValue(Object value) {
		FinishedStatus fs = FinishedStatus.class.cast(value);
	
		Icon icon = Application.getInstance().getIconLoader().tryLoadIcon(fs);
		String tooltip = Application.getInstance().getLocalization().getOptionalString(fs);
		this.setToolTipText(tooltip);
		this.setIcon(icon);
	}

}
