package com.sorrer.utils.entity;

import java.io.Serializable;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager implements Serializable{
	private static final long serialVersionUID = -4300150299681062101L;
	private LinkedList<Entity> entities = new LinkedList<Entity>();
	private LinkedList<Entity> entitiesUpdate = new LinkedList<Entity>();
	private LinkedList<Entity> bufferEntities = new LinkedList<Entity>();
	private LinkedList<Entity> debufferEntities = new LinkedList<Entity>();
	
	public void update(){
		for(Entity e: entitiesUpdate){
			if(e.isTrash()){
				e.dispose();
				e.diposeComponents();
				remove(e);
				continue;
			}
			
			if(e.updates()){
				e.update();
			}
		}
		
		updateQueue();
	}
	
	public void draw(SpriteBatch b){
		for(Entity e: entities){
			if(e.isTrash()){
				e.dispose();
				e.diposeComponents();
				remove(e);
				continue;
			}
			e.draw(b);
		}
		
		updateQueue();
	}
	
	private void updateQueue(){
		for(Entity e : bufferEntities){
			if(e.updates()){
				entitiesUpdate.add(e);
			}
			entities.add(e);
		}
		
		for(Entity e : debufferEntities){
			entities.remove(e);
			entitiesUpdate.remove(e);
		}
		
		bufferEntities.clear();
		debufferEntities.clear();
	}
	
	public void add(Entity e){
		bufferEntities.add(e);
	}
	
	public void remove(Entity e){
		debufferEntities.add(e);
	}
	
	public void discard(){
		for(Entity e: entities){
			e.diposeComponents();
			e.dispose();
		}
		entities.clear();
		entitiesUpdate.clear();
		bufferEntities.clear();
		debufferEntities.clear();
	}
	
	@Override
	public String toString(){
		String u = "['EntitiesUpdate' |Size: " + this.entities.size();
		for(Entity e : this.entitiesUpdate){
			u += ", " + e.toString();
		}
		u += "]";
		String r = "['EntitiesRender' |Size: " + this.entities.size();
		for(Entity e : this.entities){
			r += ", " + e.toString();
		}
		r += "]";
		String b = "['Buffering' |Size: " + this.entities.size();
		for(Entity e : this.bufferEntities){
			b += ", " + e.toString();
		}
		b += "]";
		String d = "['Debuffering' |Size: " + this.entities.size();
		for(Entity e : this.debufferEntities){
			d += ", " + e.toString();
		}
		d += "]";
		return u + r + b + d;
	}
}
