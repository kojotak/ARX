/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.ModeWithStatistics;
import cz.kojotak.arx.domain.enums.LegacyCategory;
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
import cz.kojotak.arx.ui.column.ScoreStatisticsColumn;
import cz.kojotak.arx.ui.column.StatisticsPositionWithIconColumn;

/**
 * @date 24.1.2010
 * @author Kojotak
 */
public class SinglePlayerMode extends Mode implements ModeWithStatistics {

	private int playerCount = 0;
	private int recordCount = 0;
	private LegacyCategory category=null;

	@Override
	public int getGameCount() {
		return games.size();
	}

	public SinglePlayerMode(LegacyImporter importer) {
		super(importer.getSinglePlayerGames());
		playerCount=importer.getSinglePlayers().size();
		recordCount=importer.getSingleRecords();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<Game,?>> getColumns() {
		PlatformIconColumn platformIcon = new PlatformIconColumn();
		platformIcon.setVisible(false);
		
		List list = new ArrayList<BaseColumn<Game,?>>();
		list.add(platformIcon);
		list.add(new StatisticsPositionWithIconColumn());
		list.add(new PlayerCountColumn());
		list.add(new RelativePositionColumn());
		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		list.add(new BestPlayerColumn());
		list.add(new ScoreStatisticsColumn());
		list.add(new AverageRatingsColumn());
		list.add(new PointsColumn());
		list.add(new ChanceColumn());
		list.add(new PlayerDiffColumn());
		list.add(new FinishedColumn());

		return list;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public LegacyCategory getCategory() {
		return category;
	}

}
