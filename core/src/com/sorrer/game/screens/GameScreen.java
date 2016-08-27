package com.sorrer.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sorrer.game.CoreGame;
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
		
		b = new SpriteBatch();
		sr = new ShapeRenderer();
		buildings = new EntityManager(world, b, sr);
		
		this.cam = new OrthographicCamera();
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.update();
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
			this.buildings.draw(b, sr);
		b.end();
		
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
