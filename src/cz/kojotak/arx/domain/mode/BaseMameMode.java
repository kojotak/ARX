/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.domain.game.CompetitiveGame;

/**
 * @date 23.1.2010
 * @author Kojotak 
 */
public abstract class BaseMameMode extends BaseMode<CompetitiveGame> {
	
	protected List<CompetitiveGame> games;

	public BaseMameMode(List<CompetitiveGame> games) {
		super();
		this.games = games;
	}

	@Override
	public List<CompetitiveGame> getGames() {
		return games;
	}

	@Override
	public Set<LegacyPlatform> getPlatforms() {
		return Collections.singleton(LegacyPlatform.MAME);
	}
	
	@Override
	public Class<CompetitiveGame> getGameType() {
		return CompetitiveGame.class;
	}
}
