/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import cz.kojotak.arx.SqliteImporter;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.ui.column.AverageRatingsColumn;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.BestPlayerColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.ChanceColumn;
import cz.kojotak.arx.ui.column.FinishedColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
import cz.kojotak.arx.ui.column.PlatformIconColumn;
import cz.kojotak.arx.ui.column.PlayerCountColumn;
import cz.kojotak.arx.ui.column.PlayerDiffColumn;
import cz.kojotak.arx.ui.column.PointsColumn;
import cz.kojotak.arx.ui.column.RelativePositionColumn;
import cz.kojotak.arx.ui.column.TopScoreColumn;
import cz.kojotak.arx.ui.column.PositionWithIconColumn;

/**
 * @date 24.1.2010
 * @author Kojotak
 */
public class SinglePlayerMode extends Mode  {

	public SinglePlayerMode(SqliteImporter SqliteImporter) {
		super(SqliteImporter.getGames());
	}
	
	@Override
	public List<Score> getScores(Game g) {
		return g.getRecords1P();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<Game,?>> getColumns() {
		var platformIcon = new PlatformIconColumn();
		var topScore = new TopScoreColumn();
		platformIcon.setVisible(false);
		topScore.setVisible(false);
		
		List list = new ArrayList<BaseColumn<Game,?>>();
		list.add(platformIcon);
		list.add(new PositionWithIconColumn());
		list.add(new PlayerCountColumn());
		list.add(new RelativePositionColumn());
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		list.add(new BestPlayerColumn());
		list.add(topScore);
		list.add(new AverageRatingsColumn());
		list.add(new PointsColumn());
		list.add(new ChanceColumn());
		list.add(new PlayerDiffColumn());
		list.add(new FinishedColumn());

		return list;
	}

}
