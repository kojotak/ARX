/**
 * 
 */
package cz.kojotak.arx.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @date 26.9.2010
 * @author Kojotak 
 */
public class Holder<T> {
	
	@Getter
	@Setter
	T holded;
}
