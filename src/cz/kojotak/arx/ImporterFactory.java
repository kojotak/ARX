/**
 * 
 */
package cz.kojotak.arx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import org.apache.logging.log4j.Logger;

/**
 * @date 28.3.2010
 * @author Kojotak 
 */
public class ImporterFactory {
	protected Logger log;
	
	/**
	 * number of lines in import file
	 * FIXME refactor me!
	 */
	public AtomicInteger lines=new AtomicInteger(0);

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
			importer = new Importer();
			importer.setReader(reader);
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
		final String URL = Application.RM_DB_URL;
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
			importer = new Importer();
			importer.setReader(br);
		}catch(IOException ioex){
			ioex.printStackTrace();
		}
		return importer;
	}
	
	public Importer createFromGziped(File file){
		log.info("processing " + file + " ...");
		InputStream in=null;
		Importer importer = null;
		try{
			in = new FileInputStream(file);
			GZIPInputStream gzip = new GZIPInputStream(in);
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			importer = new Importer();
			importer.setReader(br);
		}catch(FileNotFoundException ex){
			log.error("there is no such file " + file);
			throw new RuntimeException("there is no such a file "+file,ex);
		}catch(IOException ioex){
			log.error("Cannot import data using "+file);
			throw new RuntimeException("Error during import",ioex);
		}
		return importer;
	}

	public Importer createFromGziped(String string) {
		return createFromGziped(new File(string));
	}

}
