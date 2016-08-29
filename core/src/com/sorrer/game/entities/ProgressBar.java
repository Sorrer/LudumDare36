package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ProgressBar {
	
	float x,y;
	float max=0,cur=0;
	
	float width,height;
	
	Color c = Color.RED;
	
	public ProgressBar(float x, float y, float width, float height, float max , float cur){
		this.x = x;
		this.y = y;
		this.max = max;
		this.cur = cur;
		this.width = width;
		this.height = height;
	}
	
	boolean fliped = false;
	
	public void flip(){
		fliped = fliped ? false : true;
	}
	
	public void setCurrent(float cur){
		this.cur = cur;
	}
	
	public void setMax(float max){
		this.max = max;
	}
	
	public float getProgress(){
		return this.cur / this.max;
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public void draw(SpriteBatch b, ShapeRenderer sr){
		b.end();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(x, y, width, height);
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.set(ShapeType.Filled);
		sr.setColor(this.c);
		if(fliped) sr.rect(x, y, width, (height * getProgress()));
		else sr.rect(x, y,(width * getProgress()), height);
		sr.end();
		b.begin();
	}
}
