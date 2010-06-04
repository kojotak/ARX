/**
 * 
 */
package cz.kojotak.arx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class ImporterFactory {
	protected Logger log;

	public ImporterFactory() {
		super();
		this.log = Application.getInstance().getLogger(this);
	}
	
	public Importer createFromPlain(String fileName) {
		log.info("processing " + fileName + " ...");
		InputStream in = null;
		Importer importer = null;
		try {
			in = new FileInputStream(fileName);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			importer = new Importer(reader);
		} catch (FileNotFoundException ex) {
			log.error("there is no such file " + fileName);
			throw new RuntimeException("there is no such a file "+fileName,ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return importer;
	}
	
	public Importer createFromWeb(){
		final String URL="http://rotaxmame.cz/php/download3.php?co=kompletni_databaze";
		log.debug("opening "+URL+" ... ");
		URL url = null;
		try{
			url = new URL(URL);
		}catch(MalformedURLException mex){
			throw new RuntimeException("bad url "+URL,mex);			
		}
		URLConnection con = null;
		try{
			con = url.openConnection();
		}catch(IOException ex){
			throw new RuntimeException("cannot open "+URL,ex);		
		}
		InputStream stream = null;
		try{
			stream = con.getInputStream();
		}catch(IOException ex){
			throw new RuntimeException("cannot read "+URL,ex);
		}
		return createFromStream(stream);		
	}
	
	public Importer createFromStream(InputStream stream){
		Importer importer = null;
		try{
			GZIPInputStream gzip = new GZIPInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			importer = new Importer(br);
		}catch(IOException ioex){
			ioex.printStackTrace();
		}
		return importer;
	}
	
	public Importer createFromGziped(String filename){
		log.info("processing " + filename + " ...");
		InputStream in=null;
		Importer importer = null;
		try{
			in = new FileInputStream(filename);
			GZIPInputStream gzip = new GZIPInputStream(in);
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			importer = new Importer(br);
		}catch(FileNotFoundException ex){
			log.error("there is no such file " + filename);
			throw new RuntimeException("there is no such a file "+filename,ex);
		}catch(IOException ioex){
			log.error("Cannot import data using "+filename);
			throw new RuntimeException("Error during import",ioex);
		}
		return importer;
	}

}
