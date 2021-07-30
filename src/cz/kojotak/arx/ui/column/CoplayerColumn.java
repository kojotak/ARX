/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.impl.Record;
/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class CoplayerColumn extends BaseColumn<Record, String> {

	private static final long serialVersionUID = -6622361025347639519L;

	@Override
	public String getValue(Record record) {
		return record.getSecondPlayer() != null ? record.getSecondPlayer().nick() : "";
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
