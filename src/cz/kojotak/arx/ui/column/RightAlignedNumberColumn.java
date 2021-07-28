/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.ui.renderer.RightAlignedNumberTableCellRenderer;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public abstract class RightAlignedNumberColumn<T,V> extends BaseColumn<T,V> {

	private static final long serialVersionUID = 1L;

	public RightAlignedNumberColumn() {
		super();
		this.setCellRenderer(new RightAlignedNumberTableCellRenderer());
	}	
}
