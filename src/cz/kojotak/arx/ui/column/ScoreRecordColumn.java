/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Record;

/**
 * @date 21.3.2010
 * @author Kojotak 
 */
public class ScoreRecordColumn extends RightAlignedNumberColumn<Record,Long> {

	private static final long serialVersionUID = 1491840483445159633L;

	@Override
	public Long getValue(Record source) {
		return source.getScore();
	}
	
	@Override
	public Class<Long> getType() {
		return Long.class;
	}
}
