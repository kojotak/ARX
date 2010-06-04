/**
 * 
 */
package cz.kojotak.arx.ui.icon;

import cz.kojotak.arx.ui.UpdateComboBox;

/**
 * Icons for update menu
 * @see UpdateComboBox
 * @date 24.5.2010
 * @author Kojotak
 */
public enum UpdateActionIcons {
	PROGRAM(UpdateComboBox.PROGRAM),
	DATABASE(UpdateComboBox.DATABASE),
	AVAILIBILITY(UpdateComboBox.AVAILIBILITY);
	
	private final String actionName;
	private UpdateActionIcons(String actionName) {
		this.actionName = actionName;
	}

	public static UpdateActionIcons resolveFromName(String name){
		UpdateActionIcons[] values = UpdateActionIcons.values();
		for(UpdateActionIcons val:values){
			if(val.actionName.equals(name)){
				return val;
			}
		}
		return null;		
	}
}
