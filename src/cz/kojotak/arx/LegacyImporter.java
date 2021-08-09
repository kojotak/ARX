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
import java.util.zip.GZIPInputStream;
import java.util.logging.Logger;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.Category;
import cz.kojotak.arx.domain.Competetive;
import cz.kojotak.arx.domain.CompetitiveGame;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.domain.impl.Record;
import cz.kojotak.arx.domain.User;
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
	private Map<String, CompetitiveGame> gamesSingle;
	private Map<String, CompetitiveGame> gamesDouble;
	private Map<String, CompetitiveGame> gamesAmiga;
	private Map<String, Game> gamesNoncompetetive;
	
	private Set<User> singlePlayers = new HashSet<User>();
	//old user id (3 letters) -> fake integer id
	private Map<String,Integer> fakeUserIds = new HashMap<>();
	int singleRecords=0;
	int doubleRecords=0;
	int amigaRecords=0;
	private Set<Category> singleCategories=new HashSet<Category>();
	private Set<Category> doubleCategories=new HashSet<Category>();
	private Set<Category> amigaCategories=new HashSet<Category>();
	private Set<Category> noncometetiveCategories=new HashSet<Category>();
	private Set<LegacyPlatform> noncompetetivePlatforms=new HashSet<LegacyPlatform>();
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

	private void setRecord(Record record, String[] parts) {
		String userParts = parts[1];
		
		String[] players = userParts.split("\\s");
		if(players.length==2) {
			boolean reverse = players[0].compareTo(players[1]) > 0;
			record.setPlayer(getOrFakeUser(players[reverse ? 1 : 0]));
			record.setSecondPlayer(getOrFakeUser(players[reverse ? 0 : 1]));
		} else {
			User player = getOrFakeUser(userParts);
			record.setPlayer(player);
		}
		
		String scoreStr = parts[2];
		Long score = Long.parseLong(scoreStr);
		String dohranoStr = parts[3];
		Boolean dohrano = "1".equals(dohranoStr);
		String dobaStr = parts[4];
		Integer doba = (dobaStr != null && dobaStr.length() > 0) ? Integer
				.parseInt(dobaStr) : 0;
		record.setScore(score);
		record.setDuration(doba);
		record.setFinished(dohrano);
	}

	private void addRecord(Competetive game, Record record) {
		if (game == null) {
			log.fine("No game found for record " + record);
			return;
		}
		List<Record> records = game.getRecords();
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
		Record record = new Record();
		setRecord(record, parts);
		if ("mame".equals(emulator)) {
			CompetitiveGame game = gamesSingle.get(id);
			addRecord(game, record);
			singleRecords++;
		} else if ("mame2".equals(emulator)) {
			CompetitiveGame game = gamesDouble.get(id);
			addRecord(game, record);
			doubleRecords++;
		} else if ("amiga".equals(emulator)) {
			CompetitiveGame game = gamesAmiga.get(id);
			addRecord(game, record);
			amigaRecords++;
		} else {
			return;
		}
		//record.setPosition(competetive.getRecords().size());//rekordy jdou poporade, tj. pozice je rovna poradi
		if("mame".equals(emulator)){
			singlePlayers.add(record.getPlayer());
		}
	}

	private Float parseRating(String str) {
		String hodnoceniPercent = str.replace("%", "");
		return "-".equals(hodnoceniPercent) ? null : Float
				.parseFloat(hodnoceniPercent) / 100;
	}

	private void importNoncompetitiveGame(String[] parts) {
		// id,nazev,soubor,kategorie_id,hodnoceni,freeware,emulator,pocet_hracu)
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = Category.resolve(parts[3]);
		Float hodnoceni = parseRating(parts[4]);
		//Integer hracu = Integer.parseInt(parts[7]);
		LegacyPlatform platform = LegacyPlatform.resolve(parts[6]);

		Game game = new Game(id, cat, platform, title, file);
		game.setAverageRatings(hodnoceni);
		//game.setPlayerCount(hracu);
		this.gamesNoncompetetive.put(id, game);
		noncompetetivePlatforms.add(game.getPlatform());
		noncometetiveCategories.add(cat);
	}

	private void importAmigaGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,freeware,md5_disk1,md5_cfg,md5_start,emulator)
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = Category.resolve(parts[3]);
		String pravidla = parts[4];
		// String hrajeSeStr = parts[5];
		// Integer hrajeSe = Integer.parseInt(hrajeSeStr);
		Float hodnoceni = parseRating(parts[6]);
		String prvniStr = parts[7];
		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr
				: null;
		String hracuStr = parts[8];
		Integer hracu = Integer.parseInt(hracuStr);
//		String freeware = parts[9];
//		String md5disk1 = parts[10];
//		String md5cfg = parts[11];
//		String md5start = parts[12];
		CompetitiveGame game = new CompetitiveGame(id, cat, LegacyPlatform.AMIGA, title, file);
		game.setFirstPlayerSign(prvni);
		gamesAmiga.put(id, game);
		game.setRules(pravidla);
		game.setPlayerCount(hracu);
		game.setAverageRatings(hodnoceni);
		amigaCategories.add(cat);
	}

	private void importCompetitiveGame(String[] parts) {
		// (id,nazev,soubor,kategorie_id,pravidla,hrajesena,hodnoceni,prvni_zkratka,pocet_hracu,emulator)
		if (parts.length < 10) {
			// FIXME log
			return;
		}
		String id = parts[0];
		String title = parts[1];
		String file = parts[2];
		Category cat = Category.resolve(parts[3]);
		String pravidla = parts[4];
		Integer hrajeSe = Integer.parseInt(parts[5]);
		Float hodnoceni = parseRating(parts[6]);
		String prvniStr = parts[7];
		String prvni = (prvniStr != null && !prvniStr.contains("---")) ? prvniStr
				: null;
		String hracuStr = parts[8];
		Integer hracu = Integer.parseInt(hracuStr);
		String emulator = parts[9];

		CompetitiveGame game = null;

		if ("mame".equals(emulator)) {
			// do single mame game specific stuff
			CompetitiveGame singleGame = new CompetitiveGame(id, cat, LegacyPlatform.MAME, title, file);
			game = singleGame;
			game.setFirstPlayerSign(prvni);
			gamesSingle.put(id, singleGame);
			singleCategories.add(cat);
		} else if ("mame2".equals(emulator)) {
			// do double mame game specific stuff
			CompetitiveGame doubleGame = new CompetitiveGame(id, cat, LegacyPlatform.MAME, title, file);
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
			doubleCategories.add(cat);
		}

		game.setRules(pravidla);
		game.setPlayerCount(hracu);
		game.setAverageRatings(hodnoceni);
	}
	
	private Supplier<InputStream> in;

	public LegacyImporter(Supplier<InputStream> in){
		log = Application.getLogger(this);
		gamesSingle = new HashMap<String, CompetitiveGame>(2000);
		gamesDouble = new HashMap<String, CompetitiveGame>(200);
		gamesAmiga = new HashMap<String, CompetitiveGame>(100);
		gamesNoncompetetive = new HashMap<String, Game>(60000);

		this.in=in;
	}
	
	<T extends Game> List<T> prepareGames(Map<String, T> map,Class<T> clz) {
		if(map==null || map.isEmpty()){
			return Collections.emptyList();
		}
		Collection<T> collection = map.values();
		List<T> list = new ArrayList<T>(collection);
		log.fine("sorting "+clz.getSimpleName()+" ...");
		Collections.sort(list, TitleBasedGameComparator.INSTANCE);
		log.fine("positioning "+clz.getSimpleName()+" records ...");
		for(T t:list){
			if(!(t instanceof Competetive)){
				log.fine("skiping sorting records in non competetive game");
				break;
			}
			Competetive competetive = Competetive.class.cast(t);
			Collections.sort(competetive.getRecords(),ScoreBasedRecordComparator.INSTANCE);
			for(int i=0;i<competetive.getRecords().size();i++){
				Record r = competetive.getRecords().get(i);
				r.setPosition(i+1);
			}
		}
		return list;
	}

	public List<CompetitiveGame> getMameSingleGames() {
		return prepareGames(this.gamesSingle,CompetitiveGame.class);
	}

	public List<CompetitiveGame> getMameDoubleGames() {
		return prepareGames(this.gamesDouble,CompetitiveGame.class);
	}

	public List<CompetitiveGame> getCompetitiveGames() {
		return prepareGames(this.gamesAmiga,CompetitiveGame.class);
	}

	public List<Game> getNoncompetitiveGames() {
		return prepareGames(this.gamesNoncompetetive, Game.class);
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
			int competitiveGames = 0, noncompetitiveGames = 0, records = 0,readLines=0;
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
					importCompetitiveGame(parts);
					competitiveGames++;// both single and double mode
				} else if (line.startsWith("INSERT INTO hry_amiga")) {
					importAmigaGame(parts);
					competitiveGames++;
				} else if (line.startsWith("INSERT INTO hry")) {
					importNoncompetitiveGame(parts);
					noncompetitiveGames++;
				} else if (line.startsWith("INSERT INTO rekordy")) {
					importRecord(parts);
					records++;
				} 
			}
			log.info("done..., mame games=" + competitiveGames
					+ ", amiga games=" + competitiveGames + ", noncompetitive games="
					+ noncompetitiveGames + ", records=" + records+", read lines="+readLines);
		} catch (IOException x) {
			x.printStackTrace();
		} 
	}

	public Set<Category> getSingleCategories() {
		return singleCategories;
	}

	public Set<Category> getAmigaCategories() {
		return amigaCategories;
	}

	public Set<Category> getNoncometetiveCategories() {
		return noncometetiveCategories;
	}

	public Set<LegacyPlatform> getNoncompetetivePlatforms() {
		return noncompetetivePlatforms;
	}

	public Set<Category> getDoubleCategories() {
		return doubleCategories;
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
