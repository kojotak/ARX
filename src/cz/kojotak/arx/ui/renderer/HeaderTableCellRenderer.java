/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class HeaderTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -6984285081157030756L;
	
	protected Icon icon;
	protected String tooltip;

	public HeaderTableCellRenderer(Icon icon,String tooltip) {
		super();
		this.icon = icon;
		this.setToolTipText(tooltip);
		this.setHorizontalAlignment(CENTER);
		this.setOpaque(false);
	}

	@Override
	protected void setValue(Object value) {
		if(icon!=null){
			this.setIcon(icon);
			this.setText("");
		}else{
			this.setIcon(null);
			this.setText(value!=null?value.toString():"");
		}
	}
}
