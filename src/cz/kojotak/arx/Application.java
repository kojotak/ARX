/**
 *
 */
package cz.kojotak.arx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.domain.Language;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.mode.AmigaMode;
import cz.kojotak.arx.domain.mode.ArcadeMode;
import cz.kojotak.arx.domain.mode.NoncompetetiveMode;
import cz.kojotak.arx.domain.mode.TwoPlayerMode;
import cz.kojotak.arx.properties.Icons;
import cz.kojotak.arx.properties.Licence;
import cz.kojotak.arx.properties.Localization;
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
	private final Downloader downloader;
	private LegacyImporter importer;
	private Language language;
	private IconLoader iconLoader;
	private AmigaMode amigaMode;
	private ArcadeMode arcadeMode;
	private TwoPlayerMode arcadeTwoPlayerMode;
	private NoncompetetiveMode noncompetetiveMode;
	private Mode<?> currentMode;
	private MainWindow mainWindow;
	private List<Mode<?>> modes;
	List<User> players = new ArrayList<User>();

	public static String getRmDbUrl() {
		return RM_DB_URL;
	}

	User currentUser = null;

	private static Application app = new Application();

	public static Application getInstance() {
		return app;
	}

	public void finishInitialization() {
		this.arcadeMode = new ArcadeMode(importer);
		this.arcadeTwoPlayerMode = new TwoPlayerMode(importer);
		this.amigaMode = new AmigaMode(importer);
		this.noncompetetiveMode = new NoncompetetiveMode(importer);
		this.currentMode = arcadeMode;// FIXME
		this.modes = new ArrayList<Mode<?>>();
		this.modes.add(arcadeMode);
		this.modes.add(arcadeTwoPlayerMode);
		this.modes.add(amigaMode);
		this.modes.add(noncompetetiveMode);
	}

	private Application() {
		currentDir = System.getProperty("user.dir");
		
		log = getLogger(Application.class);
		log.debug("logger ready");

		// java.util.logging initialization for chmpane
		//System.setProperty("java.util.logging.config.file", "logging.properties");
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(getClass().getClassLoader().getResourceAsStream("logging.properties"));
		} catch (Exception ex) {
			log.error("cannot reconfigure java.util.LogManager", ex);
		}

		language = Language.CZECH;
		iconLoader = new IconLoader("icons/", "images/", this);
		properties = new Properties(language);
		localization = new Localization(language);
		icons = new Icons(language);
		licence = new Licence(language);
		downloader = new Downloader(RM_DB_URL);
		//importer = new LegacyImporter(this::getDBInputStream); 
		importer = new LegacyImporter(downloader::getDBInputStream);

		//TODO remove this mock someday
		this.players.add(importer.getOrFakeUser("COY"));
		this.players.add(importer.getOrFakeUser("VLD"));
		this.players.add(importer.getOrFakeUser("MIR"));
		this.players.add(importer.getOrFakeUser("SCH"));
		this.players.add(importer.getOrFakeUser("ORI"));
		setCurrentUser(players.get(0));
	}

	public String getTmpDir() {
		return currentDir + File.separator + "tmp";
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public List<User> getPlayers() {
		return players;
	}

	public static Logger getLogger(Object whoCalls) {
		return getLogger(whoCalls.getClass());
	}

	public static Logger getLogger(Class<?> whoCalls) {
		return org.apache.logging.log4j.LogManager.getLogger(whoCalls.getSimpleName());
	}

	public Mode<?> resolveMode(String str) {
		Mode<?> mode = arcadeMode;// default
		for (Mode<?> m : modes) {
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
			log.error("cannot found " + path, ex);
			return null;
		}
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc
					.size());
			/* Instead of using default, pass in a decoder. */
			string = Charset.defaultCharset().decode(bb).toString();
		} catch (IOException ex) {
			this.log.error("cannot read from " + path, ex);
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
				this.log.error("cannot close stream from " + path, ex);
			}
		}
		return string;
	}

	public InputStream getDBInputStream() {
		return getClass().getClassLoader().getResourceAsStream("rotaxmame_databaze.gz");
	}

	public List<Job> getJobs() {
		List<Job> list = new ArrayList<Job>();

		Job downloaderJob = new DownloaderJob(downloader, 100,	getLocalization().getString(this, "DOWNLOAD_PROGRESS"));
		
		Job importerJob = new Job(importer, 100, getLocalization().getString(this, "SPLASHSCREEN_PROGRESS"));
//		 list.add(new DummyJob(50));
		list.add(downloaderJob);
		//list.add(counterJob);
		list.add(importerJob);

		return list;
	}

	public AtomicLong bytesToImport = new AtomicLong(0);

	public static final String RM_DB_URL = "https://github.com/kojotak/ARX/blob/reloaded/res/rotaxmame_databaze.gz?raw=true";

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

	public Mode<?> getCurrentMode() {
		return currentMode;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Properties getProperties() {
		return properties;
	}

	public Licence getLicence() {
		return licence;
	}

	public LegacyImporter getImporter() {
		return importer;
	}

	public Language getLanguage() {
		return language;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public List<Mode<?>> getModes() {
		return modes;
	}

	public void setCurrentMode(Mode<?> currentMode) {
		this.currentMode = currentMode;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
}
