/**
 * 
 */
package cz.kojotak.arx;

import java.awt.Image;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.ui.MainWindow;
import cz.kojotak.arx.ui.SplashScreen;
import cz.kojotak.arx.ui.SplashWorker;

/**
 * @date 26.9.2010
 * @author Kojotak
 */
public class DesktopRunner {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO remove this localhost workaround with proper UI in issue #47
		Properties systemProps = System.getProperties();
		systemProps.setProperty("rotax.dir", "D:\\Rotaxmame");
		System.setProperties(systemProps);
		
		final Application app = Application.getInstance();
		Logger logger = Logger.getLogger(DesktopRunner.class.getName());
		
		//handle exceptions in EDT
		SwingUtilities.invokeLater(()->{
		        Thread.currentThread().setUncaughtExceptionHandler((thread,ex)-> {
						logger.log(Level.SEVERE, "Exception in EDT", ex);						
				});
		});
		
		Image image = app.getIconLoader().loadImage("logo.jpg");
		ImageIcon myImage = new ImageIcon(image);
		final SplashScreen splash = new SplashScreen(myImage);
		
		SwingUtilities.invokeLater(() -> {
				splash.setLocationRelativeTo(null);
				splash.setProgressMax(100);
				splash.setScreenVisible(true);
			});
		
		SplashWorker postInit = new SplashWorker(splash, app.getJobs());
		postInit.execute();// run in separate thread
		try {
			postInit.get();// wait until the result is available
		} catch (Exception ex) {
			logger.severe("cannot initialize application " + ex);
			System.exit(1);
		}
		splash.setProgress("initializing GUI", RunnableWithProgress.UNKNOWN);
		app.finishInitialization();

		SwingUtilities.invokeLater(() -> {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (Exception ex) {
						logger.log(Level.WARNING, "failed to load Nimbus L&F", ex);
					}
					logger.info("L&F switched to Nimbus");
					break;
				}
			}
			MainWindow inst = new MainWindow();
//			FilterModel filterModel = new FilterModel();
//			filterModel.setPlatform(LegacyPlatform.MAME.toPlatform());
//			EventBus.publish(filterModel);
			inst.setLocationRelativeTo(null);
			splash.setVisible(false);
			inst.setVisible(true);
		});
	}

}
