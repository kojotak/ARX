/**
 * 
 */
package cz.kojotak.arx.ui.renderer;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.common.Identifiable;
import cz.kojotak.arx.common.Named;
import cz.kojotak.arx.ui.icon.EmptyIcon;

public class NamedWithIdTableCellRenderer<T extends Identifiable & Named> extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private final Class<T> clz;

	public NamedWithIdTableCellRenderer(Class<T> clz) {
		super();
		this.clz = clz;
	}

	@Override
	protected void setValue(Object value) {
		T t = clz.cast(value);
		Application app=Application.getInstance();
		String cico=  app.getIcons().getOptionalString(clz.getSimpleName(), ""+t.id());
		
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
