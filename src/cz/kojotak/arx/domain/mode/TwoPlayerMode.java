/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.Record2P;
import cz.kojotak.arx.domain.game.MameGameDouble;
import cz.kojotak.arx.domain.game.MameGameSingle;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;

/**
 * @date 24.1.2010
 * @author Kojotak
 */
public class TwoPlayerMode extends BaseMameMode<Record2P,MameGameDouble> {

	public TwoPlayerMode(LegacyImporter importer) {
		super(importer.getMameDoubleGames());
		this.cats = importer.getDoubleCategories();
	}
	
	@Override
	public Class<MameGameDouble> getGameType() {
		return MameGameDouble.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<MameGameDouble,?>> getColumns() {
		List list = new ArrayList<BaseColumn<MameGameSingle,?>>();

		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

}
