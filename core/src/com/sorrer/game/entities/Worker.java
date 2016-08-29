package com.sorrer.game.entities;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.Assets;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityID;
import com.sorrer.utils.entity.PlaceholderEntity;

import box2dLight.RayHandler;

public class Worker extends Entity{
	
	float x,y;
	float pps = 300;
	Sprite worker;
	
	float maxWood = 25, currentWood = 0;
	
	boolean moving = false;
	float moveToX, moveToY;
	
	boolean restocking = false;
	
	boolean refuel = false;
	Entity refuelMe;
	Stockpile stock;

	LinkedList<Entity> refuelQueue = new LinkedList<Entity>();
	LinkedList<Stockpile> restockQueue = new LinkedList<Stockpile>();
	
	public Worker(Stockpile stock, float x, float y){
		this.x = x;
		this.y = y;
		
		this.stock = stock;
		
		this.worker = new Sprite(Assets.manager.get(Assets.worker));
	}
	
	public void setAmountWood(float x){
		this.currentWood = x;
	}
	
	public void moveTo(Vector2 pos){
		moving = true;
		moveToX = pos.x;
		moveToY = pos.y;
	}
	
	public void moveTo(float x, float y){
		moving = true;
		moveToX = x;
		moveToY = y;
	}
	
	public boolean isMoving(){
		return this.moving;
	}
	
	public void restock(Stockpile e){
		if(e.getID() != EntityID.stock){
			return;
		}
		
		if(this.isMoving()){
			boolean safe = true;
			for(Entity i: this.restockQueue){
				if(i == e) safe = false;
			}
			if(safe){
				this.restockQueue.add(e);
			}
			return;
		}

		restocking = false;
		if(!this.getRectangle().overlaps(e.getRectangle())){
			restocking = true;
			this.stock = e;
			moveTo(e.getPos());
		}else{
			if(this.currentWood < this.maxWood && e.hasWood()){
				this.currentWood += e.takeWood(this.maxWood - this.currentWood);
			}
		}
	}
	
	public boolean outOfWood(){
		return (this.currentWood == 0 ? true : false);
	}
	
	public void refuel(Entity e){
		if(e.getID() != EntityID.light){
			return;
		}
		
		if(this.isMoving() || this.outOfWood()){
			boolean safe = true;
			for(Entity i: this.refuelQueue){
				if(i == e)safe = false;
			}
			if(safe){
				this.refuelQueue.add(e);
			}
			if(this.outOfWood()){
				this.moving = false;
				this.restock(stock);
			}
			return;
		}
		
		BaseLight bL = (BaseLight)e;
		refuel = false;
		if(!this.getRectangle().overlaps(e.getRectangle())){
			refuel = true;
			this.refuelMe = e;
			moveTo(e.getPos().x, e.getPos().y);
		}else{
			switch(e.getFuelType()){
			case wood:
				float leftOver = bL.addFuel(this.currentWood);
				this.currentWood = leftOver;
				break;
				
			default:
				break;
			}
		}
	}
	
	@Override
	public void update() {
		float cX = 0, cY = 0;
		if(this.moving){
			Entity e = new PlaceholderEntity();
			if(this.restocking){
				e = this.stock;
			}else if(this.refuel){
				e = this.refuelMe;
			}
			
			if(this.getRectangle().contains(this.moveToX, this.moveToY) || e.getRectangle().overlaps(this.getRectangle())){
				this.moving = false;
				if(this.restocking){
					restock(this.stock);
					if(this.restockQueue.size() > 0){
						this.restock(this.restockQueue.getFirst());
						this.restockQueue.removeFirst();
					}
				}else if(this.refuel){
					refuel(this.refuelMe);
					if(this.refuelQueue.size() > 0){
						this.refuel(this.refuelQueue.getFirst());
						this.refuelQueue.removeFirst();
					}
				}
			}else{
				if(!this.getRectangle().contains(this.moveToX, this.y)){
					if(this.x > this.moveToX){
						cX -= this.pps * Gdx.graphics.getDeltaTime();
					}else{
						cX += this.pps * Gdx.graphics.getDeltaTime();
					}
				}
				
				if(!this.getRectangle().contains(this.x, this.moveToY)){
					if(this.y > this.moveToY){
						cY -= this.pps * Gdx.graphics.getDeltaTime();
					}else{
						cY += this.pps * Gdx.graphics.getDeltaTime();
					}
				}
			}
		}
		
		x += cX;
		y += cY;
		this.worker.setPosition(x, y);
		
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		this.worker.draw(b);
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
		return new Vector2(worker.getWidth(), worker.getHeight());
	}

}
