package com.sorrer.utils.gui;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.PrintLog;
import com.sorrer.utils.Text;
import com.sorrer.utils.Timer;

public class DialogBoxRenderer {
	
	private Text text;
	private Vector2 size, position, spacing;
	private Sprite background;
	private Color colorBackground;
	
	private Timer timer;
	
	private String curLine = "";
	private char cursor = '_';
	private int curLineProgress = 0;
	private LinkedList<String> curDialog;
	private DialogBox dialog = new DialogBox(new LinkedList<String>());
	
	private float paddingVert = 0;
	
	boolean isColor;
	/**
	 * @param speed Milliseconds it takes per character
	 */
	
	public DialogBoxRenderer(int speed, Color background, FreeTypeFontGenerator gen, FreeTypeFontParameter params){
		isColor = true;
		colorBackground = background;
		text = new Text(gen, params);
		curDialog = new LinkedList<String>();
		size = new Vector2(1, 1);
		position = new Vector2(1, 1);
		spacing = new Vector2();
		
		timer = new Timer(speed);
	}
	
	/**
	 * @param speed Milliseconds it takes per character
	 */
	public DialogBoxRenderer(int speed, Texture background, FreeTypeFontGenerator gen, FreeTypeFontParameter params){
		isColor = false;
		this.background = new Sprite(background);
		text = new Text(gen, params);
		curDialog = new LinkedList<String>();
		size = new Vector2(1, 1);
		position = new Vector2(1, 1);
		spacing = new Vector2();
		
		timer = new Timer(speed);
		
	}
	
	/**
	 * Size of dialog box
	 * @param width Size of dialog box
	 * @param height Size of dialog box
	 */
	public void setSize(float width, float height){
		size.set(width, height);
		if(!this.isColor){
			this.background.setSize(width, height);
		}
	}
	
	/**
	 * Set text params
	 */
	public void setTextParameters(FreeTypeFontParameter params){
		this.text.setParams(params);
	}
	
	/**
	 * Sets padding
	 */
	
	public void setPaddingVertical(float pad){
		this.paddingVert = pad;
	}
	
	/**
	 * Position of dialog box
	 * @param x Coord x
	 * @param y Coord y
	 */
	public void setPosition(float x, float y){
		position.set(x, y);
	}
	
	/**
	 * @param x Space between the lines
	 * @param y Space between walls
	 */
	public void setSpacing(float x, float y){
		this.spacing.set(x, y);
	}
	
	/**
	 * The Character to display for the next letter
	 * @c Character Default: '_'
	 */
	public void setCursor(char c){
		this.cursor = c;
	}
	
	/**
	 * Set dialog box
	 */
	public void setDialog(DialogBox dialog){
		this.dialog = dialog;
		reset();
	}
	
	/**
	 * Set speed (milliseconds) between each character
	 */
	public void setSpeed(int milliseconds){
		this.timer.setTimer(milliseconds);
	}
	
	/**
	 * Readjust dialogbox lines
	 */
	public void readjustDialog(){
		this.dialog.readjustLines(text, this.size.x - this.spacing.y, this.size.y - this.paddingVert, this.spacing.y);
	}
	
	/**
	 * Tells you if dialogbox has no more lines to read
	 */
	public boolean isDone(){
		if(!dialog.next()) return true;
		return false;
	}
	
	/**
	 * Resets dialog counters
	 */
	public void reset(){
		dialog.reset();
		this.curDialog.clear();
		this.curLineProgress = 0;
		this.curLine = "";
	}
	
	/**
	 * Advances to next line
	 */
	public void advance(){
		if(!dialog.isMore()){
			return;
		}
		if(this.dialog.next()){
			this.curDialog = this.dialog.getUILines();
			this.curLineProgress = 0;
		}
	}
	
	/**
	 * Updates the dialog
	 */
	public void update(){
		String currentLine = "";
		if(this.curDialog.size() != 0){
			currentLine = this.curDialog.getLast();
		}else{
			advance();
		}
		
		if(!this.dialog.isMore() || (this.dialog.getRemaining() < 2 && this.curLineProgress >= currentLine.length())){
			advance();
			this.curLine = "";
			return;
		}
		
		if(this.curDialog.size() + 1> this.dialog.getMaxLines()){
			this.curDialog.removeFirst();
		}
		
		
		if(timer.isDone()){
			if(!(this.curLineProgress > currentLine.length())){
				if(this.curDialog.getLast().length() > this.curLineProgress){
					this.curLine = currentLine.substring(0, this.curLineProgress) + this.cursor;
					curLineProgress++;
					if(!(this.curDialog.getLast().length() > this.curLineProgress)){
						this.curDialog.add("");
						this.curLine = "";
						
						if(this.curDialog.size() > this.dialog.getMaxLines()){
							this.curDialog.removeFirst();
						}
						
						this.curLineProgress = currentLine.length() + 1;
					}
				}
			}else{
				if(!this.dialog.isMore()){
					this.curLine = "";
					return;
				}
				
				if(this.curLine.equals("" + this.cursor)){
					this.curLine = "";
				}else{
					this.curLine = "" + this.cursor;
				}
			}
			timer.start();
		}

	}
	
	/**
	 * Draws dialog box, if texture background is active, then it will be used
	 */
	public void draw(SpriteBatch b, ShapeRenderer sr){
		if(isColor){
			b.end();
			if(!sr.isDrawing()) sr.begin(ShapeType.Filled);
			sr.setColor(this.colorBackground);
			sr.rect(this.position.x, this.position.y, this.size.x, this.size.y);
			sr.end();
			b.begin();
		}else{
			this.background.setPosition(this.position.x, this.position.y);
			this.background.draw(b);
		}
		
		int line = 0;
		for(int i = 0; i < this.curDialog.size() - 1; i++){
			String l = this.curDialog.get(i);
			text.draw(l, b, this.position.x + this.spacing.x/2, (((this.position.y + this.size.y)) - (text.getStringHeight(l)* line)- paddingVert/2 - this.spacing.y/2) - this.spacing.y * line);
			line++;
		}

		text.draw(this.curLine, b, this.position.x + this.spacing.x/2, (((this.position.y + this.size.y)) - (text.getStringHeight(this.curLine)* line) - paddingVert/2 - this.spacing.y/2) - this.spacing.y * line);
		
	}
	
	/**
	 * Dispose
	 */
	public void dispose(){
		this.dialog.disposeLines();
		this.text.dispose();
		reset();
	}
}
