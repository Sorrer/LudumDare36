package com.sorrer.utils;

public class TextReformater implements Runnable{
	
	String stored = "";
	
	public TextReformater(String text){
		this.stored = text;
	}
	
	/**
	 * Loads text that needs to be replaced in a queue
	 */
	public void replace(){
		
	}
	
	public String getResult(){
		return stored;
	}
	
	@Override
	public void run(){
		
	}
}
