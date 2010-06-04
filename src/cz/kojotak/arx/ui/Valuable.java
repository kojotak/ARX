/**
 * 
 */
package cz.kojotak.arx.ui;

/**
 * Simple type safe provider contract
 * @date 7.2.2010
 * @author Kojotak 
 */
public interface Valuable<T,V> {
	
	/**
	 * @return value from source
	 */
	V getValue(T source);
	
	/**
	 * @return class type of {@link #getValue(Object)}
	 */
	Class<V> getType();
}
