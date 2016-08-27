package com.sorrer.utils.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import box2dLight.RayHandler;

public class PlaceholderEntity extends Entity{

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
		return new Vector2(0,0);
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(0,0);
	}

}
