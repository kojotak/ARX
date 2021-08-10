/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Searchable;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.event.FilterModel;

public abstract class Mode implements Searchable {

	protected String desc;
	protected String name;
	protected final List<Game> games;
	protected final Set<Category> cats=new HashSet<>();
	protected final Set<Platform> platforms = new HashSet<>();
	
	protected FilterModel filter;
	
	public Mode(List<Game> games) {
		this.games=games;
		this.games.forEach(g->{
			cats.add(g.getCategory());
			platforms.add(g.getPlatform());
		});
		Localization loc = Application.getInstance().getLocalization();
		this.desc = loc.getString(this, "DESC");
		this.name = loc.getString(this, "NAME");
		this.filter = new FilterModel();
	}
	
	public abstract List<BaseColumn<Game,?>> getColumns();

	public String getDescription() {
		return desc;
	}

	public String getName() {
		return name;
	}

	public Set<Category> getCategories() {
		return cats;
	}

	public FilterModel getFilter() {
		return filter;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}

	public void setFilter(FilterModel filter) {
		this.filter = filter;
	}
	
	public List<Game> getGames() {
		return games;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
