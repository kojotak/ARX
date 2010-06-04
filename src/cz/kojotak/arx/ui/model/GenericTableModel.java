/**
 * 
 */
package cz.kojotak.arx.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.column.BaseColumn;


/**
 * Generic table, list based, model
 * @date 7.2.2010
 * @author Kojotak 
 */
public class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 5167427061360573706L;

	protected List<T> items;
	protected List<BaseColumn<? super T,?>> columns;
		
	public GenericTableModel(List<T> items,List<BaseColumn<? super T,?>> columns) {
		super();
		this.items = items;
		this.columns = columns;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columns.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return items.size();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex>=getRowCount()){
			return null;//FIXME
		}
		T game = null;
		try{
			game = items.get(rowIndex);
		}catch(IndexOutOfBoundsException ex){
			Application.getInstance().getLogger(this).error("cannot return "+rowIndex+" from "+columns);
			return null;
		}
		return columns.get(columnIndex).getValue(game);
	}

	/**
	 * FIXME upravit, abych nemusel rowIndex predtim konvertovat pomoci
	 * <pre>
	 * games.convertRowIndexToModel(idx)
	 * <pre>
	 * @param rowIndex
	 * @return
	 */
	public T getItem(int rowIndex){
		return rowIndex>=0?items.get(rowIndex):null;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columns.get(columnIndex).getType();
	}

	@Override
	public String getColumnName(int column) {
		return columns.get(column).getHeaderValue().toString();
	}	
}
