package cz.kojotak.arx.ui.column;

import cz.kojotak.arx.domain.Record;

public class RecordPlayerColumn extends BaseColumn<Record,String> {

	private static final long serialVersionUID = 7509518745442161319L;

	@Override
	public String getValue(Record source) {
		return source.getPlayer();
	}
	@Override
	public Class<String> getType() {
		return String.class;
	}

}
