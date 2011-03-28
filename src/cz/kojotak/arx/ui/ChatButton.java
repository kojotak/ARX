/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class ChatButton extends JToggleButton implements ActionListener{

	private static final long serialVersionUID = -9050492894315708044L;

	private final MainWindow window;
	boolean expanded;
	private final Application app;

	public ChatButton(final MainWindow window) {
		super();
		this.setMargin(new Insets(0,0,0,0));
		this.app = Application.getInstance();
		this.window = window;
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.CHAT));
		this.setText(app.getLocalization().getString(this, "LABEL"));
		this.addActionListener(this);
		expanded=true;
		this.setSelected(expanded);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
		expanded=!expanded;
	}
}
