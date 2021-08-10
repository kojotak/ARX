/**
 * 
 */
package cz.kojotak.arx.ui.model;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.ui.column.BaseColumn;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class GenericTableColumnModel extends DefaultTableColumnModelExt {

	private static final long serialVersionUID = -2691309236663415142L;
	
	public GenericTableColumnModel(Mode mode) {
		this(mode.getColumns());
	}
	
	public GenericTableColumnModel(List<? extends BaseColumn<?,?>> columns) {
		super();
		int idx=0;
		for(BaseColumn<?,?> col:columns){
			col.setModelIndex(idx);
			this.addColumn(col);
			idx++;
		}
	}

}
