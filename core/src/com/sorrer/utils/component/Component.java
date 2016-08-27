package com.sorrer.utils.component;

import com.sorrer.utils.entity.Entity;

public abstract class Component {
	
	public Component(){}
	
	/**
	 * If the component is done, trash
	 */
	private boolean done = false;
	
	/**
	 * Default ID is 'none'
	 */
	private ComponentID ID = ComponentID.none;
	
	public boolean isDone(){
		return done;
	}
	
	public ComponentID getID(){
		return ID;
	}
	
	public abstract void update(Entity e);
}
