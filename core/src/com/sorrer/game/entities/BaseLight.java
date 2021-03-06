package com.sorrer.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.Assets;
import com.sorrer.utils.Config;
import com.sorrer.utils.TimerUpdatable;
import com.sorrer.utils.Units;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityID;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class BaseLight extends Entity{
	
	private float diameter = 0, diameterAdjustment = 0;
	
	private float brightness = 0, brightnessAdjustment = 0;
	
	private float fuelMax = 0, currentStoredFuel = 0;
	
	/** Every Minute This much fuel gets consumed**/
	private float fuelBurnRate = 0;
	
	Sprite burntOut;
	Sprite base;
	
	Color color;
	
	TimerUpdatable timer;
	
	PointLight light;
	
	boolean isBurntOut = false, burningOut = false;
	
	float x,y;
	
	float fireStrength = 1f, delay = 1000 * 20;;
	
	LightSourceType lType;
	
	ProgressBar progress;
	
	ParticleEffect fire;
	public BaseLight(LightSourceType l, float x, float y){
		fire = new ParticleEffect(Assets.fire_particle);
		this.lType = l;
		
		this.ID = EntityID.light;
		
		
		
		this.x = x;
		this.y = y;

		//TEMP DON'T FORGET TO REMOVE!!
		burntOut = new Sprite();
		base = new Sprite();
		base.setSize(100, 100);
		burntOut.setSize(100, 100);
		// ^^^^^^^^
		
		//Configure base on light source type
		switch(l){
		case fire_stick:
			this.diameter = Units.metersToPixels(0.5f);
			this.fuelMax = 10;
			this.fuelBurnRate = 2;
			this.brightness = 0.7f;
			this.fireStrength = 0.1f;
			this.delay = 1000 * 10f;
			this.fuelType = FuelType.wood;
			
			progress = new ProgressBar(this.x - 30, this.y + 5, 10 ,this.getSize().y * .8f, this.fuelMax, this.currentStoredFuel);
			progress.flip();
			
			this.burntOut = new Sprite(Assets.manager.get(Assets.fire_stick_burntout));
			this.base = new Sprite(Assets.manager.get(Assets.fire_stick));
			break;
			
		case camp_fire:
			this.diameter = Units.metersToPixels(1f);
			this.fuelMax = 20;
			this.fuelBurnRate = 10;
			this.brightness = 0.8f;
			this.fireStrength = 0.3f;
			this.delay = 1000 * 5f;
			this.fuelType = FuelType.wood;

			progress = new ProgressBar(this.x + (this.getSize().x - this.getSize().x * .8f)/2, this.y - 10, this.getSize().x * .8f, 10, this.fuelMax, this.currentStoredFuel);
			
			this.burntOut = new Sprite(Assets.manager.get(Assets.camp_fire_burntout));
			this.base = new Sprite(Assets.manager.get(Assets.camp_fire));
			break;
			
		case fire_pit:
			this.diameter = Units.metersToPixels(2f);
			this.fuelMax = 50;
			this.fuelBurnRate = 4;
			this.brightness = 0.8f;
			this.fireStrength = 0.4f;
			this.delay = 1000 * 10f;
			this.fuelType = FuelType.wood;

			progress = new ProgressBar(this.x + (this.getSize().x - this.getSize().x * .8f)/2, this.y - 10, this.getSize().x * .8f, 10, this.fuelMax, this.currentStoredFuel);
			
			this.burntOut = new Sprite(Assets.manager.get(Assets.fire_pit_empty));
			this.base = new Sprite(Assets.manager.get(Assets.fire_pit_full));
			
			break;
			
		default:
			break;
		}
		
		this.fire.scaleEffect(fireStrength);
		timer = new TimerUpdatable((int) this.delay);
		timer.start();
		
		color = new Color(250f / 255f, 245f / 255f, 215f / 255f, this.brightness);
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
	
	public boolean isBurningOut(){
		return this.isBurntOut ? this.isBurntOut : this.burningOut;
	}
	
	
	@Override
	public void update() {
		burningOut = false;
		timer.update();
		this.burntOut.setPosition(x, y);
		this.base.setPosition(x, y);
		
		if(timer.isDone()){
			timer.start();
			this.currentStoredFuel -= this.fuelBurnRate;
		}
		if(this.currentStoredFuel <= 0){
			this.currentStoredFuel = 0;
			this.isBurntOut = true;
		}else{
			this.isBurntOut = false;
		}
		
		if(this.currentStoredFuel <= this.fuelBurnRate && !this.isBurntOut){
			burningOut = true;
			this.light.setActive(true);
			this.diameterAdjustment = -(this.diameter * timer.getProgress());
			this.brightnessAdjustment = -(timer.getProgress());
			
			if(this.diameter + this.diameterAdjustment < 0){
				this.diameterAdjustment = -this.diameter;
			}
			
			if(this.brightness + this.brightnessAdjustment < 0){
				this.brightnessAdjustment = -this.brightness;
			}
		}else if(this.isBurntOut){
			this.light.setActive(false);
		}else{
			this.light.setActive(true);
			this.diameterAdjustment = 0;
			this.brightnessAdjustment = 0;
		}
		
		this.color = new Color(this.color.r, this.color.g, this.color.b, this.brightness + this.brightnessAdjustment);
		this.light.setColor(this.color);
		this.light.setDistance(this.diameter + this.diameterAdjustment);
		this.light.setPosition(getCenterPos());
		this.fire.setPosition(getCenterPos().x, getCenterPos().y);
		this.fire.update(Gdx.graphics.getDeltaTime());
		
		if(this.isBurntOut || this.isBurningOut()){
			progress.setCurrent(0);
		}else{
			progress.setCurrent(this.currentStoredFuel);
		}
		progress.setMax(this.fuelMax);
		
		if(progress.getProgress() < .1f){
			progress.setColor(Color.RED);
		}else if(progress.getProgress() < .25f){
			progress.setColor(Color.ORANGE);
		}else if(progress.getProgress() < .5f){
			progress.setColor(Color.YELLOW);
		}else{
			progress.setColor(Color.GREEN);
		}
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		if(isBurntOut){
			this.burntOut.draw(b);
		}else{
			if(!burningOut){
				this.base.draw(b);
				this.fire.draw(b);
			}else
				this.burntOut.draw(b);
		}
		
		progress.draw(b, sr);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void addLights(RayHandler rayH) {
		light = new PointLight(rayH, Config.AMOUNT_OF_RAYS, this.color, this.diameter - this.diameterAdjustment, getCenterPos().x, getCenterPos().y);
		light.setXray(true);
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
		return (this.isBurntOut ? new Vector2(this.burntOut.getWidth(), this.burntOut.getHeight()) : new Vector2(this.base.getWidth(), this.base.getHeight()));
	}

}
