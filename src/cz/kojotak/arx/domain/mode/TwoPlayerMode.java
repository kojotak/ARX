/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import cz.kojotak.arx.SqliteImporter;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.CoplayerCountColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
/**
 * @date 24.1.2010
 * @author Kojotak
 */
public class TwoPlayerMode extends Mode {

	public TwoPlayerMode(SqliteImporter SqliteImporter) {
		super(SqliteImporter.getGames());
	}
	
	@Override
	public List<Score> getScores(Game g) {
		return g.getRecords2P();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<Game,?>> getColumns() {
		List list = new ArrayList<BaseColumn<Game,?>>();

		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		list.add(new CoplayerCountColumn());
		return list;
	}

}
