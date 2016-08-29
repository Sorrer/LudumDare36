package com.sorrer.utils.entity;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityDrawer {
	private static LinkedList<Entity> temp = new LinkedList<Entity>();
	private static LinkedList<Entity> temp2 = new LinkedList<Entity>();
	public static void draw(LinkedList<EntityManager> em, SpriteBatch b, ShapeRenderer r){
		for(EntityManager en : em){
			temp.addAll(en.getEntitiesRenderable());
			temp2.addAll(en.getEntitiesRenderable());
		}
		
		for(Entity e : temp){
			Entity moveInfront = e;
			for(Entity t : temp){
				if(e.getPos().y < t.getPos().y & e.getRectangle().overlaps(t.getRectangle())){
					moveInfront = t;
				}
			}
			int index = temp2.indexOf(moveInfront);
			if(temp2.indexOf(e) <= temp2.indexOf(moveInfront)){
				index = (index - 1 <= -1 ? 0 : index - 1);
				temp2.remove(e);
				if(temp2.size() < index){
					temp2.add(e);
				}else{
					temp2.add(index, e);
				}
			}
		}
		
		for(Entity e: temp2){
			e.draw(b, r);
		}
		
		temp.clear();
		temp2.clear();
	}
	
}
