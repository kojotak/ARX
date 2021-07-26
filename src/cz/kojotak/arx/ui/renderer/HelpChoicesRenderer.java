package cz.kojotak.arx.ui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.HelpComboBox;
import cz.kojotak.arx.ui.icon.EmptyIcon;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @author TBe
 */
public class HelpChoicesRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 5076603464105256198L;
	private HelpComboBox combo;

	public HelpChoicesRenderer(HelpComboBox parent) {
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
			icon = app.getIconLoader().tryLoadIcon(GUIIcons.HELP);
			string = HelpComboBox.LOC_KEY;
		}
		String text=app.getLocalization().getString(combo, string);
		this.setText(text);
		this.setIcon(icon);
		return this;
	}
}
