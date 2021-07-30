/**
 * 
 */
package cz.kojotak.arx.domain.impl;


/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public class User {
	
	private final String id;

	public User(String sign) {
		this.id = sign;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+getId();
	}

	public String getId() {
		return id;
	}
	
}
