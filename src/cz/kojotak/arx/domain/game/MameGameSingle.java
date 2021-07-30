/**
 * 
 */
package cz.kojotak.arx.domain.game;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.WithStatistics;

/**
 * Just type safe implementation of {@link BaseMameGame} for single play
 * @date 25.1.2010
 * @author Kojotak 
 */
public class MameGameSingle extends BaseMameGame implements WithStatistics{

	public MameGameSingle(String id,Category category, String title, String file) {
		super(id,category, title, file);
	}
	
}
