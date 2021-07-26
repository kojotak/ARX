/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.enums.Platform;
import cz.kojotak.arx.domain.game.BaseMameGame;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public abstract class BaseMameMode<R extends Record,T extends BaseMameGame<R>> extends BaseMode<T> {
	
	protected List<T> games;

	public BaseMameMode(List<T> games) {
		super();
		this.games = games;
	}

	@Override
	public List<T> getGames() {
		return games;
	}

	@Override
	public Set<Platform> getPlatforms() {
		return Collections.singleton(Platform.MAME);
	}	
}
