/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class Sidebar extends JPanel {

	private static final long serialVersionUID = 1954740479797461511L;

	private FilterTaskPane filter;
	private PreferencesTaskPane preferences;
	
	public Sidebar() {
		super();
		this.setPreferredSize(new Dimension(256,this.getPreferredSize().height));
		
		JXTaskPaneContainer taskpanecontainer = new JXTaskPaneContainer();
		taskpanecontainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		taskpanecontainer.setBackground(this.getBackground());
		taskpanecontainer.setBorder(new EmptyBorder(0,0,0,0));
		taskpanecontainer.setAutoscrolls(true);

		this.preferences = new PreferencesTaskPane();
		this.filter = new FilterTaskPane();
	

		JXTaskPane details = new DetailTaskPane();

		taskpanecontainer.add(preferences);
		taskpanecontainer.add(filter);
		taskpanecontainer.add(details);

		this.add(taskpanecontainer);
	}

	public FilterTaskPane getFilter() {
		return filter;
	}

}
