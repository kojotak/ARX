/**
 * 
 */
package cz.kojotak.arx.common;

/**
 * @date 26.9.2010
 * @author Kojotak 
 */
public interface RunnableWithProgress extends Runnable {
	
	/** unknown maximum progress */
	public static final int UNKNOWN=-1;

	/** maximum progress, counting from zero, if unknown, {@link #UNKNOWN} can be returned instead*/
	int max();
	
	/** current progress between 0 and {@link #max()}*/
	int current();
	
}
