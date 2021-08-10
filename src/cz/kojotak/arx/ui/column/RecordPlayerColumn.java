package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Score;

public class RecordPlayerColumn extends BaseColumn<Score,String> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getValue(Score source) {
		return source.player().nick();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
