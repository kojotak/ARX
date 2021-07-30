package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Record;

public class RecordPlayerColumn extends BaseColumn<Record,String> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getValue(Record source) {
		return source.getPlayer().nick();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
