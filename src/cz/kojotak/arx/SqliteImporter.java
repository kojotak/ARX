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

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.Player;

public class SqliteImporter implements RunnableWithProgress {

	public final static String BACKUP_URL = "https://github.com/kojotak/ARX/blob/reloaded/tmp/db.db?raw=true";
	
	public static void main(String[] args) {
		SqliteImporter sqliteImporter = new SqliteImporter("tmp/db.db");
		sqliteImporter.run();
	}
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	private final String url;
	
	private Date lastUpdate;
	private Map<Integer, Platform> platforms;
	private Map<Integer, Category> categories;
	private Map<Integer, Player> players;
	private Map<Integer, Game> games;
	
	public SqliteImporter(String dbPath) {
		this.url = "jdbc:sqlite:" + dbPath;
	}

	public Collection<Player> getPlayers() {
		return players.values();
	}
	
	public Platform getDefaultPlatform() {
		if(platforms!=null) {
			for(Platform p : platforms.values()) {
				if("arcade".equals(p.link())) {
					return p;
				}
			}
		}
		return null;
	}

	public List<Game> getGames() {
		return games.values().stream()
				.sorted(Comparator.comparing(Game::getTitle))
				.toList();
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public long max() {
		return -1;
	}

	public long current() {
		return -1;
	}

	public void run() {
		try (Connection conn = DriverManager.getConnection(url)) {
			logger.info("connected to SQLite: " + url);
			platforms = loadPlatforms(conn);
			logger.info("...imported " + platforms.size() + " platforms");
			categories = loadCategories(conn);
			logger.info("...imported " + categories.size() + " categories");
			players = loadplayers(conn);
			logger.info("...imported " + players.size() + " players");
			games = loadGames(conn, platforms, categories);
			logger.info("...imported " + games.size() + " games");
			Integer scores = loadScores(conn, games, players);
			logger.info("...imported " + scores + " scores");
			lastUpdate = loadLastUpdated(conn);
			logger.info("...imported " + lastUpdate + " as last update");
		} catch (SQLException e) {
			throw new RuntimeException("Failed to import DB from " + url);
		}
	}
	
	private Integer loadScores(Connection conn, Map<Integer, Game> games, Map<Integer, Player> players) {
		Map<Integer, List<Score>> scoresP1 = loadScores("user2_id == '' ", conn, players);
		Map<Integer, List<Score>> scoresP2 = loadScores("user2_id != '' ", conn, players);
		for(Integer gameId : games.keySet()) {
			Game game = games.get(gameId);
			List<Score> p1 = scoresP1.get(gameId);
			if(p1!=null) {
				game.getRecords1P().addAll(p1);
			}
			List<Score> p2 = scoresP2.get(gameId);
			if(p2!=null) {
				game.getRecords2P().addAll(p2);
			}
		}
		return scoresP1.size() + scoresP2.size();
	}
	
	private Map<Integer,List<Score>> loadScores(String sqlWhere, Connection conn, Map<Integer, Player> players){
		String sql = "select game_id, user_id, user2_id, score, finished, play_time from score where " + sqlWhere + " order by game_id asc, score desc";
		Map<Integer, GameStats> stats = loadGameStats(sqlWhere, conn);
		Map<Integer,List<Score>> map = new HashMap<>();
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				Integer lastGameId = -1, lastPosition = -1;
				while(rs.next()) {
					int gameId = rs.getInt("game_id");
					if(gameId != lastGameId) {
						lastPosition=0;
						lastGameId=gameId;
					}
					GameStats stat = stats.get(gameId);
					long score = rs.getLong("score");
					Integer position = ++lastPosition;
					Score s = new Score(
							score,
							points(score, position, stat),
							null, //rating
							rs.getBoolean("finished"),
							rs.getInt("play_time"),
							position,
							players.get(rs.getObject("user_id")),
							players.get(rs.getObject("user2_id"))
							);
					List<Score> scores = map.get(gameId);
					if(scores==null) {
						scores = new ArrayList<>();
						map.put(gameId, scores);
					}
					scores.add(s);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Can not load scores",e);
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
	
	private Map<Integer, GameStats> loadGameStats(String sqlWhere, Connection conn){
		Map<Integer, GameStats> map = new HashMap<>();
		String sql = "select game_id, count(1) as players, max(score) as topScore from score where " + sqlWhere + " group by game_id";
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					GameStats stats = new GameStats(rs.getInt("players"), rs.getLong("topScore"));
					map.put(rs.getInt("game_id"), stats);
				}
			}
		} catch (SQLException e) {
		 	logger.log(Level.SEVERE,"Can not load players per game",e);
		}
		return map;
	}

	private Map<Integer, Player> loadplayers(Connection conn) {
		Map<Integer,Player> map = new HashMap<>();
		String sql = "select id, nick from user";
		try(PreparedStatement  stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()) {
					Player u = new Player(
							rs.getInt("id"),
							rs.getString("nick"));
					map.put(u.id(), u);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Can not load players",e);
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
			logger.log(Level.SEVERE,"Can not load games",e);
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
			logger.log(Level.SEVERE,"Can not load platforms",e);
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
			logger.log(Level.SEVERE,"Can not load categories",e);
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
						logger.log(Level.SEVERE,"Can not parse last update time: " +str,e);
					}
				}
			} 
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Can not load last update time",e);
		} 
		return null;
	}
}
