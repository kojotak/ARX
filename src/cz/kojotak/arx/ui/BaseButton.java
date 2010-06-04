/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.Insets;

import javax.swing.JButton;

/**
 * @date 31.3.2010
 * @author Kojotak 
 */
public class BaseButton extends JButton{

	private static final long serialVersionUID = 4001287632789112032L;

	protected BaseButton() {
		super();
		this.setMargin(new Insets(0,0,0,0));
	}

}
