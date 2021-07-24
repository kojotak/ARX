/**
 * 
 */
package cz.kojotak.arx.mock;

import java.util.concurrent.atomic.AtomicInteger;

import cz.kojotak.arx.Application.Job;
import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.ui.SplashScreen;

/**
 * Dummy job for testing {@link SplashScreen}
 * @date 26.9.2010
 * @author Kojotak 
 */
public class DummyJob extends Job {

	public DummyJob(final int sleepInMs) {
		super(new RunnableWithProgress(){
			long max = 100;
			AtomicInteger current=new AtomicInteger(0);
			
			@Override
			public long current() {
				return current.get();
			}

			@Override
			public long max() {
				return max;
			}

			@Override
			public void run() {
				try{for (int i = 0; i <= max; i++) {
					current.incrementAndGet();
					Thread.sleep((long)(sleepInMs*Math.random()));//simulated delay
				}}catch(Exception ignore){}
			}
			
		}, 100, "Dummy job");
	}
	

}
