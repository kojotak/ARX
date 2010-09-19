/**
 * 
 */
package cz.kojotak.arx.ui;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
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
import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.Searchable;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.WithStatistics;
import cz.kojotak.arx.domain.enums.Platform;
import cz.kojotak.arx.domain.impl.SimpleUser;
import cz.kojotak.arx.domain.impl.SingleGameStatistics;
import cz.kojotak.arx.ui.column.CustomColumnControlButton;
import cz.kojotak.arx.ui.event.FilterEvent;
import cz.kojotak.arx.ui.event.RebuiltGameTable;
import cz.kojotak.arx.ui.listener.GameTableSelectListener;
import cz.kojotak.arx.ui.model.GenericTableModel;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class GameTable extends JXTable {

	private static final long serialVersionUID = -4069244471240497960L;
			
	public GameTable(GenericTableModel<?> dm, TableColumnModel cm,RecordTable rec) {
		super(dm, cm);
		AnnotationProcessor.process(this);
		GameTableSelectListener gameSelectListener = new GameTableSelectListener(
				this, rec);
		this.getSelectionModel().addListSelectionListener(gameSelectListener);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		EventBus.publish(new RebuiltGameTable());
		this.getSelectionModel().setSelectionInterval(0, 0);
		this.setColumnControlVisible(true);
	}
	
	/**
	 * @return {@link GenericTableModel} of this table or throw an {@link IllegalStateException}
	 */
	public GenericTableModel<?> getGenericTableModel(){
		TableModel tm = this.getModel();
		if(tm instanceof GenericTableModel<?>){
			return GenericTableModel.class.cast(tm);
		}else{
			throw new IllegalStateException("Game table has uknown table model!");
		}
	}
	
	public void filterChanged(){
		final Application app = Application.getInstance();
		app.getLogger(this).debug("setting game table filter");
		GenericTableModel<?> model = this.getGenericTableModel();
		RowFilter<GenericTableModel<?>,Integer> categoryFilter = new RowFilter<GenericTableModel<?>,Integer>() {

			@Override
			public boolean include(
					Entry<? extends GenericTableModel<?>, ? extends Integer> entry) {
				Mode<?> mode = app.getCurrentMode();
				if(mode instanceof Searchable){
					Searchable searchable = Searchable.class.cast(mode);
					FilterEvent model = searchable.getFilter();
					Category filterCat = model.getCategory();
					Platform platform = model.getPlatform();
					GenericTableModel<?> m = entry.getModel();
					Integer id = entry.getIdentifier();
					Game o = (Game)m.getItem(id);
					String searchStr = model.getSearch();
					boolean nameOk=true;
					boolean platformOk =true;
					boolean catOk=true;
					if(o!=null){
						platformOk = platform==null?true:platform.equals(o.getPlatform());
						catOk=filterCat==null?true:filterCat.equals(o.getCategory());
						String title = o.getTitle();
						if(title!=null && searchStr!=null && searchStr.length()>0){
							String titleIC=title.toLowerCase();
							String filterIC=searchStr.toLowerCase();
							nameOk = titleIC.contains(filterIC);
						}
					}
					
					return catOk && nameOk && platformOk;
				}else{
					return true;
				}
				
			}
			  
		};
		TableRowSorter<GenericTableModel<?>> sorter = new TableRowSorter<GenericTableModel<?>>(model);
		sorter.setRowFilter(categoryFilter);
		this.setRowSorter(sorter);
	}
	
	/**
	 * helper method to set new table model
	 */
	@EventSubscriber(eventClass=RebuiltGameTable.class)
	public void modeOrPlayerChanged(RebuiltGameTable event) {
		final Application app = Application.getInstance();
		Mode<?> mode = app.getCurrentMode();
		User user = app.getCurrentUser();
		app.getLogger(this).debug("setting new game table model for "+user+" and "+mode);
		
		@SuppressWarnings("unchecked")GenericTableModel<?> model = new GenericTableModel(mode.getGames(),mode.getColumns());
		Application.getInstance().getLogger(this).debug("new model has rows: "+model.getRowCount());
			
		
		if(Competetive.class.isAssignableFrom(mode.getGameType())){
			Application.getInstance().getLogger(this).trace("calculating statistics for user "+user+" in mode "+mode);
			for(Game game:mode.getGames()){
				if(!(game instanceof Competetive<?>)){
					throw new IllegalStateException("This model is not suitable for non competetive game");
				}
				@SuppressWarnings("unchecked")Competetive<Record> cmp = Competetive.class.cast(game);
				SingleGameStatistics stats = new SingleGameStatistics(cmp, user,new SimpleUser("VLD"));
				if(!(game instanceof WithStatistics)){
					throw new IllegalStateException("This model is not suitable for games without statistics");
				}
				WithStatistics ws = WithStatistics.class.cast(game);
				ws.setStatistics(stats);
			}
		}
		this.setModel(model);
		this.filterChanged();//have to apply filter on update table model
	}

	@Override
	protected JComponent createDefaultColumnControl() {
		return new CustomColumnControlButton(this,null);
	}
	
	
}
