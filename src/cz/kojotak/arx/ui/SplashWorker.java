/**
 * 
 */
package cz.kojotak.arx.ui;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import cz.kojotak.arx.Application;
import cz.kojotak.arx.Application.Job;
import cz.kojotak.arx.common.Holder;
import cz.kojotak.arx.common.RunnableWithProgress;
import cz.kojotak.arx.ui.SplashScreen.Progress;

/**
 * @date 26.9.2010
 * @author Kojotak
 */
public class SplashWorker extends SwingWorker<Void, Progress> {

	private SplashScreen splash;
	private List<Job> jobs;
	private int totalWeight = 0;
	private final ScheduledExecutorService scheduler;
	private final Logger log;

	public SplashWorker(SplashScreen splash, List<Job> jobs) {
		super();
		this.splash = splash;
		this.jobs = jobs;
		for (Job job : jobs) {
			totalWeight += job.weight;
		}
		scheduler = Executors.newScheduledThreadPool(1);
		log = Application.getInstance().getLogger(this);
	}
	
	

	@Override
	protected Void doInBackground() throws Exception {
		if(jobs==null && jobs.size()==0){
			return null;
		}
		final AtomicInteger doneSoFar=new AtomicInteger(0);
		final Holder<Job> currentJob = new Holder<Job>();
		final Runnable updater = new Runnable() {//updates splash screen - propagates progress
			public void run() {
				Job job = currentJob.getHolded();
				if(job==null){
					return;
				}
				RunnableWithProgress runnable = job.runnable;
				int jobDone = (int)(100.0*runnable.current()/runnable.max());
				log.debug("updating progress for "+job+", done "+jobDone);
				double done = (job.weight/totalWeight) * runnable.current()/runnable.max();
				int soFar;
				if(RunnableWithProgress.UNKNOWN == runnable.max()){
					soFar=-1;
				}else{
					soFar = doneSoFar.get()+(int)done;
				}
				Progress progress = new Progress(soFar,job.description+(soFar!=RunnableWithProgress.UNKNOWN?" "+jobDone+" %" :""));
				SplashWorker.this.publish(progress);
			}
		};
		final ScheduledFuture<?> updaterHandle = scheduler.scheduleAtFixedRate(
				updater, 0, 200, TimeUnit.MILLISECONDS);

		for (Job job : jobs) {
			System.err.println("working on "+job);
			RunnableWithProgress runnable = job.runnable;
			currentJob.setHolded(job);
			runnable.run();
			int done = job.weight/totalWeight;
			doneSoFar.addAndGet(done);
		}
		updaterHandle.cancel(true);
		return null;
	}
	
	@Override
	protected void process(List<Progress> chunks) {
		Progress progress = chunks.get(chunks.size() - 1);
		splash.setProgress(progress);
	}

}
