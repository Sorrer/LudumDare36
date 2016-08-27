package com.sorrer.utils.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.component.Component;
import com.sorrer.utils.component.ComponentManager;

import box2dLight.RayHandler;

public abstract class Entity {
	
	private ComponentManager components = new ComponentManager();
	protected EntityID ID = EntityID.none;
	protected boolean doesUpdate = true;
	protected boolean trash = false;
	protected boolean addedLights = false;
	
	public Entity(){}
	
	public void addComponent(Component c){
		this.components.add(c);
	}
	
	public boolean isTrash(){
		return trash;
	}
	
	/**
	 * @return Does this update
	 */
	public boolean updates(){
		return doesUpdate;
	}
	
	public void updateComponents(){
		components.update(this);
	}
	
	public void diposeComponents(){
		components.dipose();
	}
	
	public abstract void update();
	public abstract void draw(SpriteBatch b, ShapeRenderer sr);
	public abstract void dispose();
	public abstract void addLights(RayHandler rayH);
	public abstract Vector2 getPos();
	public abstract Vector2 getSize();

	public Rectangle getRectangle() {
		return new Rectangle(this.getPos().x, this.getPos().y, this.getSize().x, this.getSize().y);
	}
}
