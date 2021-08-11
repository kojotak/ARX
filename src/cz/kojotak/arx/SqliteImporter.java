package cz.kojotak.arx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.User;

public class SqliteImporter implements Importer {

	final static String url = "jdbc:sqlite:D:/Rotaxmame/-/db.db";
	
	public static void main(String[] args) {
		SqliteImporter importer = new SqliteImporter();
		importer.run();
		
//		for(Integer gameId : importer.games.keySet()) {
//			System.out.println("Game: " + importer.games.get(gameId) + "\n\tscores: " + importer.scores.get(gameId));
//		}
	}
	
	@Override
	public Collection<User> getPlayers() {
		return users.values();
	}
	
	@Override
	public List<Game> getSinglePlayerGames() {
		return games.values().stream()
				.sorted(Comparator.comparing(Game::getTitle))
				.toList();
	}

	@Override
	public List<Game> getDoublePlayerGames() {
		return games.values().stream()
				.filter(g->g.getModeMax()>1)
				.sorted(Comparator.comparing(Game::getTitle))
				.toList();
	}

	@Override
	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public long max() {
		return -1;
	}

	@Override
	public long current() {
		return -1;
	}

	@Override
	public void run() {
		Logger log = Application.getLogger(this);
		try (Connection conn = DriverManager.getConnection(url)) {
			log.info("Connection to SQLite has been established.");
			platforms = loadPlatforms(conn);
			log.info("...imported " + platforms.size() + " platforms");
			categories = loadCategories(conn);
			log.info("...imported " + categories.size() + " categories");
			users = loadUsers(conn);
			log.info("...imported " + users.size() + " players");
			games = loadGames(conn, platforms, categories);
			log.info("...imported " + games.size() + " games");
			scores = loadScores(conn, games, users);
			log.info("...imported " + scores.size() + " scores");
			lastUpdate = loadLastUpdated(conn);
			log.info("...imported " + lastUpdate + " as last update");
		} catch (SQLException e) {
			throw new RuntimeException("Failed to import DB from " + url);
		}
	}

	private Date lastUpdate;
	private Map<Integer, Platform> platforms;
	private Map<Integer, Category> categories;
	private Map<Integer, User> users;
	private Map<Integer, Game> games;
	private Map<Integer, List<Score>> scores;
	
	private Map<Integer, List<Score>> loadScores(Connection conn, Map<Integer, Game> games, Map<Integer, User> users) {
		Map<Integer, GameStats> stats = loadGameStats(conn);
		
		//game_id -> list<Score>
		Map<Integer, List<Score>> map = new HashMap<>();
		String sql = "select game_id, user_id, user2_id, score, finished, play_time from score order by game_id asc, score desc";
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				Integer lastGameId = -1, lastPosition = -1;
				while(rs.next()) {
					int gameId = rs.getInt("game_id");
					if(gameId != lastGameId) {
						lastPosition=0;
						lastGameId=gameId;
					}
					Game g = games.get(gameId);
					GameStats stat = stats.get(gameId);
					long score = rs.getLong("score");
					User u1 = users.get(rs.getObject("user_id"));
					User u2 = users.get(rs.getObject("user_id"));
					Integer position = ++lastPosition;
					Integer points = points(score, position, stat);
					Score s = new Score(
							score,
							points,
							null, //rating
							rs.getBoolean("finished"),
							rs.getInt("play_time"),
							position,
							u1,
							u2
							);
					List<Score> scores = map.getOrDefault(g.getId(), new ArrayList<>());
					scores.add(s);
					map.put(g.getId(), scores);
				}
			}
		} catch (SQLException e) {
		 	Application.getLogger(this).log(Level.SEVERE,"Can not load scores",e);
		}
		return map;
	}
	
	private Integer points(long score, int position, GameStats stat) {
		if(stat==null) {
			return null;
		}
		int points = 0;
		points += 100.0 * score / stat.topScore;
		points += (stat.players-position) * 10;
		return points;
	}
	
	static record GameStats (int players, long topScore) {};
	
	private Map<Integer, GameStats> loadGameStats(Connection conn){
		Map<Integer, GameStats> map = new HashMap<>();
		String sql = "select game_id, count(1) as players, max(score) as topScore from score group by game_id";
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					GameStats stats = new GameStats(rs.getInt("players"), rs.getLong("topScore"));
					map.put(rs.getInt("game_id"), stats);
				}
			}
		} catch (SQLException e) {
		 	Application.getLogger(this).log(Level.SEVERE,"Can not load players per game",e);
		}
		return map;
	}

	private Map<Integer, User> loadUsers(Connection conn) {
		Map<Integer,User> map = new HashMap<>();
		String sql = "select id, nick from user";
		try(PreparedStatement  stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					User u = new User(
							rs.getInt("id"),
							rs.getString("nick"));
					map.put(u.id(), u);
				}
			}
		} catch (SQLException e) {
			Application.getLogger(this).log(Level.SEVERE,"Can not load users",e);
		}
		return map;
	}

	private Map<Integer, Game> loadGames(Connection conn, Map<Integer, Platform> platforms, Map<Integer, Category> categories){
		Map<Integer, Game> map = new HashMap<>();
		//hash - sorting by parent descending, so we'll get null games with no parents first
		String sql = "select id, name, rules, game_platform_id, game_category_id, file_command, mode_max, parent_id, rm_commands from game order by parent_id desc";
		try(PreparedStatement  stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					Platform p = platforms.get(rs.getInt("game_platform_id"));
					Category c = categories.get(rs.getInt("game_category_id"));
					//first ID is 1, getInt returns 0 for null
					Game parent = map.get(rs.getInt("parent_id"));
					Game g = new Game(
							rs.getInt("id"),
							parent,
							c,
							p,
							rs.getString("name"), 
							rs.getString("file_command"),
							rs.getString("rules"), 
							rs.getInt("mode_max")
//							rs.getString("rm_commands")
							);
					map.put(g.getId(), g);
				}
			}
		} catch (SQLException e) {
			Application.getLogger(this).log(Level.SEVERE,"Can not load games",e);
		}
		return map;
	}
	
	private Map<Integer,Platform> loadPlatforms(Connection conn){
		Map<Integer,Platform> map = new HashMap<>();
		String sql = "select id, name, link, mode_max from game_platform";
		try(PreparedStatement  stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					Platform p = new Platform(
							rs.getInt("id"),
							rs.getString("name"), 
							rs.getString("link"), 
							rs.getInt("mode_max"));
					map.put(p.id(), p);
				}
			}
		} catch (SQLException e) {
			Application.getLogger(this).log(Level.SEVERE,"Can not load platforms",e);
		}
		return map;
	}
	
	private Map<Integer,Category> loadCategories(Connection conn){
		Map<Integer,Category> map = new HashMap<>();
		String sql = "select id, name from game_category";
		try(PreparedStatement  stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					Category c = new Category(
							rs.getInt("id"),
							rs.getString("name"));
					map.put(c.id(), c);
				}
			}
		} catch (SQLException e) {
			Application.getLogger(this).log(Level.SEVERE,"Can not load categories",e);
		}
		return map;
	}

	private Date loadLastUpdated(Connection conn) {
		String sql = "select max(last_update_time) as lastUpdate from info";
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				String str = rs.getString("lastUpdate");
				if(str!=null) {
					DateFormat df = new SimpleDateFormat("d.M.yyyy mm:HH:ss");
					try {
						return df.parse(str);
					} catch (ParseException e) {
						Application.getLogger(this).log(Level.SEVERE,"Can not parse last update time: " +str,e);
					}
				}
			} 
		} catch (SQLException e) {
			Application.getLogger(this).log(Level.SEVERE,"Can not load last update time",e);
		} 
		return null;
	}
}
