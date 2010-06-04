/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @date 27.1.2010
 * @author Kojotak
 */
public class RecordPanel extends JPanel {


	private static final long serialVersionUID = -8847662693620484228L;

	RecordTable table;

	public RecordPanel(final MainWindow window) {
		super();
		this.setPreferredSize(new Dimension(182,this.getHeight()));
		table = new RecordTable();
		JScrollPane scrollbars = new JScrollPane();
		scrollbars.setViewportView(table);
		scrollbars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setLayout(new BorderLayout());
		this.add(scrollbars,BorderLayout.CENTER);
	}
}
