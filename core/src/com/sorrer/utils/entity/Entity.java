package com.sorrer.utils.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sorrer.utils.component.Component;
import com.sorrer.utils.component.ComponentManager;

public abstract class Entity {
	
	private ComponentManager components = new ComponentManager();
	protected EntityID ID = EntityID.none;
	protected boolean doesUpdate = true;
	protected boolean trash = false;
	
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
}
