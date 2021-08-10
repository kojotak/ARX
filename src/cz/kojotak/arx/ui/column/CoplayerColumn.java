/**
 * 
 */
package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;
/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class CoplayerColumn extends BaseColumn<Score, String> {

	private static final long serialVersionUID = -6622361025347639519L;

	@Override
	public String getValue(Score record) {
		return record.secondPlayer() != null ? record.secondPlayer().nick() : "";
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
