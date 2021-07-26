/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.Insets;

import javax.swing.JButton;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class SettingsButton extends JButton {

	private static final long serialVersionUID = -7957623933341608459L;

	public SettingsButton() {
		super();
		this.setMargin(new Insets(0,0,0,0));
		this.setIcon(Application.getInstance().getIconLoader().tryLoadIcon(GUIIcons.SETTINGS));
		this.setText(Application.getInstance().getLocalization().getString(this, "LABEL"));
	}

}
