/**
 *
 */
package cz.kojotak.arx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.mode.Mode;
import cz.kojotak.arx.domain.Player;
import cz.kojotak.arx.domain.enums.Language;
import cz.kojotak.arx.domain.mode.SinglePlayerMode;
import cz.kojotak.arx.domain.mode.TwoPlayerMode;
import cz.kojotak.arx.properties.Icons;
import cz.kojotak.arx.properties.Licence;
import cz.kojotak.arx.properties.Localization;
import cz.kojotak.arx.properties.Properties;
import cz.kojotak.arx.ui.MainWindow;
import cz.kojotak.arx.util.Downloader;
import cz.kojotak.arx.util.StorageUnit;

public final class Application {

	private String currentDir;

	protected Logger log;

	private Properties properties;
	private Localization localization;
	private Licence licence;
	private Icons icons;
	private final Downloader downloader = null;
	private SqliteImporter importer;
	private Language language;
	private final IconLoader iconLoader;
	private SinglePlayerMode singlePlayerMode;
	private TwoPlayerMode arcadeTwoPlayerMode;
	private Mode currentMode;
	private MainWindow mainWindow;
	private final Rotaxmame rotax;
	private List<Mode> modes;
	private final List<Player> players = new ArrayList<Player>();
	Player currentPlayer = null;

	private static Application app = new Application();

	public static Application getInstance() {
		return app;
	}

	public void finishInitialization() {
		this.players.addAll(importer.getPlayers());
		Collections.sort(players, Comparator.comparing(p->{
			return p.nick();
		}));
		for(Player p : this.players) {
			//TODO proper player selection
			if("COY".equals(p.nick()) || p.nick().startsWith("Coy")){
				setCurrentPlayer(p);
			}
		}
		this.singlePlayerMode = new SinglePlayerMode(importer);
		this.arcadeTwoPlayerMode = new TwoPlayerMode(importer);
		this.currentMode = singlePlayerMode;
		this.modes = new ArrayList<Mode>();
		this.modes.add(singlePlayerMode);
		this.modes.add(arcadeTwoPlayerMode);
	}

	private Application() {
		currentDir = System.getProperty("user.dir");
		this.rotax = new Rotaxmame();

		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(getClass().getClassLoader().getResourceAsStream("logging.properties"));
		} catch (Exception ex) {
			log.log(Level.SEVERE, "cannot reconfigure java.util.LogManager", ex);
		}
		
		log = Logger.getLogger(getClass().getName());
		log.info("logger ready");

		language = Language.CZECH;
		iconLoader = new IconLoader(this);
		properties = new Properties(language);
		localization = new Localization(language);
		icons = new Icons(language);
		licence = new Licence(language);
//		downloader = new Downloader(LegacySqliteImporter.RM_DB_URL);
		//SqliteImporter = new LegacySqliteImporter(this::getDBInputStream); 
//		SqliteImporter = new LegacySqliteImporter(downloader::getDBInputStream);
		importer = new SqliteImporter(rotax.getRotaxDBPath());
	}

//	@EventSubscriber //chybi volani annotation processoru
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Mode resolveMode(String str) {
		Mode mode = singlePlayerMode;// default
		for (Mode m : modes) {
			if (m.getName().equals(str)) {
				mode = m;
				break;
			}
		}
		return mode;
	}

	public String readFile(String name) {
		String path = currentDir + File.separator + "res" + File.separator
				+ name;
		FileInputStream stream = null;
		String string = null;
		try {
			stream = new FileInputStream(new File(path));
		} catch (FileNotFoundException ex) {
			log.log(Level.SEVERE, "cannot found " + path, ex);
			return null;
		}
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc
					.size());
			/* Instead of using default, pass in a decoder. */
			string = Charset.defaultCharset().decode(bb).toString();
		} catch (IOException ex) {
			this.log.log(Level.SEVERE, "cannot read from " + path, ex);
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
				this.log.log(Level.SEVERE, "cannot close stream from " + path, ex);
			}
		}
		return string;
	}

	public List<Job> getJobs() {
		List<Job> list = new ArrayList<Job>();

		Job downloaderJob = new DownloaderJob(downloader, 100,	getLocalization().getString(this, "DOWNLOAD_PROGRESS"));
		
		Job SqliteImporterJob = new Job(importer, 100, getLocalization().getString(this, "SPLASHSCREEN_PROGRESS"));
//		 list.add(new DummyJob(50));
		list.add(downloaderJob);
		//list.add(counterJob);
		list.add(SqliteImporterJob);

		return list;
	}

	public AtomicLong bytesToImport = new AtomicLong(0);

	public static class DownloaderJob extends Job {

		public DownloaderJob(Downloader runnable, int weight, String description) {
			super(runnable, weight, description);
		}

		@Override
		public String getDescription() {
			RunnableWithProgress runnable = this.getRunnable();
			String current = StorageUnit.toString(runnable.current());		
			return super.getDescription() + " "+current;
		}

	}

	public static class Job {
		private RunnableWithProgress runnable;
		private int weight;
		private String description;

		public Job(RunnableWithProgress runnable, int weight, String description) {
			super();
			this.runnable = runnable;
			this.weight = weight;
			this.description = description;
		}

		public RunnableWithProgress getRunnable() {
			return runnable;
		}

		public int getWeight() {
			return weight;
		}

		public String getDescription() {
			return description;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public Localization getLocalization() {
		return localization;
	}

	public IconLoader getIconLoader() {
		return iconLoader;
	}

	public Icons getIcons() {
		return icons;
	}

	public Mode getCurrentMode() {
		return currentMode;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Properties getProperties() {
		return properties;
	}

	public Licence getLicence() {
		return licence;
	}

	public SqliteImporter getSqliteImporter() {
		return importer;
	}

	public Language getLanguage() {
		return language;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public List<Mode> getModes() {
		return modes;
	}

	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Rotaxmame getRotaxmame() {
		return rotax;
	}

}
