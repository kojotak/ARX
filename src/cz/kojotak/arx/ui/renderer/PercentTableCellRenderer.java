/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class PercentTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -9069070684240890998L;

	@Override
	protected void setValue(Object value) {
		if(value instanceof Float){
			Float percent = Float.class.cast(value);
			if(percent<=1){
				percent*=100;
			}
			Integer i = percent.intValue();
			this.setText(""+i+"%");
		}else{
			super.setValue(value);
		}
		this.setHorizontalAlignment(RIGHT);
	}
}
