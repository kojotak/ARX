/**
 * 
 */
package cz.kojotak.arx.properties;

import cz.kojotak.arx.Properties;
import cz.kojotak.arx.domain.Language;



/**
 * @date 22.10.2010
 * @author Kojotak 
 */
public class Licence extends Properties {
	
	/** name/identifier of licensed item */
	public static final String PROP_NAME="NAME";
	
	/** description of licensed item */
	public static final String PROP_DESC="DESC";
	
	/** optional URL of of licensed item */
	public static final String PROP_URL="URL";

	/** optional file name with more details */
	public static final String PROP_FILE="FILE";

	/** license name description */
	public static final String PROP_LIC="LIC";

	/** header of license list */
	public static final String HEADER="HEADER";
	
	public static final String FILE_LINK="LINK";
	
	public static enum Item{
		CHM_PANE,EVENT_BUS,FAMFAMFAM,IMAGE4J,LOG4J,LOMBOK,MAMU,ROTAXMAME,SWING_X
	}

	public Licence(Language lang) {
		super(lang);
	}

}
