package com.sorrer.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
import com.sorrer.utils.CamUtils;
import com.sorrer.utils.entity.EntityManager;

import box2dLight.RayHandler;

public class GameScreen implements Screen{
	
	CoreGame game;
	OrthographicCamera cam;
	SpriteBatch b;
	ShapeRenderer sr;
	
	World world;
	EntityManager buildings;
	
	public GameScreen(CoreGame g){
		this.game = g;
		
		this.world = new World(new Vector2(), false);
		
		this.b = new SpriteBatch();
		this.sr = new ShapeRenderer();
		
		this.cam = new OrthographicCamera();
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		cam.update();
		
		this.buildings = new EntityManager(cam, world, b, sr);
		
		BaseLight bL = new BaseLight(LightSourceType.fire_pit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		BaseLight bL1 = new BaseLight(LightSourceType.camp_fire, 100, 100);
				
		bL1.addFuel(8);
		bL.addFuel(8);

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
			cam.position.x -= 20;
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			cam.position.x += 20;
		}
		cam.update();
		
		b.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(60f / 255f, 180 / 255f, 90 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b.begin();
			this.buildings.draw();
		b.end();
		
		this.buildings.drawLights();
		
	}
	
	public void update(){
		this.buildings.update();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.update();
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
