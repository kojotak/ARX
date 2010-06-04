/**
 *
 */
package cz.kojotak.arx.ui.column;

import javax.swing.Icon;

import org.jdesktop.swingx.table.TableColumnExt;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.ui.Valuable;

/**
 * Base column supports localization and fills column header
 * 
 * @date 25.3.2010
 * @author Kojotak
 */
public abstract class BaseColumn<T, V> extends TableColumnExt implements
		Valuable<T, V> {

	private static final long serialVersionUID = -5766438589532953454L;
	private String colName = null;
	
	
	public static enum PROPS {
		NAME, WIDTH, WIDTH_MAX, WIDTH_MIN, TOOLTIP, PROTOTYPE
	}

	protected Icon getHeaderIcon() {
		return null;
	}

	protected BaseColumn() {
		super();
		Localization loc = Application.getInstance().getLocalization();
		colName = loc.getString(this, PROPS.NAME.toString());
		Integer prefW = loc.getOptionalInteger(this, PROPS.WIDTH.toString());
		Integer minW = loc.getOptionalInteger(this, PROPS.WIDTH_MAX.toString());
		Integer maxW = loc.getOptionalInteger(this, PROPS.WIDTH_MIN.toString());
		String tooltip = loc.getOptionalString(this, PROPS.TOOLTIP.toString());
		String proto = loc.getOptionalString(this, PROPS.PROTOTYPE.toString());
		this.setToolTipText(tooltip);
		this.setHeaderValue(colName);
		if(proto!=null){
			this.setPrototypeValue(proto);
		}
		
		// this.setHeaderRenderer(new
		// HeaderTableCellRenderer(getHeaderIcon(),tooltip));
		if (prefW != null) {
			this.setPreferredWidth(prefW);
		}
		if (minW != null) {
			this.setMinWidth(minW);
		}
		if (maxW != null) {
			this.setMaxWidth(maxW);
		}

	}

	public String getColumnName() {
		return colName;
	}
}
