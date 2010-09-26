/**
 * 
 */
package cz.kojotak.arx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import lombok.ToString;

import org.apache.log4j.Logger;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.ImporterFactory;
import cz.kojotak.arx.common.RunnableWithProgress;

/**
 * @date 26.9.2010
 * @author Kojotak 
 */
@ToString(of={"file"})
public class LineCounter implements RunnableWithProgress {
	
	private File file;
	private Logger log;
	private ImporterFactory ifa;

	public LineCounter(File file,Application app) {
		super();
		this.file = file;
		this.log = app.getLogger(this);
		this.ifa = app.getImporterFactory();
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.common.RunnableWithProgress#current()
	 */
	@Override
	public int current() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.common.RunnableWithProgress#max()
	 */
	@Override
	public int max() {
		return RunnableWithProgress.UNKNOWN;
	}

	private boolean isZipped(){
		if(file==null){
			return false;
		}
		String name = file.getName();
		int lastDot = name.lastIndexOf('.');
		if(lastDot<0){
			return false;
		}
		String ext = name.substring(lastDot);
		if(".zip".equalsIgnoreCase(ext)||
			".gz".equalsIgnoreCase(ext)){
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(file==null || !file.exists() || file.isDirectory()){
			return;
		}
		InputStreamReader isr = null;
		InputStream is=null;
		try {
			is = new FileInputStream(file);
			if(isZipped()){
				is =  new GZIPInputStream(is);
			}
			isr=new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			int lines = 0;
			while(reader.readLine()!=null){
				lines++;
			}
			ifa.setLines(lines);
		} catch (IOException ex) {
			log.error("cannot count lines in "+file,ex);
		} finally {
			try{
				if(isr!=null){
					isr.close();
				}
				if(is!=null){
					is.close();
				}
			}catch(IOException ioex){
				log.warn("cannot release streams from "+file,ioex);
			}
		}
	}

}
