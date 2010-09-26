/**
 * 
 */
package cz.kojotak.arx.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.ToString;

import org.apache.log4j.Logger;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.common.RunnableWithProgress;

/**
 * Simple http downloader implementation
 * @date 26.9.2010
 * @author Kojotak 
 */
@ToString(exclude={"log","readBytes","totalBytes"})
public class Downloader implements RunnableWithProgress {
	
	private Logger log;
	private File target;
	private String source;
	private AtomicInteger readBytes=new AtomicInteger(0);
	private AtomicInteger totalBytes=new AtomicInteger(0);
	private static final int BUFFER_SIZE=4096;

	public Downloader(Application app,String source,File target) {
		super();
		this.log = app.getLogger(this);
		this.target = target;
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.common.RunnableWithProgress#current()
	 */
	@Override
	public int current() {
		return readBytes.get();
	}

	/* (non-Javadoc)
	 * @see cz.kojotak.arx.common.RunnableWithProgress#max()
	 */
	@Override
	public int max() {
		return totalBytes.get();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		URL url = null;
		try{
			url = new URL(source);
		}catch(MalformedURLException mex){
			throw new RuntimeException("bad url "+source,mex);			
		}
		URLConnection con = null;
		try{
			con = url.openConnection();
		}catch(IOException ex){
			throw new RuntimeException("cannot open "+source,ex);		
		}
		this.totalBytes.set(con.getContentLength());
		BufferedInputStream in = null;
		OutputStream out = null;
		try{
			in = new BufferedInputStream(con.getInputStream());
			FileOutputStream fos = new FileOutputStream(target);
			out = new BufferedOutputStream(fos);
			byte[] buf = new byte[BUFFER_SIZE];
			int read = -1;
			while ((read = in.read(buf)) != -1) {
				readBytes.addAndGet(read);
				out.write(buf, 0, read);
			}
		}catch(IOException ioex){
			log.error("cannot read from "+source,ioex);
		}finally{
			try{
				if(in!=null){
					in.close();
				}
				if(out!=null){
					out.close();
				}
			}catch(IOException ex){
				log.warn("problem with closing streams from "+source, ex);
			}
		}

	}

}
