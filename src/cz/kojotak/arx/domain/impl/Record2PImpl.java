/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import cz.kojotak.arx.domain.Record2P;

/**
 * @date 26.1.2010
 * @author Kojotak 
 */
public class Record2PImpl extends RecordImpl implements Record2P {
	protected String secondUser;

	
	/* (non-Javadoc)
	 * @see cz.kojotak.arx.domain.Record2P#getSecondPlayer()
	 */
	@Override
	public String getSecondPlayer() {
		return secondUser;
	}

	
	public void setSecondUser(String secondUser) {
		this.secondUser = secondUser;
	}

	
}
