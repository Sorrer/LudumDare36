package com.sorrer.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sorrer.game.CoreGame;
import com.sorrer.game.entities.BaseLight;
import com.sorrer.game.entities.LightSourceType;
import com.sorrer.utils.entity.EntityManager;

public class GameScreen implements Screen{
	
	CoreGame game;
	OrthographicCamera cam;
	SpriteBatch b;
	ShapeRenderer sr;
	
	World world;
	EntityManager buildings;
	
	public GameScreen(CoreGame g){
		this.game = g;
		
		this.b = new SpriteBatch();
		this.sr = new ShapeRenderer();
		
		this.cam = new OrthographicCamera();
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
		cam.update();
		
		this.buildings = new EntityManager(cam, world, b, sr);
		
		BaseLight bL = new BaseLight(LightSourceType.fire_pit, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		bL.addFuel(8);
		
		this.buildings.add(bL);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(75f / 255f, 180 / 255f, 90 / 255f, 1);
		b.setProjectionMatrix(cam.combined);
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
