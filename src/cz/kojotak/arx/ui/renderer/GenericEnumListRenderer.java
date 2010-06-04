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
import cz.kojotak.arx.ui.icon.EmptyIcon;

/**
 * @date 25.2.2010
 * @author Kojotak
 */
public class GenericEnumListRenderer<T extends Enum<?>> extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = -2126498516703851009L;
	private Class<T> enumType;
	
	/**
	 * this enum's instance represents "all" option
	 */
	private T allValue;

	public GenericEnumListRenderer(T allValue,Class<T> enumType) {
		super();
		this.allValue=allValue;
		this.enumType=enumType;
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
		
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
		Application app = Application.getInstance();
		T item = enumType.cast(value);

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		Icon icon=null;
		String text=null;
		if(item==null || item.equals(allValue)){
			icon = new EmptyIcon();
			if(allValue==null){
				text=app.getLocalization().getString(this, "ALL");
			}else{
				text=app.getLocalization().getString(allValue);
			}
		}else if(item!=null){
			String icoName = app.getIcons().getOptionalString(item);
			if(icoName!=null){
				icon =app.getIconLoader().loadIcon(icoName);
			}else{
				icon = new EmptyIcon();
			}
			text=Application.getInstance().getLocalization().getString(item);
		}
		
		setIcon(icon);
		setText(text);
		return this;
	}

}
