/**
 * 
 */
package cz.kojotak.arx.domain.impl;

import lombok.Getter;
import lombok.Setter;
import cz.kojotak.arx.domain.Record2P;

/**
 * @date 26.1.2010
 * @author Kojotak 
 */
public class Record2PImpl extends RecordImpl implements Record2P {
	
	@Getter
	@Setter
	protected String secondPlayer;

}
