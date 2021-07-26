/**
 * 
 */
package cz.kojotak.arx.util;

import java.util.Enumeration;

/**
 * Empty enumeration implementation
 * 
 * @date 24.5.2010
 * @author Kojotak
 */
public class EmptyEnumeration<E> implements Enumeration<E> {
	
	public static final EmptyEnumeration<String> STRING_SINGLETON = new EmptyEnumeration<String>();

	@Override
	public boolean hasMoreElements() {
		return false;
	}

	@Override
	public E nextElement() {
		return null;
	}

}
