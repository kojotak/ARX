/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.CompetitiveGame;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;

/**
 * @date 25.1.2010
 * @author Kojotak 
 */
public class AmigaMode extends BaseMode<CompetitiveGame> {

	@Override
	public List<CompetitiveGame> getGames() {
		return games;
	}
		
	@Override
	public Class<CompetitiveGame> getGameType() {
		return CompetitiveGame.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends BaseColumn<CompetitiveGame,?>> getColumns() {
		List list = new ArrayList<BaseColumn<CompetitiveGame,?>>();
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

	private List<CompetitiveGame> games;

	public AmigaMode(LegacyImporter importer) {
		super();
		this.games = importer.getCompetitiveGames();
		this.cats = importer.getAmigaCategories();
	}

	@Override
	public Set<LegacyPlatform> getPlatforms() {
		return Collections.singleton(LegacyPlatform.AMIGA);
	}

}
