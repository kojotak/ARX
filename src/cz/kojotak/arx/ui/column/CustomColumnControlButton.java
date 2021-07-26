/**
 * 
 */
package cz.kojotak.arx.ui.column;

import javax.swing.Icon;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 * My custom column control button which uses column descriptions instead of column headers for action names.
 * @date 16.9.2010
 * @author Kojotak 
 */
public class CustomColumnControlButton extends ColumnControlButton {

	//generated
	private static final long serialVersionUID = 3085435438688149763L;

	@Override
	protected ColumnVisibilityAction createColumnVisibilityAction(
			TableColumn column) {
		ColumnVisibilityAction action = super.createColumnVisibilityAction(column);
		if(column instanceof TableColumnExt){
			TableColumnExt ext = TableColumnExt.class.cast(column);
			String tip = ext.getToolTipText();
			if(tip!=null){
				action.setName(tip);
			}
		}
		return action;
	}
	
	public CustomColumnControlButton(JXTable table, Icon icon) {
		super(table,icon);
	}

}
