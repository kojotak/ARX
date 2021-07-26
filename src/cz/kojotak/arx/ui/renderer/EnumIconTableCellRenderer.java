/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ui.icon.EmptyIcon;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class EnumIconTableCellRenderer<T extends Enum<?>> extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 106691423222953976L;

	@Override
	protected void setValue(Object value) {
		@SuppressWarnings("unchecked")T t = (T)value;
		Application app=Application.getInstance();
		String cico=  app.getIcons().getOptionalString(t);
		
		Icon icon=null;
		if(cico!=null){
			icon =app.getIconLoader().loadIcon(cico);
		}else{
			icon = new EmptyIcon();
		}
		this.setIcon(icon);
		if(t!=null){
			this.setToolTipText(app.getLocalization().getOptionalString(t));		
		}
	}

}
