/**
 * 
 */
package cz.kojotak.arx.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.common.RunnableWithProgress;

/**
 * Simple http downloader implementation
 * @date 26.9.2010
 * @author Kojotak 
 */
public class Downloader implements RunnableWithProgress {
	
	private final Logger log = LogManager.getLogger(this);
	private String source;
	private AtomicLong readBytes=new AtomicLong(0);
	private AtomicLong totalBytes=new AtomicLong(0);
	private byte[] bytes;
	private static final int BUFFER_SIZE=4096;

	public Downloader(String source) {
		this.source = source;
	}

	@Override
	public long current() {
		return readBytes.get();
	}

	@Override
	public long max() {
		return totalBytes.get();
	}

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
		this.totalBytes.set(con.getContentLengthLong());
		log.info("total bytes: ", this.totalBytes);
		try(BufferedInputStream in = new BufferedInputStream(con.getInputStream())){
			try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
				try(OutputStream out = new BufferedOutputStream(bos)){
					byte[] buf = new byte[BUFFER_SIZE];
					int read = -1;
					int total=0;
					while ((read = in.read(buf)) != -1) {
						readBytes.addAndGet(read);
						out.write(buf, 0, read);
						total+=read;
					}
					log.trace(total+" bytes were read");
				}
				bytes = bos.toByteArray();
			}
		}catch(IOException ioex){
			throw new RuntimeException("cannot read from "+source,ioex);
		}
		Application.getInstance().bytesToImport=totalBytes; //TODO remove ???
	}
	
	@Override
	public String toString() {
		return "Downloader [source=" + source + "]";
	}
	
	public InputStream getDBInputStream() {
		return bytes != null ? new ByteArrayInputStream(bytes) : null;
	}
}
