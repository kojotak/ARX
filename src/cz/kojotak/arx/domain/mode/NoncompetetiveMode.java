/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
import cz.kojotak.arx.ui.column.PlatformIconColumn;
import cz.kojotak.arx.ui.column.PlatformNameColumn;
/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class NoncompetetiveMode extends BaseMode<Game> {
	
	@Override
	public List<? extends BaseColumn<Game,?>> getColumns() {
		var list = new ArrayList<BaseColumn<Game,?>>();
		list.add(new PlatformIconColumn());
		list.add(new PlatformNameColumn());
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

	@Override
	public Class<Game> getGameType() {
		return Game.class;
	}

	private List<Game> games;
	private Set<Platform> platforms;

	public NoncompetetiveMode(LegacyImporter importer) {
		super();
		this.games = importer.getNoncompetitiveGames();
		this.cats=importer.getNoncometetiveCategories();
		this.platforms=importer.getNoncompetetivePlatforms();
	}

	public List<Game> getGames() {
		return games;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}
	
}
