package cz.kojotak.arx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.User;

public class SqliteImporter {

	final static String url = "jdbc:sqlite:D:/Rotaxmame/-/db.db";
	
	static record Game(int id, 
			String name, 
			String rules, 
			Platform platform, 
			Category category, 
			String file,
			int modeMax,
			Game parent,
			String command) {
	}
	
	static record Score(int id, 
			User user, 
			User user2, 
			long score, 
			int position,
			boolean finished, 
			long duration) {
	}
	
	public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            
            Map<Integer, Platform> platforms = loadPlatforms(conn);
            Map<Integer, Category> categories = loadCategories(conn);
            Map<Integer, User> users = loadUsers(conn);
            Map<Integer, Game> games = loadGames(conn, platforms, categories);
            
            //game id -> list of scores
            Map<Integer, List<Score>> scores = loadScores(conn, games, users);
            
            for(Integer gameId : games.keySet()) {
            	System.out.println("Game: " + games.get(gameId) + "\n\tscores: " + scores.get(gameId));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
		
	}
	
	private static Map<Integer, List<Score>> loadScores(Connection conn, Map<Integer, Game> games, Map<Integer, User> users) {
		Map<Integer, List<Score>> map = new HashMap<>();
		String sql = "select id, game_id, user_id, user2_id, score, finished, play_time from score order by game_id asc, score desc";
		try(PreparedStatement stm = conn.prepareStatement(sql)){
			try(ResultSet rs = stm.executeQuery()){
				int lastGameId = -1, lastPosition = -1;
				while(rs.next()) {
					int gameId = rs.getInt("game_id");
					if(gameId!=lastGameId) {
						lastPosition=0;
						lastGameId=gameId;
					}
					Game g = games.get(gameId);
					User u1 = users.get(rs.getObject("user_id"));
					User u2 = users.get(rs.getObject("user_id"));
					Score s = new Score(
							rs.getInt("id"),
							u1,
							u2,
							rs.getLong("score"),
							++lastPosition,
							rs.getBoolean("finished"),
							rs.getLong("play_time")
							);
					List<Score> scores = map.getOrDefault(g.id(), new ArrayList<>());
					scores.add(s);
					map.put(g.id(), scores);
				}
			}
		} catch (SQLException e) {
			System.err.println("failed to load scores " + e);
		}
		return map;
	}

	static Map<Integer, User> loadUsers(Connection conn) {
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
			System.err.println("failed to load users " + e);
		}
		return map;
	}

	static Map<Integer, Game> loadGames(Connection conn, Map<Integer, Platform> platforms, Map<Integer, Category> categories){
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
							rs.getString("name"), 
							rs.getString("rules"), 
							p,
							c,
							rs.getString("file_command"),
							rs.getInt("mode_max"),
							parent,
							rs.getString("rm_commands")
							);
					map.put(g.id(), g);
				}
			}
		} catch (SQLException e) {
			System.err.println("failed to load games " + e);
		}
		return map;
	}
	
	static Map<Integer,Platform> loadPlatforms(Connection conn){
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
			System.err.println("failed to load platforms " + e);
		}
		return map;
	}
	
	static Map<Integer,Category> loadCategories(Connection conn){
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
			System.err.println("failed to load categories " + e);
		}
		return map;
	}

}
