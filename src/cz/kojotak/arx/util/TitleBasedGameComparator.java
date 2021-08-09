/**
 * 
 */
package cz.kojotak.arx.util;

import java.util.Comparator;

import cz.kojotak.arx.domain.Game;

/**
 * @date 24.1.2010
 * @author Kojotak 
 */
public enum TitleBasedGameComparator implements Comparator<Game> {
	INSTANCE;

	@Override
	public int compare(Game arg0, Game arg1) {
		String title0 = arg0.getTitle();
		String title1 = arg1.getTitle();
		return title0.compareTo(title1);
	}

}
