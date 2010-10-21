/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Record2P;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class CoplayerColumn extends BaseColumn<Record2P,String> {

	private static final long serialVersionUID = -6622361025347639519L;

	@Override
	public String getValue(Record2P record) {
		return record.getSecondPlayer();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
