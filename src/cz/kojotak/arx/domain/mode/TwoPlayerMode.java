/**
 *
 */
package cz.kojotak.arx.domain.mode;

import java.util.ArrayList;
import java.util.List;

import cz.kojotak.arx.LegacyImporter;
import cz.kojotak.arx.domain.CompetitiveGame;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.column.CategoryColumn;
import cz.kojotak.arx.ui.column.GameNameColumn;
/**
 * @date 24.1.2010
 * @author Kojotak
 */
public class TwoPlayerMode extends BaseMameMode {

	public TwoPlayerMode(LegacyImporter importer) {
		super(importer.getMameDoubleGames());
		this.cats = importer.getDoubleCategories();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BaseColumn<CompetitiveGame,?>> getColumns() {
		List list = new ArrayList<BaseColumn<CompetitiveGame,?>>();

		list.add(new GameNameColumn());
		list.add(new CategoryColumn());
		return list;
	}

}
