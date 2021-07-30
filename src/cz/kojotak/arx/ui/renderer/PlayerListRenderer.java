/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.ui.PlayerComboBox;
import cz.kojotak.arx.ui.icon.EmptyIcon;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 19.4.2010
 * @author Kojotak 
 */
public class PlayerListRenderer extends JLabel implements ListCellRenderer<User> {

	private static final long serialVersionUID = -8067946040669163322L;
	transient private Application app;
	
	public PlayerListRenderer() {
		super();
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
		app=Application.getInstance();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User string,
			int index, boolean isSelected, boolean cellHasFocus) {
		Icon icon = null;
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		if(index<0){
			icon = app.getIconLoader().tryLoadIcon(GUIIcons.USER);
		}else{
			icon=new EmptyIcon();
		}
//		if(PlayerComboBox.ADD_NEW.equals(string)){//FIXME or delete me
//			string=app.getLocalization().getString(this,PlayerComboBox.ADD_NEW);
//			icon= app.getIconLoader().tryLoadIcon(GUIIcons.ADD);
//		}
		this.setIcon(icon);
		this.setText(string.nick());
		return this;
	}

}
