/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.event.ResizeRecordPanel;

/**
 * @date 27.1.2010
 * @author Kojotak
 */
public class RecordPanel extends JPanel {


	private static final long serialVersionUID = -8847662693620484228L;

	RecordTable table;
	JScrollPane scrollbars;
	MainWindow window;

	public RecordPanel(final MainWindow window) {
		super();
		AnnotationProcessor.process(this);
		//this.setPreferredSize(new Dimension(182,this.getHeight()));
		table = new RecordTable(Application.getInstance().getCurrentMode());
		this.window = window;
				
		scrollbars = new JScrollPane();
		scrollbars.setViewportView(table);
		scrollbars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setLayout(new BorderLayout());
		this.add(scrollbars,BorderLayout.CENTER);
	}
		
	@EventSubscriber
	public void resizePanel(ResizeRecordPanel resize){
		int height = table.getPreferredSize().height;
		int width = table.getPreferredSize().width
			+ scrollbars.getVerticalScrollBar().getPreferredSize().width
			+ 6 /* FIXME remove this magic constant*/
			;
		Dimension dim = new Dimension(width,height);
		this.setPreferredSize(dim);
		this.revalidate();
	}

}
