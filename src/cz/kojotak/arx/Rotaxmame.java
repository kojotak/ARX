package cz.kojotak.arx;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kojotak.arx.common.StreamGobbler;
import cz.kojotak.arx.domain.Game;

public class Rotaxmame {

	private static final String EXECUTABLE = "Rotaxmame.exe";
	private final String rotaxDir;
	private final Logger logger;

	public Rotaxmame() {
		logger = Logger.getLogger(getClass().getName());
		String configured = System.getProperty("rotax.dir");
		
		File file = new File(configured, EXECUTABLE);
		if(file.exists()) {
			rotaxDir = configured;
			logger.info("rotaxmame found in: " + rotaxDir);
		}else {
			rotaxDir = null;
			logger.warning("rotaxmame not found in: " + rotaxDir);
		}
	}
	
	public String getDatabasePath() {
		if(rotaxDir==null) {
			return null;
		}
		return rotaxDir + File.separator + "-" + File.separator + "db.db";
	}
	
	public String getScreenshotPath() {
		if(rotaxDir==null) {
			return null;
		}
		return rotaxDir + File.separator + "-" + File.separator + "screenshot" + File.separator;
	}
	
	public void run(Game game){
		if(rotaxDir==null) {
			logger.info("rotaxmame directory not known to start: " + game);
			return;
		}
		logger.info("going to run : " + game);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cmd.exe", "/c", EXECUTABLE + " " + game.getId());
		builder.directory(new File(rotaxDir));
		try {
			Process process = builder.start();
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), out-> logger.log(Level.FINE, out));
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			int exitCode = process.waitFor();
			logger.info("rotaxmame finished with exit code " + exitCode);
		} catch (IOException | InterruptedException ex) {
			logger.log(Level.SEVERE, "Failed to run rotaxmame", ex);
		}
	}
}
