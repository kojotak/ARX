package cz.kojotak.arx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.Platform;
import cz.kojotak.arx.domain.Score;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.enums.LegacyCategory;
import cz.kojotak.arx.domain.enums.LegacyPlatform;
import cz.kojotak.arx.util.ProgressInputStream;
import cz.kojotak.arx.util.ScoreBasedRecordComparator;
import cz.kojotak.arx.util.TitleBasedGameComparator;

/**
 * Needs refactoring!
 * @date 26.9.2010
 * @author Kojotak
 */
public class LegacyImporter implements RunnableWithProgress{	
	
	public static final String RM_DB_URL = "https://github.com/kojotak/ARX/blob/reloaded/tmp/rotaxmame_databaze.gz?raw=true";
	
	private Map<String, Game> gamesSingle;
	private Map<String, Game> gamesDouble;
	private Map<String, Float> avgRatings = new HashMap<>();
	
	private Set<User> singlePlayers = new HashSet<User>();
	//old user id (3 letters) -> fake integer id
	private Map<String,Integer> fakeUserIds = new HashMap<>();

	int singleRecords=0;
	int doubleRecords=0;
	
	private Date lastUpdate;
	protected Logger log;

	public User getOrFakeUser(String playerSign) {
		if(playerSign == null) {
			return null;
		}
		Integer id = fakeUserIds.get(playerSign);
		if(id==null) {
			id = fakeUserIds.size()+1;
			fakeUserIds.put(playerSign, id);
		}
		return new User(id, playerSign);
	}

	private void addRecord(Game game, Score record) {
		if (game == null) {
			log.fine("No game found for record " + record);
			return;
		}
		List<Score> records = game.getRecords();
		if (records == null || records.size() == 0) {
			// null or maybe imutable
			records = new ArrayList<>();
			game.setRecords(records);
		}
		records.add(record);
	}

	private void importRecord(String[] parts) {
		// (hra_id,zkratka,skore,dohrano,doba,emulator)
		// ('39663','VLD','29200','0','93','mame')
		String emulator = parts[5];
		String id = parts[0];
		String userParts = parts[1];
		
		String[] players = userParts.split("\\s");
		User p1=null, p2=null;
		if(players.length==2) {
			boolean reverse = players[0].compareTo(players[1]) > 0;
			p1 = getOrFakeUser(players[reverse ? 1 : 0]);
			p2 = getOrFakeUser(players[reverse ? 0 : 1]);
		} else {
			p1 = getOrFakeUser(userParts);
		}
		
		String scoreStr = parts[2];
		Long scoreLong = Long.parseLong(scoreStr);
		String dohranoStr = parts[3];
		Boolean dohrano = "1".equals(dohranoStr);
		String dobaStr = parts[4];
		int duration = (dobaStr != null && dobaStr.length() > 0) ? Integer.parseInt(dobaStr) : 0;
		
		Score score = new Score(scoreLong, null, null, dohrano, duration, null, p1, p2);
		
		if ("mame".equals(emulator)) {
			Game game = gamesSingle.get(id);
			addRecord(game, score);
			singleRecords++;
		} else if ("mame2".equals(emulator)) {
			Game game = gamesDouble.get(id);
			addRecord(game, score);
			doubleRecords++;
		} else if ("amiga".equals(emulator)) {
			Game game = gamesSingle.get(id);
			addRecord(game, score);
			singleRecords++;
		} else {
			return;
		}
		if("mame".equals(emulator)){
			singlePlayers.add(score.player());
		}
	}

	private Float parseRating(String str) {
		String hodnoceniPercent = str.replace("%", "");
		return "-".equals(hodnoceniPercent) ? null : Float
				.parseFloat(hodnoceniPercent) / 100;
	}

	private void importNonGame(String[] parts) {
		// id,nazev,soubor,kategorie_id,hodnoceni,freeware,emulator,pocet_hracu)
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		Float hodnoceni = parseRating(parts[4]);
		//Integer hracu = Integer.parseInt(parts[7]);
		Platform platform = LegacyPlatform.resolve(parts[6]).toPlatform();

		Game game = new Game(id, cat, platform, title, file, null);
		avgRatings.put(id, hodnoceni);
		//game.setPlayerCount(hracu);
		this.gamesSingle.put(id, game);
	}

	private void importAmigaGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,freeware,md5_disk1,md5_cfg,md5_start,emulator)
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		String pravidla = parts[4];
		// String hrajeSeStr = parts[5];
		// Integer hrajeSe = Integer.parseInt(hrajeSeStr);
		Float hodnoceni = parseRating(parts[6]);
		String prvniStr = parts[7];
		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr
				: null;
		String hracuStr = parts[8];
//		String freeware = parts[9];
//		String md5disk1 = parts[10];
//		String md5cfg = parts[11];
//		String md5start = parts[12];
		Game game = new Game(id, cat, LegacyPlatform.AMIGA.toPlatform(), title, file, pravidla);
		game.setFirstPlayerSign(prvni);
		gamesSingle.put(id, game);
		avgRatings.put(id, hodnoceni);
	}

	private void importGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,emulator)
		if (parts.length < 10) {
			// FIXME log
			return;
		}
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		String pravidla = parts[4];
//		Integer hrajeSe = Integer.parseInt(parts[5]);
		Float hodnoceni = parseRating(parts[6]);
		String prvniStr = parts[7];
		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr
				: null;
		String hracuStr = parts[8];
		String emulator = parts[9];

		Game game = null;

		if ("mame".equals(emulator)) {
			// do single mame game specific stuff
			Game singleGame = new Game(id, cat, LegacyPlatform.MAME.toPlatform(), title, file, pravidla);
			game = singleGame;
			game.setFirstPlayerSign(prvni);
			gamesSingle.put(id, singleGame);
		} else if ("mame2".equals(emulator)) {
			// do double mame game specific stuff
			Game doubleGame = new Game(id, cat, LegacyPlatform.MAME.toPlatform(), title, file, pravidla);
			game = doubleGame;
			if (prvni != null) {
				String[] players = prvni.split("\\s");
				if (players.length == 2) {
					// prvni bude ten, kdo je drive v abecede
					boolean reverse = players[0].compareTo(players[1]) > 0;
					doubleGame.setFirstPlayerSign(players[reverse ? 1 : 0]);
					doubleGame.setSecondPlayerSign(players[reverse ? 0 : 1]);
				}
			}
			gamesDouble.put(id, doubleGame);
		}

		avgRatings.put(id, hodnoceni);
	}
	
	private Supplier<InputStream> in;

	public LegacyImporter(Supplier<InputStream> in){
		log = Application.getLogger(this);
		gamesSingle = new HashMap<String, Game>(2000);
		gamesDouble = new HashMap<String, Game>(200);
		this.in=in;
	}
	
	List<Game> prepareGames(Map<String, Game> map) {
		if(map==null || map.isEmpty()){
			return Collections.emptyList();
		}
		Collection<Game> collection = map.values();
		List<Game> list = new ArrayList<Game>(collection);
		Collections.sort(list, TitleBasedGameComparator.INSTANCE);
		for(Game game:list){
			if(game.getRecords().isEmpty()) {
				continue;
			}
			Collections.sort(game.getRecords(),ScoreBasedRecordComparator.INSTANCE);
			long topScore = game.getRecords().get(0).score();
			int players = game.getRecords().size();
			List<Score> positioned = new ArrayList<>();
			for(int i=0;i<game.getRecords().size();i++){
				Score r = game.getRecords().get(i);
				int pos = i+1;
				Integer points = points(r.score(), topScore, pos, players);
				Float rating = r.rating();
				if(rating == null) {
					//workaround for missing ratings
					rating = avgRatings.get(game.getId());
				}
				Score ns = new Score(r.score(), points, rating, r.finished(), r.duration(), pos, r.player(), r.secondPlayer());
				positioned.add(ns);
			}
			game.setRecords(positioned);
		}
		return list;
	}
	
	private Integer points(long score, long topScore, int position, int players) {
		int points = 0;
		points += 100.0 * score / topScore;
		points += (players-position) * 10;
		return points;
	}

	public List<Game> getSinglePlayerGames() {
		return prepareGames(this.gamesSingle);
	}

	public List<Game> getDoublePlayerGames() {
		return prepareGames(this.gamesDouble);
	}

	@Override
	public long max(){
		return -1; //we don't know
	};
	
	@Override
	public long current(){
		return progressIs.getTotalNumBytesRead();
	}
	ProgressInputStream progressIs=null;
	public void run(){
		log.info("starting importer ...");
		BufferedReader reader;
		try{
			progressIs = new ProgressInputStream(in.get());
			GZIPInputStream gzip = new GZIPInputStream(progressIs);
			reader = new BufferedReader(new InputStreamReader(gzip));
		}catch(IOException ioex){
			throw new RuntimeException("Error during import",ioex);
		}
			
		try {
			String line = null;
			int mameGames = 0, amigaGames =0, nonGames = 0, records = 0,readLines=0;
			while ((line = reader.readLine()) != null) {
				
				readLines++;
				int start = line.indexOf("('");
				int end = line.indexOf("')");
				if (start < 0 || end < 0) {
					if(line.startsWith("UPDATE info SET cas_aktualizace")){
						start=line.indexOf("'");
						end=line.indexOf("'",start+1);
						if(start>0 && end > start){
							String sub = line.substring(start+1, end);
							Long l = Long.parseLong(sub);
							l*=1000;//php asi neumi millisekundy
							lastUpdate = new Date(l);
							log.info("database version: "+lastUpdate);
						}
						continue;
					}else{
						continue;
					}
				}
				String data = line.substring(start + 2, end);
				String[] parts = data.split("','");
				if (line.startsWith("INSERT INTO hry_mame")) {
					importGame(parts);
					mameGames++;// both single and double mode
				} else if (line.startsWith("INSERT INTO hry_amiga")) {
					importAmigaGame(parts);
					amigaGames++;
				} else if (line.startsWith("INSERT INTO hry")) {
					importNonGame(parts);
					nonGames++;
				} else if (line.startsWith("INSERT INTO rekordy")) {
					importRecord(parts);
					records++;
				} 
			}
			log.info("done..., mame games=" + mameGames
					+ ", amiga games=" + amigaGames + ", noncompetitive games="
					+ nonGames + ", records=" + records+", read lines="+readLines);
		} catch (IOException x) {
			x.printStackTrace();
		} 
	}

	public Set<User> getSinglePlayers() {
		return singlePlayers;
	}

	public int getSingleRecords() {
		return singleRecords;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
	
}
