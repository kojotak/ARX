/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.enums.Platform;
import cz.kojotak.arx.domain.game.AmigaGame;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class AmigaMode extends BaseMode<AmigaGame> {

	@Override
	public List<AmigaGame> getGames() {
		return games;
	}
		
	@Override
	public Class<AmigaGame> getGameType() {
		return AmigaGame.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends BaseColumn<AmigaGame,?>> getColumns() {
		List list = new ArrayList<BaseColumn<AmigaGame,?>>();
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

	private List<AmigaGame> games;

	public AmigaMode(LegacyImporter importer) {
		super();
		this.games = importer.getAmigaGames();
		this.cats = importer.getAmigaCategories();
	}

	@Override
	public Set<Platform> getPlatforms() {
		return Collections.singleton(Platform.AMIGA);
	}

}
