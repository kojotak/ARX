/**
 *
 */
package cz.kojotak.arx.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JComboBox;

import org.bushe.swing.event.EventBus;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.ui.FilterTaskPane;
import cz.kojotak.arx.ui.MainWindow;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class ModeChangeListener implements ActionListener {

	private MainWindow window;
	private static final Logger logger = Logger.getLogger(ModeChangeListener.class.getName());
	
	public ModeChangeListener(MainWindow window) {
		super();
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JComboBox<String> cb = (JComboBox<String>) event.getSource();
		String name = (String) cb.getSelectedItem();
		Application app = Application.getInstance();
		Mode mode = app.resolveMode(name);
		Application.getInstance().setCurrentMode(mode);
		//window.changeTable();//getGameTable().updateTableModel();
		FilterTaskPane filter = window.getSidebar().getFilter();
		//filter.getCategoryComboBox().updateCategoryListModel();
		//filter.getPlatformComboBox().updateListModel();
		filter.arrangeFilter();
		//window.switchRecordPanel();
		EventBus.publish(mode);
		logger.info("selected mode " + name);
	}

}
