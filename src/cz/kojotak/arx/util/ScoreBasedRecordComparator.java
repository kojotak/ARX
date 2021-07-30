package cz.kojotak.arx.util;

import java.util.Comparator;

import cz.kojotak.arx.domain.impl.Record;

public enum ScoreBasedRecordComparator implements Comparator<Record> {
	INSTANCE;

	@Override
	public int compare(Record o1, Record o2) {
		int diff = -1*o1.getScore().compareTo(o2.getScore());
		if(diff==0){
			diff = o1.getDuration().compareTo(o2.getDuration());
		}
		return diff;
	}

	

}
