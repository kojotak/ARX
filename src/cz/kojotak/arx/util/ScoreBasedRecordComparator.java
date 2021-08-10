package cz.kojotak.arx.util;

import java.util.Comparator;

import cz.kojotak.arx.domain.Score;

public enum ScoreBasedRecordComparator implements Comparator<Score> {
	INSTANCE;

	@Override
	public int compare(Score o1, Score o2) {
		int diff = -1* Long.compare(o1.score(), o2.score());
		if(diff==0){
			diff = o1.duration().compareTo(o2.duration());
		}
		return diff;
	}

}
