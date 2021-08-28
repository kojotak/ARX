/**
 *
 */
package cz.kojotak.arx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import cz.kojotak.arx.ui.icon.EmptyIcon;
import net.sf.image4j.codec.ico.ICODecoder;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class IconLoader {

	private final String icoPrefix;
	private final String imgPrefix;
	private final String screenPrefix;
	private Application app;
	final static String IMAGE_EXTENSION = ".png";
	private final Logger logger = Logger.getLogger(getClass().getName());

	public IconLoader(Application app) {
		super();
		this.screenPrefix = app.getRotaxmame().getRotaxScreenshotPath();
		this.imgPrefix = "images/";
		this.icoPrefix = "icons/";
		this.app = app;
	}
	
	/**
	 * @return icon for given enumeration or {@link EmptyIcon} if there is none defined
	 */
	public Icon tryLoadIcon(Enum<?> e){
		String path = this.app.getIcons().getOptionalString(e);
		if(path!=null){
			return loadIcon(path);
		}else{
			return new EmptyIcon();
		}
	}

	public ImageIcon loadIcon(String path){
		if(path==null){
			return null;
		}
		ImageIcon i =null;

		String iconPath = icoPrefix+path;
		if(iconPath.endsWith(".ico")){

				Image im = loadIcoAsImage(path);
				im = im.getScaledInstance(16, 16, Image.SCALE_FAST);
				i = new ImageIcon(im);

		}else if(iconPath.endsWith(".png")){
			i = new ImageIcon(url(iconPath));
		}
		return i;
	}
	
	private URL url(String path){
		return getClass().getClassLoader().getResource(path);
	}
	
	private Image loadInt(String prefix,String path){
		if(prefix==null) {
			return null;
		}
		String filename = prefix+path;
		try{
			return ImageIO.read(url(filename));
		}catch(Exception ex){
			logger.warning("cannot load image "+filename+" because: "+ex);
		}
		return null;
	}
	
	public Image loadImageFromIcon(Enum<?> e){
		String path = app.getIcons().getOptionalString(e);
		return loadInt(icoPrefix, path);
	}

	public Image loadImage(String path){
		return loadInt(imgPrefix, path);
	}
	
	public Image loadGameScreenshot(int id) {
		String filename = screenPrefix + "" + id + ".jpg";
		try{
			return ImageIO.read(new File(filename));
		}catch(Exception ex){
			logger.warning("cannot load image "+filename+" because: "+ex);
			return null;
		}
	}
	
	//FIXME this ugly code!!!
//	private Image downloadAndLoadImageInt(String path) throws Exception{
//		
//		Application.getLogger(this).info("downloading "+path+" ... ");
//		URL u = new URL("http://rotaxmame.cz/hry/"+path+IMAGE_EXTENSION);
//	    URLConnection uc = u.openConnection();
//	    int contentLength = uc.getContentLength();
//	    InputStream raw = uc.getInputStream();
//	    InputStream in = new BufferedInputStream(raw);
//	    byte[] data = new byte[contentLength];
//	    int bytesRead = 0;
//	    int offset = 0;
//	    while (offset < contentLength) {
//	      bytesRead = in.read(data, offset, data.length - offset);
//	      if (bytesRead == -1)
//	        break;
//	      offset += bytesRead;
//	    }
//	    in.close();
//
//	    if (offset != contentLength) {
//	      Application.getLogger(this).warning("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
//	      return null;
//	    }
//
//	    if(screenshotPrefix!=null){
//	    	String filename = imgPrefix + path+IMAGE_EXTENSION;
//	  		Application.getLogger(this).fine("saving as "+filename+" ... ");
//	  	    FileOutputStream out = new FileOutputStream(filename);
//	  	    out.write(data);
//	  	    out.flush();
//	  	    out.close();
//	  		return loadImage(path);
//	    }else{
//	    	BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
//	    	return img;
//	    }
//	  
//	}
//	public Image downloadAndLoadImage(String path){
//		Image img = null;
//		try{
//			img = downloadAndLoadImageInt(path);
//		}catch(Exception ex){
//			Application.getLogger(this).log(Level.SEVERE, "cannot download image "+path,ex);
//		}
//		return img;
//	}

	public Image loadIcoAsImage(String name){
		if(name==null){
			return null;
		}
		String path = icoPrefix+name;
		if(!name.endsWith(".ico")){
			return loadImage(path);
		}
		List<BufferedImage> bufims = Collections.emptyList();
		try{
			URL url = url(path);
			if(url==null){
				logger.severe("Cannot create url for icon "+path);
				return null;
			}
			bufims=ICODecoder.read(url.openStream());
			if(bufims.size()>0){
				return bufims.get(0);
			}
		}catch(IOException ex){
			logger.log(Level.SEVERE, "Cannot load icon "+path,ex);
		}
		return null;
	}
}
