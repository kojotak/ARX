/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import java.awt.Color;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @date 2.4.2010
 * @author Kojotak 
 */
public class PlayerDiffRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -8712072314523432534L;

	@Override
	protected void setValue(Object value) {
		if(value instanceof Integer){
			Integer diff = Integer.class.cast(value);
			StringBuilder sb = new StringBuilder();
			if(diff>0){
				sb.append("+");
				this.setForeground(Color.BLACK);
			}else{
				this.setForeground(Color.RED);
			}
			sb.append(diff);
			this.setText(sb.toString());
			this.setIcon(null);
		}else{
			this.setText("");
			this.setIcon(null);
		}
		this.setHorizontalAlignment(RIGHT);
	}
	
}
