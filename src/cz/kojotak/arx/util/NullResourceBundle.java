/**
 * 
 */
package cz.kojotak.arx.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Null implementation of {@link ResourceBundle}
 * @date 24.5.2010
 * @author Kojotak
 */
public class NullResourceBundle extends ResourceBundle {
	
	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#getKeys()
	 */
	@Override
	public Enumeration<String> getKeys() {		
		return EmptyEnumeration.STRING_SINGLETON;
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	@Override
	protected Object handleGetObject(String key) {
		return null;
	}

}
