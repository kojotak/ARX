/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import cz.kojotak.arx.domain.User;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public class SimpleUser implements User {
	
	private final String id;

	public SimpleUser(String sign) {
		super();
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
