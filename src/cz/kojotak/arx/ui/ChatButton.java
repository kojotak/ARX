/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class ChatButton extends BaseButton implements ActionListener{

	private static final long serialVersionUID = -9050492894315708044L;

	private final MainWindow window;
	boolean expanded;
	private final Application app;
	private final Logger logger;

	public ChatButton(final MainWindow window) {
		super();
		this.app = Application.getInstance();
		this.logger = app.getLogger(this);
		this.window = window;
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.CHAT));
		this.setText(app.getLocalization().getString(this, "LABEL"));
		expanded=false;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		expanded=!expanded;
//		window.getSplitter().setDividerLocation(expanded?0.5:1);
//		logger.debug("chat "+(expanded?"expanded":"collapsed"));
		JComponent toRemove = expanded?window.getSplitter():window.getUpperPanel();
		JComponent toAdd = expanded?window.getUpperPanel():window.getSplitter();
		JComponent panel = window.getCenterPanel();
				
		panel.remove(toRemove);
		panel.add(toAdd,BorderLayout.CENTER);
		if(!expanded){
			window.getSplitter().setLeftComponent(window.getUpperPanel());
		}
		panel.invalidate();
		window.pack();
	}
}
