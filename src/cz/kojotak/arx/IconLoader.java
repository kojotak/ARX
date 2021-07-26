/**
 *
 */
package cz.kojotak.arx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sf.image4j.codec.ico.ICODecoder;
import cz.kojotak.arx.ui.icon.EmptyIcon;

/**
 * @date 21.3.2010
 * @author Kojotak
 */
public class IconLoader {

	private final String icoPrefix;
	private final String imgPrefix;
	private final String screenshotPrefix=null;
	private Application app;
	final static String IMAGE_EXTENSION = ".png";

	public IconLoader(String icoPrefix,String imgPrefix,Application app) {
		super();
		this.icoPrefix = icoPrefix;
		this.imgPrefix = imgPrefix;
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
		Image img=null;		
		String filename = prefix+path;
		try{
			img = ImageIO.read(url(filename));
		}catch(Exception ex){
			Application.getInstance().getLogger(this).warn("cannot load image "+filename+" because: "+ex);
		}
		return img;
	}
	
	public Image loadImageFromIcon(Enum<?> e){
		String path = app.getIcons().getOptionalString(e);
		return loadInt(icoPrefix, path);
	}

	public Image loadImage(String path){
		return loadInt(imgPrefix, path);
	}
	
	//FIXME this ugly code!!!
	private Image downloadAndLoadImageInt(String path) throws Exception{
		
		app.getLogger(this).info("downloading "+path+" ... ");
		URL u = new URL("http://rotaxmame.cz/hry/"+path+IMAGE_EXTENSION);
	    URLConnection uc = u.openConnection();
	    int contentLength = uc.getContentLength();
	    InputStream raw = uc.getInputStream();
	    InputStream in = new BufferedInputStream(raw);
	    byte[] data = new byte[contentLength];
	    int bytesRead = 0;
	    int offset = 0;
	    while (offset < contentLength) {
	      bytesRead = in.read(data, offset, data.length - offset);
	      if (bytesRead == -1)
	        break;
	      offset += bytesRead;
	    }
	    in.close();

	    if (offset != contentLength) {
	      app.getLogger(this).warn("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
	      return null;
	    }

	    if(screenshotPrefix!=null){
	    	String filename = imgPrefix + path+IMAGE_EXTENSION;
	  		app.getLogger(this).debug("saving as "+filename+" ... ");
	  	    FileOutputStream out = new FileOutputStream(filename);
	  	    out.write(data);
	  	    out.flush();
	  	    out.close();
	  		return loadImage(path);
	    }else{
	    	BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
	    	return img;
	    }
	  
	}
	public Image downloadAndLoadImage(String path){
		Image img = null;
		try{
			img = downloadAndLoadImageInt(path);
		}catch(Exception ex){
			app.getLogger(this).error("cannot download image "+path,ex);
		}
		return img;
	}

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
				app.getLogger(this).error("Cannot create url for icon "+path);
				return null;
			}
			bufims=ICODecoder.read(url.openStream());
			if(bufims.size()>0){
				return bufims.get(0);
			}
		}catch(IOException ex){
			app.getLogger(this).error("Cannot load icon "+path,ex);
		}
		return null;
	}
}
