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
	
	private String sign;

	public SimpleUser(String sign) {
		super();
		this.sign = sign;
	}


	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.Identifiable#getId()
	 */
	@Override
	public String getId() {
		return sign;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+getId();
	}
	
	

}
