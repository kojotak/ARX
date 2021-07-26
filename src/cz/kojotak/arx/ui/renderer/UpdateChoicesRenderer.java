package cz.kojotak.arx.ui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.UpdateComboBox;
import cz.kojotak.arx.ui.icon.EmptyIcon;
import cz.kojotak.arx.ui.icon.GUIIcons;
import cz.kojotak.arx.ui.icon.UpdateActionIcons;

/**
 * 
 * FIXME javadoc
 * @date 24.5.2010
 * @author Kojotak
 */
public class UpdateChoicesRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 5076603464105256198L;
	private UpdateComboBox combo;

	public UpdateChoicesRenderer(UpdateComboBox parent) {
		super();
		this.setOpaque(true);
		this.combo=parent;
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList list,
			Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Application app = Application.getInstance();
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		Icon icon=new EmptyIcon();
		String string = String.class.cast(value);
		if(index<0){
			icon = app.getIconLoader().tryLoadIcon(GUIIcons.REFRESH);
			string = UpdateComboBox.LOC_KEY;
		}else{
			String option = UpdateComboBox.CHOICES[index];
			UpdateActionIcons action = UpdateActionIcons.resolveFromName(option);
			icon = app.getIconLoader().tryLoadIcon(action);
		}
		String text=app.getLocalization().getString(combo, string);
		this.setText(text);
		this.setIcon(icon);
		return this;
	}
}
