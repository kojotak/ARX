/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import cz.kojotak.arx.Importer;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.ModeWithStatistics;
import cz.kojotak.arx.domain.Record;
import cz.kojotak.arx.domain.game.MameGameSingle;
import cz.kojotak.arx.ui.column.AverageRatingsColumn;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.BestPlayerColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.ChanceColumn;
import cz.kojotak.arx.ui.column.FinishedColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
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
public class ArcadeMode extends BaseMameMode<Record,MameGameSingle> implements ModeWithStatistics {

	@Getter
	private int playerCount = 0;
	
	@Getter
	private int recordCount = 0;
	
	@Getter
	private Category category=null;

	@Override
	public int getGameCount() {
		return games.size();
	}

	public ArcadeMode(Importer importer) {
		super(importer.getMameSingleGames());
		playerCount=importer.getSinglePlayers().size();
		recordCount=importer.getSingleRecords();
		cats=importer.getSingleCategories();
	}
	
	@Override
	public Class<MameGameSingle> getGameType() {
		return MameGameSingle.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<MameGameSingle,?>> getColumns() {
		List list = new ArrayList<BaseColumn<MameGameSingle,?>>();
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

}
