/**
 *
 */
package cz.kojotak.arx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 28.3.2010
 * @author Kojotak
 */
public class ChatButton extends BaseButton implements ActionListener{

	private static final long serialVersionUID = -9050492894315708044L;

	JSplitPane split;
	boolean expanded;

	public ChatButton(final JSplitPane split) {
		super();
		this.setIcon(Application.getInstance().getIconLoader().tryLoadIcon(GUIIcons.CHAT));
		this.setText(Application.getInstance().getLocalization().getString(this, "LABEL"));
		this.split=split;
		expanded=false;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//split.s
		expanded=!expanded;
		split.setDividerLocation(expanded?0.5:1);
		Application.getInstance().getLogger(this).debug("chat "+(expanded?"expanded":"collapsed"));
	}
}
