package com.sorrer.game.screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.sorrer.game.CoreGame;
import com.sorrer.game.entities.BaseLight;
import com.sorrer.game.entities.LightSourceType;
import com.sorrer.game.entities.Stat;
import com.sorrer.game.entities.Stockpile;
import com.sorrer.game.entities.Worker;
import com.sorrer.utils.Assets;
import com.sorrer.utils.CamUtils;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityDrawer;
import com.sorrer.utils.entity.EntityManager;

import box2dLight.RayHandler;

public class GameScreen implements Screen{
	
	CoreGame game;
	OrthographicCamera cam;
	OrthographicCamera uicam;
	SpriteBatch b;
	ShapeRenderer sr;
	
	World world;
	
	Stat wood;
	EntityManager buildings;
	EntityManager resources;
	EntityManager stockpiles;
	EntityManager workers;
	
	EntityManager ui;
	
	BaseLight bL, bL1;
	
	public GameScreen(CoreGame g){
		this.game = g;
		
		this.world = new World(new Vector2(), false);
		
		this.b = new SpriteBatch();
		this.sr = new ShapeRenderer();
		
		this.cam = new OrthographicCamera();
		this.uicam = new OrthographicCamera();
		uicam.viewportWidth = Gdx.graphics.getWidth();
		uicam.viewportHeight = Gdx.graphics.getHeight();
		uicam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		uicam.update();
		
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		cam.update();
		
		this.buildings = new EntityManager(cam, world, b, sr);
		this.workers = new EntityManager(cam, world, b, sr);
		this.resources = new EntityManager(cam, world, b, sr);
		this.stockpiles = new EntityManager(cam, world, b , sr);
		this.ui = new EntityManager(uicam, world, b, sr);
		
		wood = new Stat(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() * .16f, Assets.manager.get(Assets.icon_wood));
		wood.center();
		
		this.ui.add(wood);
		
		Stockpile sp = new Stockpile(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		sp.addWood(10);
		this.stockpiles.add(sp);
		
		bL = new BaseLight(LightSourceType.fire_pit, Gdx.graphics.getWidth()/2  + 600, Gdx.graphics.getHeight()/2  + 10);
		bL1 = new BaseLight(LightSourceType.camp_fire, 100, 100);
				
		bL1.addFuel(8);
		bL.addFuel(8);
		
		Worker worker = new Worker(sp, 200, 200);
		worker.setAmountWood(5);
		
		this.workers.add(worker);
		
		this.buildings.add(bL);
		this.buildings.add(bL1);
		
		
		RayHandler.useDiffuseLight(true);
	
		CamUtils.curCam = this.cam;
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		update();
		
		if(Gdx.input.justTouched()){
			Vector3 mCoords = CamUtils.mouseWorldCoords(cam);
			BaseLight bL = new BaseLight(LightSourceType.fire_stick, mCoords.x, mCoords.y);
			bL.addFuel(8);
			this.buildings.add(bL);
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			cam.position.x -= 400 * delta;
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			cam.position.x += 400 * delta;
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			cam.position.y -= 400 * delta;
		}else if(Gdx.input.isKeyPressed(Keys.W)){
			cam.position.y += 400 * delta;
		}
		
		cam.update();
		LinkedList<EntityManager> managers = new LinkedList<EntityManager>();

		managers.add(this.stockpiles);
		managers.add(this.workers);
		managers.add(this.buildings);
		managers.add(this.resources);
		
		b.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(60f / 255f, 180 / 255f, 90 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b.begin();
			EntityDrawer.draw(managers, b, sr);
		b.end();
		this.buildings.drawLights();
		b.setProjectionMatrix(uicam.combined);
		
		b.begin();
			this.ui.draw();
		b.end();
	}
	
	public void update(){
		this.buildings.update();
		this.workers.update();
		this.resources.update();
		this.stockpiles.update();
		this.ui.update();
		
		float a = 0;
		for(Entity e : this.stockpiles.getEntities()){
			Stockpile s = (Stockpile)e;
			a += s.woodLeft();
		}
		
		this.wood.setValue(a+ "");
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.update();
		uicam.viewportWidth = Gdx.graphics.getWidth();
		uicam.viewportHeight = Gdx.graphics.getHeight();
		uicam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		uicam.update();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
