/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXImagePanel.Style;
import org.jdesktop.swingx.JXTaskPane;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.Game;
import cz.kojotak.arx.ui.icon.GUIIcons;

/**
 * @date 30.5.2010
 * @author Kojotak 
 */
public class DetailTaskPane extends JXTaskPane{

	private static final long serialVersionUID = 2996295690816666019L;

	private JXImagePanel ipanel;
	private JXHyperlink link;
	private Application app;
	private final Logger logger = Logger.getLogger(getClass().getName());
	public DetailTaskPane() {
		super();
		AnnotationProcessor.process(this);
		app = Application.getInstance();
		String label = app.getLocalization().getString(this, "LABEL");
		this.setTitle(label);
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.DETAIL));
		ipanel = new JXImagePanel();
		var dim = new Dimension(200,400);
		ipanel.setStyle(Style.SCALED_KEEP_ASPECT_RATIO);
		ipanel.setMaximumSize(dim);
		ipanel.setMinimumSize(dim);
		ipanel.setPreferredSize(dim);
		link = new JXHyperlink();
		this.add(link);
		this.add(ipanel);
	}
	
	private void setHyperlink(URI uri){
		link.setURI(uri);
		link.setText("RotaxMAME");
		link.setToolTipText("otevre web pro danou hru");
		link.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.EXTERNAL_LINK));
	}
	
	private void setImage(Image image){
		ipanel.setImage(image);
		ipanel.revalidate();
		ipanel.repaint();
	}

	@EventSubscriber
	public void valueChanged(Game game) {
		final Application app = Application.getInstance();
		Image image = app.getIconLoader().loadGameScreenshot(game.getId());
//		ImageDownloader downloader = null;
//		if(image==null){
//			image = app.getIconLoader().loadImageFromIcon(ImageDetail.PENDING);
//			downloader = new ImageDownloader(app, file);
//		}
		setImage(image);
		String uriStr = "https://www.rotaxmame.cz/game/"+game.getId();
		try{
			URI uri = new URI(uriStr);
			setHyperlink(uri);
		}catch(URISyntaxException ex){
			logger.log(Level.WARNING, "cannot set uri "+uriStr);
		}

		
//		if(downloader!=null){
//			downloader.execute();
//		}
	}
	
//	class ImageDownloader extends SwingWorker<Image,Void>{
//		private Application app;
//		private String name;
//		
//		ImageDownloader(Application app, String name) {
//			super();
//			this.app = app;
//			this.name = name;
//		}
//
//		@Override
//		protected Image doInBackground() throws Exception {
//			Application.getLogger(DetailTaskPane.this).fine("working...");
//			return app.getIconLoader().downloadAndLoadImage(name);
//		}
//
//		@Override
//		protected void done() {
//			Application.getLogger(DetailTaskPane.this).fine("done...");
//			Image downloaded = null;
//			try{
//				downloaded = get();
//			}catch (InterruptedException ignore) {}
//            catch (java.util.concurrent.ExecutionException e) {
//                String why = null;
//                Throwable cause = e.getCause();
//                if (cause != null) {
//                    why = cause.getMessage();
//                } else {
//                    why = e.getMessage();
//                }
//                Application.getLogger(DetailTaskPane.this).warning("Error retrieving "+name+ " because: " + why);
//            }
//            if(downloaded==null){
//            	downloaded = app.getIconLoader().loadImageFromIcon(ImageDetail.MISSING);
//            }
//            setImage(downloaded);
//		}
//					
//	};

}
