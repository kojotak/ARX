/**
 * 
 */
package cz.kojotak.arx.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXTable;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.GameStatistics;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Player;
import cz.kojotak.arx.domain.enums.LegacyCategory;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.ui.column.CustomColumnControlButton;
import cz.kojotak.arx.ui.event.FilterModel;
import cz.kojotak.arx.ui.event.OpponentChosen;
import cz.kojotak.arx.ui.model.GenericTableColumnModel;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class GameTable extends JXTable {

	private static final long serialVersionUID = 1L;
	private final Logger logger;
	private FilterModel filterModel = new FilterModel();
	
	public GameTable(GenericTableModel<?> dm, TableColumnModel cm) {
		super(dm, cm);
		AnnotationProcessor.process(this);
		logger = Logger.getLogger(getClass().getName());
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.getSelectionModel().setSelectionInterval(0, 0);
		this.setColumnControlVisible(true);
		this.player=Application.getInstance().getCurrentPlayer();
		this.mode=Application.getInstance().getCurrentMode();
		FilterModel filter = new FilterModel();
		filterModel.setPlatform(LegacyPlatform.MAME.toPlatform());
		this.updateGameFilter(filter);
		this.recalculate();	
	}

	public GenericTableModel<?> getGenericTableModel() {
		TableModel tm = this.getModel();
		if (tm instanceof GenericTableModel<?>) {
			return GenericTableModel.class.cast(tm);
		} else {
			throw new IllegalStateException("Game table has uknown table model!");
		}
	}

	@EventSubscriber
	public void updateGameFilter(FilterModel update) {
		this.filterModel = FilterModel.updateWith(this.filterModel, update);

		logger.fine("setting game table filter " + update);
		GenericTableModel<?> model = this.getGenericTableModel();
		RowFilter<GenericTableModel<?>, Integer> categoryFilter = new RowFilter<GenericTableModel<?>, Integer>() {

			@Override
			public boolean include(
					Entry<? extends GenericTableModel<?>, ? extends Integer> entry) {

				Category filterCat = filterModel.getCategory();
				if (LegacyCategory.VSECHNY.toCategory().equals(filterCat)) {
					filterCat = null;
				}
				Platform platform = filterModel.getPlatform();
				if (LegacyPlatform.ALL.toPlatform().equals(platform)) {
					platform = null;
				}
				GenericTableModel<?> m = entry.getModel();
				Integer id = entry.getIdentifier();
				Game o = (Game) m.getItem(id);
				String searchStr = filterModel.getSearch();
				boolean nameOk = true;
				boolean platformOk = true;
				boolean catOk = true;
				if (o != null) {
					platformOk = platform == null ? true : platform.equals(o
							.getPlatform());
					catOk = filterCat == null ? true : filterCat.equals(o
							.getCategory());
					String title = o.getTitle();
					if (title != null && searchStr != null
							&& searchStr.length() > 0) {
						String titleIC = title.toLowerCase();
						String filterIC = searchStr.toLowerCase();
						nameOk = titleIC.contains(filterIC);
					}
				}

				return catOk && nameOk && platformOk;
			}

		};
		TableRowSorter<GenericTableModel<?>> sorter = new TableRowSorter<GenericTableModel<?>>(
				model);
		sorter.setRowFilter(categoryFilter);
		this.setRowSorter(sorter);
	}

	private Mode mode=null;
	private Player player=null;
	private Player opponent=null;
	
	@EventSubscriber
	public void updateMode(Mode mode){
		this.mode=mode;
		GenericTableColumnModel cm=new GenericTableColumnModel(mode);
		this.setColumnModel(cm);//got new column model
		logger.info("setting new mode: "+this.mode);
		recalculate();
	}
	
	@EventSubscriber
	public void updateOpponent(OpponentChosen event){
		this.opponent = event.opponent();
		logger.info("setting new opponent: "+this.opponent);
		recalculate();
	}
	
	@EventSubscriber
	public void updatePlayer(Player player){
		this.player=player;
		logger.info("setting new player: "+this.player);
		recalculate();
	}
	
	/**
	 * helper method to set new table model
	 */
	private void recalculate() {
		logger.fine("setting new game table model for " + player + " and " + mode);

		GenericTableModel<?> model = new GenericTableModel(mode.getGames(),	mode.getColumns());
		logger.fine("new model has rows: " + model.getRowCount());
		logger.fine("calculating statistics for player " + player + " in mode "+ mode);
		for (Game game : mode.getGames()) {
			GameStatistics stats = new GameStatistics(mode.getScores(game), player, opponent);
			game.setStatistics(stats);
		}
		this.setModel(model);
	}

	@Override
	protected JComponent createDefaultColumnControl() {
		return new CustomColumnControlButton(this, null);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		super.valueChanged(e);

		if (e.getValueIsAdjusting()) {
			return;
		}

		ListSelectionModel rowSM = (ListSelectionModel) e.getSource();
		int idx = rowSM.getMinSelectionIndex();
		if (idx < 0) {
			idx = 0;
		}

		GenericTableModel<?> ggtm = getGenericTableModel();
		try{
			Game game = (Game)ggtm.getItem(convertRowIndexToModel(idx));
			EventBus.publish(game);
		}catch(Exception ex){//dirty and ugly
			logger.log(Level.WARNING, "invalid row index in game table model",ex);
		}
		
	}

}
