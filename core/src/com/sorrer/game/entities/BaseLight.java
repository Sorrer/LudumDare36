package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sorrer.utils.Timer;
import com.sorrer.utils.Units;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityID;

public class BaseLight extends Entity{
	
	private float diameter = 0;
	private float diameterAdjustment = 0;
	
	private int brightness = 0;
	private float brightnessAdjustment = 0;
	
	private float fuelMax = 0;
	private float currentStoredFuel = 0;
	
	/** Every Minute This much fuel gets consumed**/
	private float fuelBurnRate = 0;
	
	Sprite burntOut;
	Sprite base;
	
	Timer timer;
	
	boolean isBurntOut = false;
	
	float x,y;
	
	public BaseLight(LightSourceType l, float x, float y){
		
		this.ID = EntityID.light;
		
		timer = new Timer(1000 * 60);
		timer.start();
		
		this.x = x;
		this.y = y;
		
		burntOut = new Sprite();
		base = new Sprite();
		
		//Configure base on light source type
		switch(l){
		case fire_stick:
			this.diameter = Units.metersToPixels(0.5f);
			this.fuelMax = 12;
			this.fuelBurnRate = 6;
			break;
		case camp_fire:
			this.diameter = Units.metersToPixels(1f);
			this.fuelMax = 20;
			this.fuelBurnRate = 10;
			break;
		case pit_fire:
			this.diameter = Units.metersToPixels(2f);
			this.fuelMax = 50;
			this.fuelBurnRate = 4;
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * @param amount  # of logs
	 * @return amount of excess fuel not consumed
	 */
	
	public float addFuel(float amount){
		float space = this.fuelMax - this.currentStoredFuel;
		this.currentStoredFuel += amount;
		if(this.currentStoredFuel > this.fuelMax){
			this.currentStoredFuel = this.fuelMax;
		}
		return (space > amount ? 0 : amount - space);
	}
	
	@Override
	public void update() {
		this.burntOut.setPosition(x, y);
		this.base.setPosition(x, y);
		
		if(timer.isDone()){
			timer.start();
			this.currentStoredFuel -= this.fuelBurnRate;
			if(this.currentStoredFuel < 0){
				this.currentStoredFuel = 0;
				this.isBurntOut = true;
			}else{
				this.isBurntOut = false;
			}
		}
		
		if(this.currentStoredFuel <= this.fuelBurnRate){
			this.diameterAdjustment = -(this.diameter * timer.getProgress());
			this.brightnessAdjustment = -(timer.getProgress());
		}else{
			this.diameterAdjustment = 0;
			this.brightnessAdjustment = 0;
		}
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		
	}

	@Override
	public void dispose() {
		
	}

}
