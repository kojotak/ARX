/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.enums.MedalPosition;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class PositionTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void setValue(Object value) {
		if(value==null   //missing value 
			|| !(value instanceof Integer) //something else than position 
			|| (value instanceof Integer pos && pos == Integer.MAX_VALUE) //worst possible position
				){
			setIcon(null);
			setText("");
			return;
		}
		Integer position = Integer.class.cast(value);
		MedalPosition medal = MedalPosition.resolveFrom(position);
		Icon icon=null;
		if (medal != null) {
			icon = Application.getInstance().getIconLoader().tryLoadIcon(medal);
			setText("");
		} else {
			icon=null;
			setText(position != null ? position.toString() + "." : "");
		}
		setIcon(icon);
		this.setToolTipText(position != null ? position + ". "+Application.getInstance().getLocalization().getString(this, "PLACE") : "");
		this.setHorizontalAlignment(RIGHT);
	}
}
