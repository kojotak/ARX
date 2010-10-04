/**
 *
 */
package cz.kojotak.arx.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXTable;

import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.PositionColumn;
import cz.kojotak.arx.ui.column.RecordDurationColumn;
import cz.kojotak.arx.ui.column.RecordPlayerColumn;
import cz.kojotak.arx.ui.column.ScoreRecordColumn;
import cz.kojotak.arx.ui.model.GenericTableColumnModel;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 7.2.2010
 * @author Kojotak
 */
public class RecordTable extends JXTable {
	private static final long serialVersionUID = 6894398339660146006L;

	private static final List<BaseColumn<?,?>> COLS= new ArrayList<BaseColumn<?,?>>(){
		private static final long serialVersionUID = 576599846226451116L;{
		add(new PositionColumn());
		add(new RecordPlayerColumn());
		add(new ScoreRecordColumn());
		add(new RecordDurationColumn());
	}};

	@SuppressWarnings("unchecked")
	public RecordTable() {
		super(new GenericTableModel(Collections.emptyList(),COLS),new GenericTableColumnModel(COLS));
		AnnotationProcessor.process(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setColumnControlVisible(true);
	}

	@EventSubscriber
	public void updateTableModel(Game event){
		if(!(event instanceof Competetive<?>)){
			return;
		}
		Competetive<?> game = Competetive.class.cast(event); 
		List<?> records = game!=null?game.getRecords():Collections.emptyList();
		@SuppressWarnings("unchecked")GenericTableModel<?> model = new GenericTableModel(records,COLS);
		this.setModel(model);
		model.fireTableDataChanged();

		if(game instanceof WithStatistics){
			WithStatistics ws = WithStatistics.class.cast(game);
			GameStatistics gs = ws.getStatistics();
			if(gs!=null){
				Integer position = gs.getPlayerPosition();
				if(position!=null){
					int pos=--position;
					this.getSelectionModel().setSelectionInterval(pos, pos);
				}
			}
		}
	}
}
