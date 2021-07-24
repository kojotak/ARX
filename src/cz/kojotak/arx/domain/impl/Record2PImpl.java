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
	
	private String secondPlayer;

	public String getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(String secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

}
