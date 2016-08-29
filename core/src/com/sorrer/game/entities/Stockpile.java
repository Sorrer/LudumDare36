package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.Assets;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityID;

import box2dLight.RayHandler;

public class Stockpile extends Entity{
	
	Sprite s;
	float x,y;
	
	float wood = 0;

	public Stockpile(float x, float y){
		this.ID = EntityID.stock;
		this.x = x;
		this.y = y;
		s = new Sprite(Assets.manager.get(Assets.stockpile));
		s.setPosition(x, y);
	}
	
	public boolean hasWood(){
		if(wood <= 0){
			wood = 0;
			return false;
		}
		return true;
	}
	
	public void addWood(float amount){
		this.wood += amount;
	}
	
	/**Returns amount that was actually given**/
	public float takeWood(float amount){
		float returnA;
		if(this.wood < amount){
			returnA = this.wood;
			this.wood = 0;
		}else{
			this.wood -= amount;
			returnA = amount;
		}
		return returnA;
	}
	
	@Override
	public void update() {
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		s.draw(b);
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
		return new Vector2(x, y);
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(s.getWidth(), s.getHeight());
	}

	public float woodLeft() {
		return this.wood;
	}

}
