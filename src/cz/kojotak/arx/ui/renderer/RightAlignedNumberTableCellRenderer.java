/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class RightAlignedNumberTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 7675514193309887397L;
	
	@Override
	protected void setValue(Object value) {
		if(value instanceof Number){
			Number numero = Number.class.cast(value);
			this.setText(""+numero);
		}else{
			super.setValue(value);
		}
		this.setHorizontalAlignment(RIGHT);
	}

}
