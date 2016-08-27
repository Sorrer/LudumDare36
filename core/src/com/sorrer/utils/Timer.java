package com.sorrer.utils;

public class Timer {
	private int countTo = 0;
	private boolean done = true;
	private long cur = 0;
	private Thread worker;
	private boolean paused = false;
	public Timer(int milliseconds){
		this.countTo = milliseconds;
	}
	
	/**
	 * Starts the timer
	 */
	public void start(){
//		PrintLog.printSys("Starting timer for " + countTo + " milliseconds");
		done = false;
		this.paused = false;
		worker = new Thread(new Worker());
		worker.start();
	}
	
	/**
	 * Is the timer done?
	 * @return boolean
	 */
	public boolean isDone(){
		return done;
	}
	
	/**
	 * How much elapsed time.
	 * @return Milliseconds
	 */
	public int getProgressTime(){
		if(this.isDone()){
			return countTo;
		}
		return (int) (System.currentTimeMillis() - cur);
	}
	
	/**
	 * How far it elapsed. (0f - 1f)
	 * @return percentage
	 */
	public float getProgress(){
		if(this.isDone()){
			return 1f;
		}
		return ((float) getProgressTime()) / ((float) countTo);
	}
	
	/**
	 * Sets the length of time the timer should count to
	 */
	public void setTimer(int milliseconds){
		this.countTo = milliseconds;
	}
	
	/**
	 *Pauses the current timer
	 *WARNING: Might prolong the timer
	 */
	public void pause(){
		if(!this.isDone()) this.paused = true;
	}
	
	/**
	 * Resumes the current timer
	 */
	public void resume(){
		this.paused = false;
	}
	
	private class Worker implements Runnable{
		public void run(){
			boolean wasPaused = false;
			int pausedProgress = 0;
			cur = System.currentTimeMillis();
			while(System.currentTimeMillis() < cur+countTo){
				while(paused){
					if(!wasPaused){
						wasPaused = true;
						pausedProgress = getProgressTime();
					}
				}
				if(wasPaused){
					cur = System.currentTimeMillis() - pausedProgress;
					wasPaused = false;
				}
			}
			done = true;
		}
	}
}
