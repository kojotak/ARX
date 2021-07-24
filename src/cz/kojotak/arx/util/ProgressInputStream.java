package cz.kojotak.arx.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Counts read bytes from wrapped input stream
 * 
 * @see <a href="http://stackoverflow.com/questions/1339437/inputstream-or-reader-wrapper-for-progress-reporting">stackoverflow</a>
 * @date 13.4.2011
 * @author Kojotak
 */
public class ProgressInputStream extends FilterInputStream {
 
    private volatile long totalNumBytesRead=0;

    public ProgressInputStream(InputStream in) {
        super(in);
    }
    
    public long getTotalNumBytesRead() {
        return totalNumBytesRead;
    }

    @Override
    public int read() throws IOException {
        return (int)updateProgress(super.read());
    }

    @Override
    public int read(byte[] b) throws IOException {
        return (int)updateProgress(super.read(b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return (int)updateProgress(super.read(b, off, len));
    }

    @Override
    public long skip(long n) throws IOException {
        return updateProgress(super.skip(n));
    }

    @Override
    public void mark(int readlimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    private long updateProgress(long numBytesRead) {
        if (numBytesRead > 0) {
            this.totalNumBytesRead += numBytesRead;
        }

        return numBytesRead;
    }
}