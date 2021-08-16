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
import cz.kojotak.arx.common.Identifiable;
import cz.kojotak.arx.common.Named;
import cz.kojotak.arx.ui.icon.EmptyIcon;

public class NamedWithIdListCellRenderer<T extends Identifiable & Named> extends JLabel implements ListCellRenderer<T> {

	private static final long serialVersionUID = 1L;

	public NamedWithIdListCellRenderer() {
		super();
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
		Application app = Application.getInstance();
		if (value == null) {
			return new JLabel();// avoid class cast exception
		}

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		Icon icon = null;
		String icoName = app.getIcons().getOptionalString("Category", ""+value.id());
		if (icoName != null) {
			icon = app.getIconLoader().loadIcon(icoName);
		} else {
			icon = new EmptyIcon();
		}

		setIcon(icon);
		setText(value.name());
		return this;
	}

}
