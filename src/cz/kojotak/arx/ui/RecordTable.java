/**
 *
 */
package cz.kojotak.arx.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXTable;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.domain.mode.TwoPlayerMode;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CoplayerColumn;
import cz.kojotak.arx.ui.column.CustomColumnControlButton;
import cz.kojotak.arx.ui.column.FinishedRecordColumn;
import cz.kojotak.arx.ui.column.PositionColumn;
import cz.kojotak.arx.ui.column.ScoreDurationColumn;
import cz.kojotak.arx.ui.column.ScorePlayerColumn;
import cz.kojotak.arx.ui.column.ScorePointsColumn;
import cz.kojotak.arx.ui.column.ScoreRecordColumn;
import cz.kojotak.arx.ui.event.ResizeRecordPanel;
import cz.kojotak.arx.ui.model.GenericTableColumnModel;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 7.2.2010
 * @author Kojotak
 */
public class RecordTable extends JXTable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RecordTable.class.getName());	
	
	private List<BaseColumn<?,?>> cols;

	private RecordTable(List<BaseColumn<?,?>> cols) {
		super(createTableModel(cols), createColumnModel(cols));
		this.cols = cols;
		AnnotationProcessor.process(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setColumnControlVisible(true);
	}
	
	public static RecordTable createRecordTable(Mode mode){
		List<BaseColumn<?,?>> cols = createColumns(mode);
		RecordTable rt = new RecordTable(cols);
		return rt;
	}
	
	private static List<BaseColumn<?,?>> createColumns(Mode mode){
		var scorePointsColumn = new ScorePointsColumn();
		var secondPlayerColumn = new CoplayerColumn();
		
		scorePointsColumn.setVisible(false);
		secondPlayerColumn.setVisible(mode instanceof TwoPlayerMode);
		
		List<BaseColumn<?,?>> cols = new ArrayList<BaseColumn<?,?>>();
		cols.add(new PositionColumn());
		cols.add(new ScorePlayerColumn());
		cols.add(secondPlayerColumn);
		cols.add(new ScoreRecordColumn());
		cols.add(scorePointsColumn);
		cols.add(new ScoreDurationColumn());
		cols.add(new FinishedRecordColumn());
		return cols;
	}
	
	private static TableModel createTableModel(List<BaseColumn<?,?>> cols) {
		return new GenericTableModel(Collections.emptyList(),cols);
	}
	
	private static GenericTableColumnModel createColumnModel(List<BaseColumn<?,?>> cols) {
		return new GenericTableColumnModel(cols); 
	}

	@EventSubscriber
	public void onModeChange(Mode mode){
		for(BaseColumn<?, ?> col: cols) {
			if(col instanceof CoplayerColumn) {
				col.setVisible(mode instanceof TwoPlayerMode);
			}
		}
		
		EventBus.publish(new ResizeRecordPanel());
	}
	
	@EventSubscriber
	public void onGameChange(Game game){
		List<Score> records = Application.getInstance().getCurrentMode().getScores(game);
		logger.info("onGameChange: " + game + ", scores: " + records.size()+", mode: "+Application.getInstance().getCurrentMode());
		@SuppressWarnings("unchecked")GenericTableModel<?> tableModel = new GenericTableModel(records,cols);
		this.setModel(tableModel);
		//tableModel.fireTableDataChanged();

		GameStatistics gs = game.getStatistics();
		if(gs!=null){
			Integer position = gs.getPlayerPosition();
			if(position!=null){
				int pos=--position;
				this.getSelectionModel().setSelectionInterval(pos, pos);
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
