/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

/**
 * @date 27.1.2010
 * @author Kojotak
 */
public class RecordPanel extends JPanel implements PropertyChangeListener {


	private static final long serialVersionUID = -8847662693620484228L;

	RecordTable table;
	JScrollPane scrollbars;
	MainWindow window;

	public RecordPanel(final MainWindow window) {
		super();
		//this.setPreferredSize(new Dimension(182,this.getHeight()));
		table = new RecordTable();
		this.window = window;
		
		Enumeration<TableColumn> cols = table.getColumnModel().getColumns();
		while(cols.hasMoreElements()){
			TableColumn col = cols.nextElement();
			col.addPropertyChangeListener(this);
		}
		
		scrollbars = new JScrollPane();
		scrollbars.setViewportView(table);
		scrollbars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		updatePreferredSize();
		this.setLayout(new BorderLayout());
		this.add(scrollbars,BorderLayout.CENTER);
	}
	
	private void updatePreferredSize(){
		int height = table.getPreferredSize().height;
		int width = table.getPreferredSize().width
			+ scrollbars.getVerticalScrollBar().getPreferredSize().width
			+ 6 /* FIXME remove this magic constant*/
			;
		Dimension dim = new Dimension(width,height);
		this.setPreferredSize(dim);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
			
				
			
		updatePreferredSize();		
		window.pack();
		
			}});
	}
	
	
}
