/**
 * 
 */
package cz.kojotak.arx.domain.mode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.Searchable;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.ui.column.BaseColumn;
import cz.kojotak.arx.ui.event.FilterModel;

public abstract class Mode implements Searchable {

	protected String desc;
	protected String name;
	protected final List<Game> games;
	protected final Set<Category> cats=new HashSet<>();
	protected final Set<Platform> platforms = new HashSet<>();
	private int scores = 0;
	private int players = 0;
	
	protected FilterModel filter;
	
	public Mode(List<Game> games) {
		Set<User> users = new HashSet<>(); 
		this.games=games;
		this.games.forEach(g->{
			cats.add(g.getCategory());
			platforms.add(g.getPlatform());
			scores += g.getRecords1P().size();
			users.addAll(g.getRecords1P().stream().flatMap(s -> Stream.of(s.player(), s.secondPlayer())).toList());
		});
		Localization loc = Application.getInstance().getLocalization();
		this.desc = loc.getString(this, "DESC");
		this.name = loc.getString(this, "NAME");
		this.filter = new FilterModel();
		players = users.size();
		System.err.println(getClass().getSimpleName() + " games: " + games.size()+", scores: " + scores + ", users: "+players);
	}
	
	public abstract List<BaseColumn<Game,?>> getColumns();
	public abstract List<Score> getScores(Game g);
	
	public int getPlayerCount() {
		return players;
	}

	public int getGameCount() {
		return games.size();
	}

	public int getRecordCount() {
		return scores;
	}

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
