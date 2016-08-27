package com.sorrer.utils.gui;

import java.util.LinkedList;

import com.sorrer.utils.Text;

public class DialogBox {
	
	private int step = -1;
	private LinkedList<String> lines;
	String original = "";
	private int maxLines = 4;
	
	public DialogBox(LinkedList<String> lines){
		this.lines = lines;
		for(String s : this.lines){
			this.original += s; 
		}
	}
	/**
	 * Tests if stepping is still possible.
	 * If it is possible return true and add one to step;
	 * @return Is there a next line?
	 */
	public boolean next(){
		if(step < lines.size()){
			step++;
			return true;
		}
		return false;
	}
	
	/**
	 * Test if stepping is still possible.
	 * @return Is there a next line?
	 */
	public boolean isMore(){
		if(step < lines.size()){
			return true;
		}
		return false;
	}
	
	/**
	 * See if the current step is before the first position;
	 * @return boolean
	 */
	public boolean isBeforeFirst(){
		if(step == -1) return true;
		return false;
	}
	
	/**
	 * Grabs and returns current line step is on.
	 * Returns an empty string if not possible.
	 * @return Current line
	 */
	public String getLine(){
		if(step < lines.size()){
			return lines.get(step);
		}else{
			return "";
		}
	}
	/**
	 * @return Full dialog
	 */
	public LinkedList<String> getLines(){
		return this.lines;
	}
	/**
	 * @return Number of lines
	 */
	public int getSize(){
		return this.lines.size();
	}
	
	public int getMaxLines(){
		return this.maxLines;
	}
	
	/**
	 * Resets step count to before first.
	 */
	public void reset(){
		step = -1;
	}
	
	/**
	 * Resets step count to first;
	 */
	
	public void resetFirst(){
		step = 0;
	}
	
	/**
	 * Sets the dialog, and resets the current step count to before first.
	 * @param lines List of dialog lines
	 */
	public void setLines(LinkedList<String> lines){
		this.lines = lines;
		reset();
	}
	
	/**
	 * Get remaining lines
	 */
	public int getRemaining(){
		return this.lines.size() - step;
	}
	
	/**
	 * Get dialog lines that are related to the current box of dialogs(cur - maxLines through cur).
	 */
	public LinkedList<String> getUILines(){
		LinkedList<String> dialog = new LinkedList<String>();
		this.step -= this.maxLines;
		for(int i = 0 ; i < this.maxLines; i++){
			if(next() && !(step < 0)){
				dialog.add(getLine());
			}
		}
		return dialog;
		
	}
	/**
	 * Adjusts the dialog to fit into the region defined
	 * @param text
	 * @param width
	 * @param height
	 * @param distance between text (x2)
	 */
	public void readjustLines(Text text, float width, float height, float padding){
		String combined = this.original;
		
		
		this.lines.clear();
		int i = 0;
		while(combined.length() > 0){
			combined = combined.trim() + " ";
			if(i > combined.length()){
				this.lines.add(combined);
				combined = "";
				break;
			}
			
			if(combined.length() > i+2){
				if(combined.substring(i,i+2).equals("/n")){
					this.lines.add(combined.substring(0, i));
					combined = combined.substring(i+2);
					i = 0;
				}
			}
			
			if(text.getStringLength(combined.substring(0, i)) >= width){
				combined = combined.trim();
				this.lines.add(combined.substring(0, i - 1));
				combined = combined.substring(i - 1);
				i = 0;
			}else{
				i++;
			}
			
		}
		
		if(this.lines.getLast().trim().equals("")){
			this.lines.removeLast();
		}
		
		this.maxLines = 0;
		for(String l : this.lines){
			int max = 0;
			while((max * text.getStringHeight(l)) + (padding * max) < height){
				max++;
			}
			if(max > this.maxLines){
				this.maxLines = max;
			}
		}
		
	}
	
	/**
	 * Removes lines out of memory
	 */
	public void disposeLines(){
		this.lines = null;
	}
	
}
