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
import cz.kojotak.arx.domain.impl.SimpleUser;
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
	private ImporterFactory importerFactory;
	private Importer importer;
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

	public void init() {
		importerFactory = new ImporterFactory();

		// this.importer = new Importer();//delete me
		// this.doImport();
		// this.postInit();
		// this.initHttpClient();
		this.initPlayers();
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

	 private void doImport() {
		 long startTime = System.currentTimeMillis();
		 long startMem = Runtime.getRuntime().freeMemory();
		
		  importer = importerFactory.createFromGziped(currentDir + File.separator + "tmp"+File.separator+"rotaxmame_databaze.gz");
//		 importer = importerFactory.createFromWeb();
		 long endTime = System.currentTimeMillis();
		 long endMem = Runtime.getRuntime().freeMemory();
		 log.info("import done in " + (double)(endTime - startTime) / 1000
		 + " s, eaten " + (endMem - startMem) / 1024 + " kB RAM");
		
	 }
	/*
	 * private void logCookies() { List<Cookie> cookies =
	 * client.getCookieStore().getCookies(); if (cookies.isEmpty()) {
	 * log.info("no cookies"); } else { for (int i = 0; i < cookies.size(); i++)
	 * { log.debug("cookie - " + cookies.get(i).toString()); } } }
	 */

	/*
	 * private void initHttpClient() { client = new DefaultHttpClient();
	 * client.addRequestInterceptor(new HttpRequestInterceptor() {
	 * 
	 * public void process( final HttpRequest request, final HttpContext
	 * context) throws HttpException, IOException { if
	 * (!request.containsHeader("Accept-Encoding")) {
	 * request.addHeader("Accept-Encoding", "gzip"); } }
	 * 
	 * });
	 * 
	 * client.addResponseInterceptor(new HttpResponseInterceptor() {
	 * 
	 * public void process( final HttpResponse response, final HttpContext
	 * context) throws HttpException, IOException { HttpEntity entity =
	 * response.getEntity(); Header ceheader = entity.getContentEncoding(); if
	 * (ceheader != null) { HeaderElement[] codecs = ceheader.getElements(); for
	 * (int i = 0; i < codecs.length; i++) { if
	 * (codecs[i].getName().equalsIgnoreCase("gzip")) { response.setEntity( new
	 * GzipDecompressingEntity(response.getEntity())); return; } } } }
	 * 
	 * });
	 * 
	 * client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
	 * CookiePolicy.BROWSER_COMPATIBILITY);
	 * 
	 * 
	 * HttpContext localContext = new BasicHttpContext(); HttpResponse response
	 * = null; HttpPost auth = new
	 * HttpPost("http://www.rotaxmame.cz/index.php"); auth.addHeader(new
	 * BasicHeader("Referer","http://www.rotaxmame.cz/"));
	 * 
	 * 
	 * List<NameValuePair> nvps = new ArrayList<NameValuePair>(); nvps.add(new
	 * BasicNameValuePair("prihjmeno", "coyot")); nvps.add(new
	 * BasicNameValuePair("heslo", "rotaxheslo")); nvps.add(new
	 * BasicNameValuePair("odeslat", "Prihlasit")); try{ auth.setEntity(new
	 * UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	 * }catch(UnsupportedEncodingException ex){
	 * log.error("unknown encoding",ex); }
	 * 
	 * try { response = client.execute(auth, localContext); log.info("login> " +
	 * response.getStatusLine()); this.logCookies(); } catch (Exception ex) {
	 * log.error("cannot connect to chat", ex); } HttpEntity entity =
	 * response.getEntity(); if(entity!=null){ try{ entity.consumeContent();
	 * }catch(IOException ex){ log.error("cannot consume content", ex); } }
	 * 
	 * HttpGet get = new HttpGet("http://www.rotaxmame.cz/php/chat_new.php");
	 * try { response = client.execute(get, localContext);
	 * entity=response.getEntity(); log.info("chat> " +
	 * response.getStatusLine()); this.logCookies(); } catch (Exception ex) {
	 * log.error("cannot connect to chat", ex); }
	 * 
	 * if(entity!=null){ try{ //log.debug(EntityUtils.toString(entity));
	 * chatStr=EntityUtils.toString(entity); }catch(Exception ex){
	 * log.error("cannot convert content",ex); } } }
	 */
	private void initPlayers() {
		this.setPlayer("SCH");
		this.setPlayer("VLD");
		this.setPlayer("RGB");
		this.setPlayer("HYP");
		this.setPlayer("ORI");
		this.setPlayer("COY");

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
	}

	public String getTmpDir() {
		return currentDir + File.separator + "tmp";
	}

	public void setPlayer(String name) {
		User user = new SimpleUser(name);
		this.currentUser = user;
		this.players.add(user);
	}

	public List<String> getPlayers() {
		List<User> users = players;
		List<String> names = new ArrayList<String>();
		for (User u : users) {
			names.add(u.getId());
		}
		return names;
	}

	public Logger getLogger(Object whoCalls) {
		return getLogger(whoCalls.getClass());
	}

	public Logger getLogger(Class<?> whoCalls) {
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

	public InputStream getZipedDatabaseFile() {
//		File tmpDb = new File("rotaxmame_databaze.gz");
//		if(!tmpDb.exists()){
//			try {
//				tmpDb.createNewFile();
//				log.info(tmpDb + " has been created");
//			} catch (IOException e) {
//				log.error("cannot create tmp file for rotax database",e);
//			}
//		}
//		return tmpDb;
		return getClass().getClassLoader().getResourceAsStream("rotaxmame_databaze.gz");
	}

	public List<Job> getJobs() {
		List<Job> list = new ArrayList<Job>();

//		Downloader downloader = new Downloader(this, RM_DB_URL, getZipedDatabaseFile());
//		Job downloaderJob = new DownloaderJob(downloader, 100,
//				"stahov�n� datab�ze");
		//LineCounter counter = new LineCounter(getZipedDatabaseFile(), this);
		//Job counterJob = new Job(counter, 5, "zji��ov�n� velikosti datab�ze");
		this.importer = new Importer(getZipedDatabaseFile()); 
			//this.importerFactory.createFromGziped(getZipedDatabaseFile());
		Job importerJob = new Job(importer, 100, getLocalization().getString(this, "SPLASHSCREEN_PROGRESS"));
		// list.add(new DummyJob(50));
//		list.add(downloaderJob);
		//list.add(counterJob);
		list.add(importerJob);

		return list;
	}

	public AtomicLong bytesToImport = new AtomicLong(0);

	public static final String RM_DB_URL = "http://rotaxmame.cz/php/download3.php?co=kompletni_databaze";

	public static class DownloaderJob extends Job {

		public DownloaderJob(Downloader runnable, int weight, String description) {
			super(runnable, weight, description);
		}

		@Override
		public String getDescription() {
			
			RunnableWithProgress runnable = this.getRunnable();
			
//			String max = StorageUnit.toString(runnable.max());
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

	public Importer getImporter() {
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
