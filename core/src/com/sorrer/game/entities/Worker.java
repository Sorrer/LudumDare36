package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.entity.Entity;

import box2dLight.RayHandler;

public class Worker extends Entity{
	
	float x,y;
	Sprite worker;
	
	public Worker(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void addLights(RayHandler rayH) {
		
	}

	@Override
	public Vector2 getPos() {
		return null;
	}

	@Override
	public Vector2 getSize() {
		return null;
	}

}
