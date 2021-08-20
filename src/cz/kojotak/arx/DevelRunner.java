package cz.kojotak.arx;

import java.util.Properties;

/** Runner for local development with hardcoded rotaxmame path */
public class DevelRunner {

	public static void main(String[] args) {
		Properties systemProps = System.getProperties();
		systemProps.setProperty("rotax.dir", "D:\\Rotaxmame");
		System.setProperties(systemProps);
		DesktopRunner.main(args);
	}

}
