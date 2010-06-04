/**
 *
 */
package cz.kojotak.arx.ui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cz.kojotak.arx.ui.icon.EmptyIcon;

/**
 * @author TBe
 */
public class IconizedTextListRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = -4809120910858745683L;

	Icon icon;

	public IconizedTextListRenderer(Icon icon) {
		super();
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
		this.icon=icon;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing
	 * .JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		String text = String.class.cast(value);

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		Icon icon=null;
		if(index<0){
			icon = this.icon;
		}else{
			icon=new EmptyIcon();
		}
		setIcon(icon);
		setText(text);
		return this;
	}

}
