package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sorrer.utils.Assets;
import com.sorrer.utils.CamUtils;
import com.sorrer.utils.Text;
import com.sorrer.utils.entity.Entity;

import box2dLight.RayHandler;

public class Stat extends Entity{
	
	float x,y;
	Sprite icon;
	Text t;
	
	String text = "";
	String value = "";
	
	public Stat(float x, float y, Texture ico){
		this.x = x;
		this.y = y;
		this.icon = new Sprite(ico);
		FreeTypeFontParameter params = new FreeTypeFontParameter();;
		params.size = 32;
		
		this.t = new Text(Assets.manager.get(Assets.dialog), params);
		this.t.setColor(Color.WHITE);
	}
	
	@Override
	public void update() {
		this.icon.setPosition(x , y);
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		this.icon.draw(b);
		this.t.draw(this.text + value, b, x + this.icon.getWidth(), y + this.icon.getHeight());
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void addLights(RayHandler rayH) {
		
	}
	
	@Override
	public void setPos(float x, float y){
		this.x = x; this.y = y;
	}
	
	@Override
	public Vector2 getPos() {
		return new Vector2(x,y);
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(icon.getWidth() + this.t.getStringLength(this.text + this.value), icon.getHeight() + t.getStringHeight(text + value));
	}

	public void setValue(String string) {
		this.value = string;
	}

}
