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
import java.util.List;
import java.util.logging.LogManager;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cz.kojotak.arx.domain.Language;
import cz.kojotak.arx.domain.Mode;
import cz.kojotak.arx.domain.User;
import cz.kojotak.arx.domain.impl.SimpleUser;
import cz.kojotak.arx.domain.mode.AmigaMode;
import cz.kojotak.arx.domain.mode.ArcadeMode;
import cz.kojotak.arx.domain.mode.NoncompetetiveMode;
import cz.kojotak.arx.domain.mode.TwoPlayerMode;
import cz.kojotak.arx.properties.Icons;
import cz.kojotak.arx.properties.Localization;

/**
 * @date 25.10.2009
 * @author Kojotak
 */
public final class Application {

	// FIXME onload?
	public static String currentDir = System.getProperty("user.dir");

	protected Logger log;
	private Properties props;
	private Localization l10n;
	private Icons icons;
	private ImporterFactory ifa;
	private Importer importer;
	private Language language;
	private IconLoader iconLoader;
	private AmigaMode amiga;
	private ArcadeMode arcade;
	private TwoPlayerMode arcade2;
	private NoncompetetiveMode noncompetetive;
	private Mode<?> currentMode;
	private List<Mode<?>> all;
	private DefaultHttpClient client;
	List<User> players = new ArrayList<User>();
	User player = null;

	private static Application app = new Application();

	public static Application getInstance() {
		return app;
	}

	public void init() {
		language = Language.CZECH;
		ifa = new ImporterFactory();
		String icoPath = Application.currentDir + File.separator
		+ "res" + File.separator + "icons" + File.separator;
		String imgPath = Application.currentDir + File.separator
		+ "tmp" + File.separator + "images" + File.separator;
		iconLoader = new IconLoader(icoPath,imgPath,this);
		props = new Properties(language);
		l10n = new Localization(language);
		icons = new Icons(language);

		this.initImporter();
		this.initModes();
		//this.initHttpClient();
		this.initPlayers();
	}

	public void destroy() {
		client.getConnectionManager().shutdown();
	}

	private void initModes() {
		this.arcade = new ArcadeMode(importer);
		this.arcade2 = new TwoPlayerMode(importer);
		this.amiga = new AmigaMode(importer);
		this.noncompetetive = new NoncompetetiveMode(importer);
		this.currentMode = arcade;// FIXME
		this.all = new ArrayList<Mode<?>>();
		this.all.add(arcade);
		this.all.add(arcade2);
		this.all.add(amiga);
		this.all.add(noncompetetive);
	}

	private void initImporter() {
		long startTime = System.currentTimeMillis();
		long startMem = Runtime.getRuntime().freeMemory();

		// importer = ifa.createFromGziped(currentDir + File.separator +
		// "tmp"+File.separator+"rotaxmame_databaze.gz");
		importer = ifa.createFromWeb();
		long endTime = System.currentTimeMillis();
		long endMem = Runtime.getRuntime().freeMemory();
		log.info("import done in " + (double)(endTime - startTime) / 1000
				+ " s, eaten " + (endMem - startMem) / 1024 + " kB RAM");

	}
/*
	private void logCookies() {
		List<Cookie> cookies = client.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			log.info("no cookies");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				log.debug("cookie - " + cookies.get(i).toString());
			}
		}
	}
*/
	//FIXME
	private String chatStr="";
	public String getChatStr() {
		return chatStr;
	}
/*
	private void initHttpClient() {
		client = new DefaultHttpClient();
		client.addRequestInterceptor(new HttpRequestInterceptor() {

            public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }

        });

        client.addResponseInterceptor(new HttpResponseInterceptor() {

            public void process(
                    final HttpResponse response,
                    final HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(
                                    new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }

        });

        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);


      	HttpContext localContext = new BasicHttpContext();
		HttpResponse response = null;
		HttpPost auth = new HttpPost("http://www.rotaxmame.cz/index.php");
		auth.addHeader(new BasicHeader("Referer","http://www.rotaxmame.cz/"));


        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("prihjmeno", "coyot"));
        nvps.add(new BasicNameValuePair("heslo", "rotaxheslo"));
        nvps.add(new BasicNameValuePair("odeslat", "Prihlasit"));
        try{
        	auth.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        }catch(UnsupportedEncodingException ex){
        	log.error("unknown encoding",ex);
        }

		try {
			response = client.execute(auth, localContext);
			log.info("login> " + response.getStatusLine());
			this.logCookies();
		} catch (Exception ex) {
			log.error("cannot connect to chat", ex);
		}
		HttpEntity entity = response.getEntity();
		if(entity!=null){
			try{
				entity.consumeContent();
			}catch(IOException ex){
				log.error("cannot consume content", ex);
			}
		}

		HttpGet get = new HttpGet("http://www.rotaxmame.cz/php/chat_new.php");
		try {
			response = client.execute(get, localContext);
			entity=response.getEntity();
			log.info("chat> " + response.getStatusLine());
			this.logCookies();
		} catch (Exception ex) {
			log.error("cannot connect to chat", ex);
		}

		if(entity!=null){
			try{
				//log.debug(EntityUtils.toString(entity));
				chatStr=EntityUtils.toString(entity);
			}catch(Exception ex){
				log.error("cannot convert content",ex);
			}
		}
	}
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
		String etcDirPath = currentDir + File.separator + "etc"
				+ File.separator;

		// log4 initialization
		PropertyConfigurator.configure(etcDirPath + "log4j.properties");
		log = Logger.getLogger(Application.class);
		log.debug("logger ready");

		// java.util.logging initialization for chmpane
		System.setProperty("java.util.logging.config.file", etcDirPath
				+ "logging.properties");
		try {
			LogManager.getLogManager().readConfiguration();
		} catch (Exception ex) {
			log.error("cannot reconfigure java.util.LogManager", ex);
		}
	}

	public User getCurrentUser() {
		return player;
	}

	public Mode<?> getCurrentMode() {
		return currentMode;
	}

	public void setMode(Mode<?> mode) {
		this.currentMode = mode;
	}

	public void setPlayer(String name) {
		User user = new SimpleUser(name);
		this.player = user;
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

	public Importer getImporter() {
		return importer;
	}

	public Language getLanguage() {
		return language;
	}

	public Logger getLogger(Object whoCalls) {
		return Logger.getLogger(whoCalls.getClass());
	}

	public Logger getLogger(Class<?> whoCalls) {
		return Logger.getLogger(whoCalls);
	}

	public IconLoader getIconLoader() {
		return iconLoader;
	}

	public Properties getProperties() {
		return props;
	}
	
	public Localization getLocalization(){
		return l10n;
	}
	
	public Icons getIcons(){
		return icons;
	}

	public AmigaMode getAmigaMode() {
		return amiga;
	}

	public ArcadeMode getArcadeMode() {
		return arcade;
	}

	public TwoPlayerMode getArcadeTwoPlayerMode() {
		return arcade2;
	}

	public NoncompetetiveMode getNoncompetetiveMode() {
		return noncompetetive;
	}

	public List<Mode<?>> getModes() {
		return all;
	}

	public Mode<?> resolveMode(String str) {
		Mode<?> mode = arcade;// default
		for (Mode<?> m : all) {
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

	public HttpClient getHttpClient() {
		return client;
	}

}
