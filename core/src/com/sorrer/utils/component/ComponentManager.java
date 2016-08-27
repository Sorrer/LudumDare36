package com.sorrer.utils.component;

import java.util.LinkedList;

import com.sorrer.utils.entity.Entity;

public class ComponentManager {
	
	private LinkedList<Component> components = new LinkedList<Component>();
	private LinkedList<Component> bufferComponents = new LinkedList<Component>();
	private LinkedList<Component> debufferComponents = new LinkedList<Component>();
	private LinkedList<ComponentID> debufferComponentsID = new LinkedList<ComponentID>();
	
	public void update(Entity e){
		for(Component c: components){
			c.update(e);
			if(c.isDone()){
				debufferComponents.add(c);
			}
		}
		
		for(Component b: bufferComponents){
			components.add(b);
		}
		
		if(debufferComponentsID.size() > 0){
			for(Component c: components){
				for(ComponentID dID: debufferComponentsID){
					if(c.getID() == dID){
						debufferComponents.add(c);
						break;
					}
				}
			}
		}
		
		for(Component d: debufferComponents){
			components.remove(d);
		}
		
		this.debufferComponentsID.clear();
		this.debufferComponents.clear();
		this.bufferComponents.clear();
		
	}
	
	public void add(Component c){
		this.bufferComponents.add(c);
	}
	
	public void remove(Component c){
		this.debufferComponents.add(c);
	}
	
	public void removeById(ComponentID cId){
		this.debufferComponentsID.add(cId);
	}

	public void dipose() {
		components.clear();
		bufferComponents.clear();
		debufferComponents.clear();
		debufferComponentsID.clear();
	}
	
	
}
