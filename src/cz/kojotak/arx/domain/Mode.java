/**
 * 
 */
package cz.kojotak.arx.domain;

import java.util.List;
import java.util.Set;

import cz.kojotak.arx.domain.enums.LegacyCategory;
import cz.kojotak.arx.ui.column.BaseColumn;
/**
 * 
 * @date 24.5.2010
 * @author Kojotak
 */
public interface Mode<T extends Game> {
	
	/**
	 * @return name of mode (what user sees)
	 */
	String getName();
	
	/**
	 * @return mode's description
	 */
	String getDescription();
	
	/**
	 * @return list of mode's games
	 */
	List<T> getGames();
	
	/**
	 * @return game's class type for {@link #getGames()}
	 */
	Class<T> getGameType();
	
	/**
	 * @return list of mode's column to be displayed in {@link GameTable}
	 */
	List<? extends BaseColumn<T,?>> getColumns();
	
	/**
	 * @return set of all categories as found in {@link LegacyImporter} (categories with no games are skipped)
	 */
	Set<Category> getCategories();
	
	/**
	 * 
	 * @return all platforms found in all games
	 */
	Set<Platform> getPlatforms();
}
