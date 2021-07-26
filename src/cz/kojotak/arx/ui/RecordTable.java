/**
 *
 */
package cz.kojotak.arx.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXTable;

import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.mode.TwoPlayerMode;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CoplayerColumn;
import cz.kojotak.arx.ui.column.CustomColumnControlButton;
import cz.kojotak.arx.ui.column.FinishedRecordColumn;
import cz.kojotak.arx.ui.column.PositionColumn;
import cz.kojotak.arx.ui.column.Record2PFirstPlayerColumn;
import cz.kojotak.arx.ui.column.RecordDurationColumn;
import cz.kojotak.arx.ui.column.RecordPlayerColumn;
import cz.kojotak.arx.ui.column.ScoreRecordColumn;
import cz.kojotak.arx.ui.event.ResizeRecordPanel;
import cz.kojotak.arx.ui.model.GenericTableColumnModel;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 7.2.2010
 * @author Kojotak
 */
public class RecordTable extends JXTable {
	private static final long serialVersionUID = 6894398339660146006L;
		
	
	private List<BaseColumn<?,?>> cols;
	@SuppressWarnings("unchecked")
	private RecordTable(List<BaseColumn<?,?>> cols) {
		super(new GenericTableModel(Collections.emptyList(),cols), new GenericTableColumnModel(cols));
		this.cols = cols;
		AnnotationProcessor.process(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setColumnControlVisible(true);
	}
	
	public static RecordTable createRecordTable(Mode<?> mode){
		List<BaseColumn<?,?>> cols = getColumns(mode);
		RecordTable rt = new RecordTable(cols);
		return rt;
	}
	
	private static List<BaseColumn<?,?>> getColumns(Mode<?> mode){
		List<BaseColumn<?,?>> cols = new ArrayList<BaseColumn<?,?>>();
		cols.add(new PositionColumn());
		
		if(mode instanceof TwoPlayerMode){
			cols.add(new Record2PFirstPlayerColumn());
			cols.add(new CoplayerColumn());
		}else{
			cols.add(new RecordPlayerColumn());
		}
		cols.add(new ScoreRecordColumn());
		cols.add(new RecordDurationColumn());
		cols.add(new FinishedRecordColumn());
		return cols;
	}

	@EventSubscriber
	public void onModeChange(Mode<?> mode){
		List<BaseColumn<?,?>> cols = getColumns(mode);
		this.cols=cols;
		@SuppressWarnings("unchecked")TableModel tm = new GenericTableModel(Collections.emptyList(),cols);
		TableColumnModel tcm = new GenericTableColumnModel(cols);
		this.setModel(tm);
		this.setColumnModel(tcm);
		EventBus.publish(new ResizeRecordPanel());
	}
	
	/**
	 * updates record table
	 * @param event
	 */
	@EventSubscriber
	public void onGameChange(Game event){
		if(!(event instanceof Competetive<?>)){
			return;
		}
		Competetive<?> game = Competetive.class.cast(event); 
		List<?> records = game!=null?game.getRecords():Collections.emptyList();
		@SuppressWarnings("unchecked")GenericTableModel<?> tableModel = new GenericTableModel(records,cols);
		this.setModel(tableModel);
		//tableModel.fireTableDataChanged();

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

	@Override
	public void columnAdded(TableColumnModelEvent e) {
		super.columnAdded(e);
		EventBus.publish(new ResizeRecordPanel());
	}

	@Override
	public void columnRemoved(TableColumnModelEvent e) {
		super.columnRemoved(e);
		EventBus.publish(new ResizeRecordPanel());
	}
	
	@Override
	protected JComponent createDefaultColumnControl() {
		return new CustomColumnControlButton(this, null);
	}
		
}
