/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.game.MameGame;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public abstract class BaseMameMode extends BaseMode<MameGame> {
	
	protected List<MameGame> games;

	public BaseMameMode(List<MameGame> games) {
		super();
		this.games = games;
	}

	@Override
	public List<MameGame> getGames() {
		return games;
	}

	@Override
	public Set<LegacyPlatform> getPlatforms() {
		return Collections.singleton(LegacyPlatform.MAME);
	}
	
	@Override
	public Class<MameGame> getGameType() {
		return MameGame.class;
	}
}
