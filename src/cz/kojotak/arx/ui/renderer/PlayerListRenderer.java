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
import cz.kojotak.arx.ui.PlayerComboBox;
import cz.kojotak.arx.ui.icon.EmptyIcon;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 19.4.2010
 * @author Kojotak 
 */
public class PlayerListRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = -8067946040669163322L;
	transient private Application app;
	
	public PlayerListRenderer() {
		super();
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
		app=Application.getInstance();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		String string = value.toString();
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
		if(PlayerComboBox.ADD_NEW.equals(string)){
			string=app.getLocalization().getString(this,PlayerComboBox.ADD_NEW);
			icon= app.getIconLoader().tryLoadIcon(GUIIcons.ADD);
		}
		this.setIcon(icon);
		this.setText(string);
		return this;
	}

}
