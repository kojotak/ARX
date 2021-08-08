/**
 * 
 */
package cz.kojotak.arx;

import java.awt.Image;
import java.lang.Thread.UncaughtExceptionHandler;

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
		final Application app = Application.getInstance();
		
		//handle exceptions in EDT
		SwingUtilities.invokeLater(()->{
		        Thread.currentThread().setUncaughtExceptionHandler((thread,ex)-> {
						Application.getLogger(thread).error("Exception in EDT", ex);						
				});
		});
		
		Image image = app.getIconLoader().loadImage("logo.jpg");
		ImageIcon myImage = new ImageIcon(image);
		final SplashScreen splash = new SplashScreen(myImage);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splash.setLocationRelativeTo(null);
				splash.setProgressMax(100);
				splash.setScreenVisible(true);
			}});
		
		SplashWorker postInit = new SplashWorker(splash, app.getJobs());
		postInit.execute();// run in separate thread
		try {
			postInit.get();// wait until the result is available
		} catch (Exception ex) {
			app.getLogger(DesktopRunner.class).error(
					"cannot initialize application", ex);
		}
		splash.setProgress("initializing GUI", RunnableWithProgress.UNKNOWN);
		app.finishInitialization();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						try {
							UIManager.setLookAndFeel(info.getClassName());
						} catch (Exception ex) {
							app.getLogger(MainWindow.class).warn(
									"failed to load Nimbus L&F", ex);
						}
						app.getLogger(MainWindow.class).info("L&F switched to Nimbus");
						break;
					}
				}
				MainWindow inst = new MainWindow();
				inst.setLocationRelativeTo(null);
				splash.setVisible(false);
				inst.setVisible(true);
			}
		});
	}

}
