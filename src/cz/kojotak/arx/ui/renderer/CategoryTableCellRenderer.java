/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.ui.icon.EmptyIcon;

public class CategoryTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setValue(Object value) {
		Category t = (Category)value;
		Application app=Application.getInstance();
		String cico=  app.getIcons().getOptionalString("Category", ""+t.id());
		
		Icon icon=null;
		if(cico!=null){
			icon =app.getIconLoader().loadIcon(cico);
		}else{
			icon = new EmptyIcon();
		}
		this.setIcon(icon);
		if(t!=null){
			this.setToolTipText(t.name());		
		}
	}

}
