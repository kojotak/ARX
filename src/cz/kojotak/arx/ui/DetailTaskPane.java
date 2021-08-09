/**
 * 
 */
package cz.kojotak.arx.ui;

import java.awt.Image;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.swing.SwingWorker;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXImagePanel.Style;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.domain.game.Game;
import cz.kojotak.arx.domain.enums.ImageDetail;
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
	public DetailTaskPane() {
		super();
		AnnotationProcessor.process(this);
		app = Application.getInstance();
		String label = app.getLocalization().getString(this, "LABEL");
		this.setTitle(label);
		this.setIcon(app.getIconLoader().tryLoadIcon(GUIIcons.DETAIL));
		ipanel = new JXImagePanel();
		ipanel.setStyle(Style.SCALED_KEEP_ASPECT_RATIO);
		link = new JXHyperlink();
		setHyperlink();
		this.add(link);
		this.add(ipanel);
	}
	
	private void setHyperlink(){
		link.setText("MAWS");
		link.setToolTipText("otevre MAWS pro danou hru");
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
		final String file = game.getFile();
		Image image = app.getIconLoader().loadImage(file);
		ImageDownloader downloader = null;
		if(image==null){
			image = app.getIconLoader().loadImageFromIcon(ImageDetail.PENDING);
			downloader = new ImageDownloader(app, file);
		}
		setImage(image);
		String uri = "http://maws.mameworld.info/maws/romset/"+file;
		try{
			link.setURI(new URI(uri));
		}catch(URISyntaxException ex){
			Application.getLogger(this).log(Level.SEVERE, "cannot set uri "+uri,ex);
		}
		setHyperlink();
		
		if(downloader!=null){
			downloader.execute();
		}
	}
	
	class ImageDownloader extends SwingWorker<Image,Void>{
		private Application app;
		private String name;
		
		ImageDownloader(Application app, String name) {
			super();
			this.app = app;
			this.name = name;
		}

		@Override
		protected Image doInBackground() throws Exception {
			Application.getLogger(DetailTaskPane.this).fine("working...");
			return app.getIconLoader().downloadAndLoadImage(name);
		}

		@Override
		protected void done() {
			Application.getLogger(DetailTaskPane.this).fine("done...");
			Image downloaded = null;
			try{
				downloaded = get();
			}catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
                Application.getLogger(DetailTaskPane.this).warning("Error retrieving "+name+ " because: " + why);
            }
            if(downloaded==null){
            	downloaded = app.getIconLoader().loadImageFromIcon(ImageDetail.MISSING);
            }
            setImage(downloaded);
		}
					
	};

}
