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
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.impl.GameStatistics;
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

	private static final long serialVersionUID = -4069244471240497960L;
	private final Logger logger;
	
	public GameTable(GenericTableModel<?> dm, TableColumnModel cm) {
		super(dm, cm);
		AnnotationProcessor.process(this);
		logger =Application.getInstance().getLogger(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.getSelectionModel().setSelectionInterval(0, 0);
		this.setColumnControlVisible(true);
		this.user=Application.getInstance().getCurrentUser();
		this.mode=Application.getInstance().getCurrentMode();
		this.recalculate();		
	}

	/**
	 * @return {@link GenericTableModel} of this table or throw an
	 *         {@link IllegalStateException}
	 */
	public GenericTableModel<?> getGenericTableModel() {
		TableModel tm = this.getModel();
		if (tm instanceof GenericTableModel<?>) {
			return GenericTableModel.class.cast(tm);
		} else {
			throw new IllegalStateException(
					"Game table has uknown table model!");
		}
	}

	private FilterModel filterModel = new FilterModel();

	@EventSubscriber
	public void updateGameFilter(FilterModel update) {
		this.filterModel = FilterModel.updateWith(this.filterModel, update);

		logger.fine("setting game table filter");
		GenericTableModel<?> model = this.getGenericTableModel();
		RowFilter<GenericTableModel<?>, Integer> categoryFilter = new RowFilter<GenericTableModel<?>, Integer>() {

			@Override
			public boolean include(
					Entry<? extends GenericTableModel<?>, ? extends Integer> entry) {

				Category filterCat = filterModel.getCategory();
				if (Category.VSECHNY.equals(filterCat)) {
					filterCat = null;
				}
				LegacyPlatform platform = filterModel.getPlatform();
				if (LegacyPlatform.ALL.equals(platform)) {
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

	private Mode<?> mode=null;
	private User user=null;
	private User opponent=null;
	
	@EventSubscriber
	public void updateMode(Mode<?> mode){
		this.mode=mode;
		GenericTableColumnModel cm=new GenericTableColumnModel(mode);
		this.setColumnModel(cm);//got new column model
		logger.info("setting new mode: "+this.mode);
		this.recalculate();
	}
	
	@EventSubscriber
	public void updateOpponent(OpponentChosen opponent){
		this.opponent = opponent.getOpponent();
		logger.info("setting new opponent: "+this.opponent);
		recalculate();
	}
	
	@EventSubscriber
	public void updateUser(User user){
		this.user=user;
		logger.info("setting new user: "+this.user);
		recalculate();
	}
	
	/**
	 * helper method to set new table model
	 */
	private void recalculate() {
		final Application app = Application.getInstance();
		Application.getLogger(this).fine(
				"setting new game table model for " + user + " and " + mode);

		@SuppressWarnings("unchecked")
		GenericTableModel<?> model = new GenericTableModel(mode.getGames(),	mode.getColumns());
		Application.getLogger(this).fine(
				"new model has rows: " + model.getRowCount());

		if (Competetive.class.isAssignableFrom(mode.getGameType())) {
			Application.getLogger(this).fine(
					"calculating statistics for user " + user + " in mode "
							+ mode);
			for (Game game : mode.getGames()) {
				if (!(game instanceof Competetive)) {
					throw new IllegalStateException(
							"This model is not suitable for non competetive game");
				}
				Competetive cmp = Competetive.class.cast(game);
				GameStatistics stats = new GameStatistics(cmp, user, opponent);
				if (!(game instanceof WithStatistics)) {
					throw new IllegalStateException(
							"This model is not suitable for games without statistics");
				}
				WithStatistics ws = WithStatistics.class.cast(game);
				ws.setStatistics(stats);
			}
		}
		this.setModel(model);
		this.updateGameFilter(null);// have to apply filter on update table model
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
			Application.getLogger(this).log(Level.WARNING, "invalid row index in game table model",ex);
		}
		
	}

}
