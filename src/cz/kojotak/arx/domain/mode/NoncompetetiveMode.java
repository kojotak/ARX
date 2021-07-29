/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.enums.Platform;
import cz.kojotak.arx.domain.game.NoncompetitiveGame;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
import cz.kojotak.arx.ui.column.PlatformIconColumn;
import cz.kojotak.arx.ui.column.PlatformNameColumn;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class NoncompetetiveMode extends BaseMode<NoncompetitiveGame> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends BaseColumn<NoncompetitiveGame,?>> getColumns() {
		List list = new ArrayList<BaseColumn<NoncompetitiveGame,?>>();
		list.add(new PlatformIconColumn());
		list.add(new PlatformNameColumn());
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

	@Override
	public Class<NoncompetitiveGame> getGameType() {
		return NoncompetitiveGame.class;
	}

	private List<NoncompetitiveGame> games;
	private Set<Platform> platforms;

	public NoncompetetiveMode(LegacyImporter importer) {
		super();
		this.games = importer.getNoncompetitiveGames();
		this.cats=importer.getNoncometetiveCategories();
		this.platforms=importer.getNoncompetetivePlatforms();
	}

	public List<NoncompetitiveGame> getGames() {
		return games;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}
	
}
