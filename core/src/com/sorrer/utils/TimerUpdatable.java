package com.sorrer.utils;


public class TimerUpdatable {
	private int countTo = 0;
	private boolean done = true;
	private long cur = 0;
	private boolean paused = false;
	public TimerUpdatable(int milliseconds){
		this.countTo = milliseconds;
	}
	
	/**
	 * Starts the timer
	 */
	public void start(){
		this.done = false;
		this.cur = System.currentTimeMillis();
	}
	
	boolean wasPaused;
	
	public void update(){
		if(this.paused){
			wasPaused = true;
			return;
		}else if(this.wasPaused){
			wasPaused = false;
			this.cur = System.currentTimeMillis() - getProgressTime();
		}
		
		if(this.cur + this.countTo < System.currentTimeMillis()){
			this.done = true;
			return;
		}
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
}
