/**
 * 
 */
package cz.kojotak.arx.ui.listener;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.ui.GameTable;
import cz.kojotak.arx.ui.RecordTable;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 7.2.2010
 * @author Kojotak 
 */
public class GameTableSelectListener implements ListSelectionListener {
		
	private GameTable games;
	private RecordTable records;
	
		
	public GameTableSelectListener(GameTable games,RecordTable records) {
		super();
		this.games = games;
		this.records=records;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		
		ListSelectionModel rowSM = (ListSelectionModel)e.getSource();
		int idx = rowSM.getMinSelectionIndex();
		if(idx<0){
			idx=0;
		}
		TableModel tm = games.getModel();
		if(tm instanceof GenericTableModel<?>){
			GenericTableModel<?> ggtm = GenericTableModel.class.cast(tm);
			Object object =ggtm.getItem(games.convertRowIndexToModel(idx));
			if(!(object instanceof Competetive<?>)){
				//log.error("bad use of "+this.getClass().getSimpleName()+", expected "+Competetive.class.getSimpleName()+" and got "+object!=null?object.getClass().getSimpleName():"null");
			}else{
				Competetive<?> cmp = Competetive.class.cast(object);
				Application.getInstance().getLogger(this).info("selected "+cmp);
				this.records.updateTableModel(cmp);	
			}
		}		
	}
}
