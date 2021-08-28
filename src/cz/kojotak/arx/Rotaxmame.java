package cz.kojotak.arx;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kojotak.arx.common.StreamGobbler;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.ui.GameTable;

public class Rotaxmame {

	private final String rotaxDir;
	private final Logger logger;

	public Rotaxmame() {
		logger = Logger.getLogger(getClass().getName());
		rotaxDir = System.getProperty("rotax.dir");
		
		//TODO validate using Rotaxmame.exe
		
		//TODO log validation result
	}
	
	public String getRotaxDir() {
		return rotaxDir;
	}
	
	public String getRotaxDBPath() {
		return rotaxDir + File.separator + "-" + File.separator + "db.db";
	}
	
	public String getRotaxScreenshotPath() {
		return rotaxDir + File.separator + "-" + File.separator + "screenshot" + File.separator;
	}
	
	public void run(Game game){
		logger.info("going to run : " + game);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cmd.exe", "/c", "Rotaxmame.exe "+game.getId());
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
