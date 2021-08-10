/**
 *
 */
package cz.kojotak.arx.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.enums.Language;
import cz.kojotak.arx.util.NullResourceBundle;

/**
 * Helper wrapper around {@link ResourceBundle} with some (really? :-) helpfully methods.
 * //FIXME caching
 * @date 25.3.2010
 * @author Kojotak
 */
public class Properties{

	protected ResourceBundle bundle;
	public Properties(Language lang){
		try{
			bundle = ResourceBundle.getBundle(getClass().getName(),lang.getLocale());
		}catch(MissingResourceException ex){
			bundle = new NullResourceBundle();
		}
	}
	
	public String getString(Enum<?> e){
		if(e==null){
			throw new IllegalArgumentException("Cannot get string from null enum!");
		}
		return getString(e,e.name());
	}

	public String getString(Object owner,String property){
		if(owner==null || property==null){
			throw new IllegalArgumentException("Cannot get string for null owner or property");
		}
		String key = getKey(owner,property);
		try{
			return bundle.getString(key);
		}catch(MissingResourceException ex){
			Application.getLogger(this).severe("missing value for key "+key);
			return "";
		}
	}
	
	public String getOptionalString(Enum<?> e){
		if(e==null){
			return null;
		}
		return getOptionalString(e, e.name());
	}

	public String getOptionalString(Object owner,String property){
		if(owner==null || property==null){
			throw new IllegalArgumentException("cannot get property for null parameters");
		}
		String key = getKey(owner,property);
		try{
			return bundle.getString(key);
		}catch(MissingResourceException ex){
			return null;
		}
	}

	protected String getKey(Object owner,String property){
		StringBuilder sb = new StringBuilder(owner instanceof String?(String)owner:owner.getClass().getSimpleName());
		sb.append(".").append(property);
		return sb.toString();
	}

	public Integer getOptionalInteger(Object owner,String property){
		String str = getOptionalString(owner, property);
		if(str!=null && str.length()>0){
			Integer i=null;
			try{
				i = Integer.parseInt(str);
			}catch(NumberFormatException nfe){
				String key =getKey(owner, property);
				throw new IllegalStateException("Bad integer format for "+key+" and value "+str);
			}
			return i;
		}else{
			return null;
		}
	}

	public Integer getInteger(Object owner,String property){
		Integer i = getOptionalInteger(owner, property);
		if(i!=null){
			return i;
		}else{
			throw new IllegalStateException("Property "+property+" of "+owner.getClass().getSimpleName()+"is not an integer!");
		}
	}
}
