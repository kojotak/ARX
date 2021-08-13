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
public class LegacyImporter implements RunnableWithProgress, Importer{	
	
	public static final String RM_DB_URL = "https://github.com/kojotak/ARX/blob/reloaded/tmp/rotaxmame_databaze.gz?raw=true";
	
	private Map<Integer, Game> games;
	private Map<Integer, Float> avgRatings = new HashMap<>();
	
	private Set<User> users = new HashSet<User>();
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

	private void importRecord(String[] parts) {
		// (hra_id,zkratka,skore,dohrano,doba,emulator)
		// ('39663','VLD','29200','0','93','mame')
		String emulator = parts[5];
		int id = Integer.parseInt(parts[0]);
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
			Game game = games.get(id);
			game.getRecords1P().add(score);
			singleRecords++;
		} else if ("mame2".equals(emulator)) {
			Game game = games.get(id);
			game.getRecords2P().add(score);
			doubleRecords++;
		} else if ("amiga".equals(emulator)) {
			Game game = games.get(id);
			game.getRecords1P().add(score);
			singleRecords++;
		} else {
			return;
		}
		users.add(p1);
		if(p2!=null) {
			users.add(p2);
		}
	}

	private Float parseRating(String str) {
		String hodnoceniPercent = str.replace("%", "");
		return "-".equals(hodnoceniPercent) ? null : Float
				.parseFloat(hodnoceniPercent) / 100;
	}

	private void importNonGame(String[] parts) {
		// id,nazev,soubor,kategorie_id,hodnoceni,freeware,emulator,pocet_hracu)
		int id = Integer.parseInt(parts[0]);
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		Float hodnoceni = parseRating(parts[4]);
		//Integer hracu = Integer.parseInt(parts[7]);
		Platform platform = LegacyPlatform.resolve(parts[6]).toPlatform();

		Game game = new Game(id, null, cat, platform, title, file, null,1);
		avgRatings.put(id, hodnoceni);
		//game.setPlayerCount(hracu);
		this.games.put(id, game);
	}

	private void importAmigaGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,freeware,md5_disk1,md5_cfg,md5_start,emulator)
		int id = Integer.parseInt(parts[0]);
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		String pravidla = parts[4];
		// String hrajeSeStr = parts[5];
		// Integer hrajeSe = Integer.parseInt(hrajeSeStr);
		Float hodnoceni = parseRating(parts[6]);
//		String prvniStr = parts[7];
//		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr	: null;
//		String hracuStr = parts[8];
//		String freeware = parts[9];
//		String md5disk1 = parts[10];
//		String md5cfg = parts[11];
//		String md5start = parts[12];
		Game game = new Game(id, null, cat, LegacyPlatform.AMIGA.toPlatform(), title, file, pravidla,1);
		games.put(id, game);
		avgRatings.put(id, hodnoceni);
	}

	private void importGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,emulator)
		if (parts.length < 10) {
			// FIXME log
			return;
		}
		int id = Integer.parseInt(parts[0]);
		String title = parts[1];
		String file = parts[2];
		Category cat = LegacyCategory.resolve(parts[3]).toCategory();
		String pravidla = parts[4];
//		Integer hrajeSe = Integer.parseInt(parts[5]);
		Float hodnoceni = parseRating(parts[6]);
//		String prvniStr = parts[7];
//		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr: null;
//		String hracuStr = parts[8];
		String emulator = parts[9];

		if ("mame".equals(emulator)) {
			// do single mame game specific stuff
			Game singleGame = new Game(id, null, cat, LegacyPlatform.MAME.toPlatform(), title, file, pravidla,1);
			games.put(id, singleGame);
		} else if ("mame2".equals(emulator)) {
			// do double mame game specific stuff
			Game doubleGame = new Game(id, null, cat, LegacyPlatform.MAME.toPlatform(), title, file, pravidla,2);
//			if (prvni != null) {
//				String[] players = prvni.split("\\s");
//				if (players.length == 2) {
//					// prvni bude ten, kdo je drive v abecede
//					boolean reverse = players[0].compareTo(players[1]) > 0;
//				}
//			}
			games.put(id, doubleGame);
		}

		avgRatings.put(id, hodnoceni);
	}
	
	private Supplier<InputStream> in;

	public LegacyImporter(Supplier<InputStream> in){
		log = Application.getLogger(this);
		games = new HashMap<Integer, Game>(4000);
		this.in=in;
	}
	
	List<Game> prepareGames(Map<Integer, Game> map) {
		if(map==null || map.isEmpty()){
			return Collections.emptyList();
		}
		Collection<Game> collection = map.values();
		List<Game> list = new ArrayList<Game>(collection);
		Collections.sort(list, TitleBasedGameComparator.INSTANCE);
		for(Game game:list){
			if(game.getRecords1P().isEmpty()) {
				continue;
			}
			Collections.sort(game.getRecords1P(),ScoreBasedRecordComparator.INSTANCE);
			long topScore = game.getRecords1P().get(0).score();
			int players = game.getRecords1P().size();
			List<Score> positioned = new ArrayList<>();
			for(int i=0;i<game.getRecords1P().size();i++){
				Score r = game.getRecords1P().get(i);
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
			game.getRecords1P().clear();
			game.getRecords1P().addAll(positioned);
		}
		return list;
	}
	
	private Integer points(long score, long topScore, int position, int players) {
		int points = 0;
		points += 100.0 * score / topScore;
		points += (players-position) * 10;
		return points;
	}

	@Override
	public List<Game> getGames() {
		return prepareGames(this.games);
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

	public Set<User> getPlayers() {
		return users;
	}

	public int getSingleRecords() {
		return singleRecords;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public Platform getDefaultPlatform() {
		return LegacyPlatform.MAME.toPlatform();
	}
	
}
