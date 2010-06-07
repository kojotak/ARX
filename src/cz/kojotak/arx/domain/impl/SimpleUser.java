/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import lombok.Getter;
import cz.kojotak.arx.domain.User;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public class SimpleUser implements User {
	
	@Getter
	private String id;

	public SimpleUser(String sign) {
		super();
		this.id = sign;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+":"+getId();
	}
	
}
