package com.sorrer.game.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sorrer.game.CoreGame;
import com.sorrer.utils.Assets;
import com.sorrer.utils.PrintLog;
import com.sorrer.utils.Timer;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class SplashScreen implements Screen {

	Texture madeWithLibgdx;
	Timer timer;
	SpriteBatch b;
	long cur;
	int splashCount = -1;
	int splashBegin = 500;
	int splash1 = 1500;
	int splash2 = 1000;
	int splashEnd = 1000;

	World world;
	RayHandler rayHandler;
	PointLight light;
	OrthographicCamera cam;
	CoreGame game;
	public SplashScreen(CoreGame game) {
		this.game = game;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		madeWithLibgdx = new Texture(Gdx.files.internal("MadeWithLibgdx.png"));
		world = new World(new Vector2(0, 0), false);
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(cam);
		light = new PointLight(rayHandler, 5000, new Color(245f / 255f, 245f / 255f, 245f / 255f, 1f),
				madeWithLibgdx.getWidth() * 2, (float) Gdx.graphics.getWidth() / 2f,
				(float) Gdx.graphics.getHeight() / 2f);
		b = new SpriteBatch();

		timer = new Timer(splashBegin);
		
		Assets.load();

	}

	@Override
	public void show() {

	}

	float nextFlicker = 0;
	float lastFlicker = 0;
	boolean switchL = true;	
	boolean oncePressed = false;
	@Override
	public void render(float delta) {
		
		if((Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.isTouched()) && !oncePressed){
			oncePressed = true;
			this.splashCount = 3;
		}
		
		cam.viewportWidth = Gdx.graphics.getWidth();
		cam.viewportHeight = Gdx.graphics.getHeight();
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		
		light.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		rayHandler.updateAndRender();
		b.setProjectionMatrix(cam.combined);
		rayHandler.setCombinedMatrix(cam);
		b.begin();
		b.draw(madeWithLibgdx, Gdx.graphics.getWidth() / 2 - madeWithLibgdx.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - madeWithLibgdx.getHeight() / 2);
		b.end();
		if (splashCount == -1) {
			timer.start();
			splashCount++;
		} else if (splashCount == 0) {
			if (timer.isDone()) {
				timer.setTimer(splash1);
				timer.start();
				splashCount++;
			}
			b.setColor(1, 1, 1, 1);
			Gdx.gl20.glClearColor(0, 0, 0, 1 - timer.getProgress());
		} else if (splashCount == 1) {
			if (timer.isDone()) {
				timer.setTimer(splash2);
				timer.start();
				splashCount++;
				light.setColor(new Color(245f / 255f, 245f / 255f, 245f / 255f, 1f));
				light.setDistance(madeWithLibgdx.getWidth() * 2);

			} else {
				if (System.currentTimeMillis() > cur + 60) {
					cur = System.currentTimeMillis();
					Random random = new Random();
				
					lastFlicker = nextFlicker;
					nextFlicker = random.nextFloat() + 0.1f;
				}
				
				float prog = (float)System.currentTimeMillis()/(float)(cur + 60);
				prog = prog * (nextFlicker - lastFlicker);
				light.setDistance(
						((prog + lastFlicker) * madeWithLibgdx.getWidth() * 2) + madeWithLibgdx.getWidth() * .90f);
				light.setColor(new Color(245f / 255f, 245f / 255f, 245f / 255f, lastFlicker + prog));
			}
		} else if (splashCount == 2) {
			if (timer.isDone()) {
				timer.setTimer(splashEnd);
				timer.start();
				splashCount++;
			}
			Gdx.gl20.glClearColor(0, 0, 0, timer.getProgress());
		} else if (splashCount == 3) {

			float lightSensitivity = (switchL ? 1  - timer.getProgress() : timer.getProgress());
			
			if(timer.isDone()){
				if (Assets.manager.update()) {
					splashCount++;
					return;
				}
				timer.start();
				switchL = (switchL ? false : true);
			}
			
			b.setColor(1, 1, 1, lightSensitivity);
			light.setColor(new Color(245f / 255f, 245f / 255f, 245f / 255f, lightSensitivity * .9f));
		}else if(splashCount == 4){
			PrintLog.printGame("Moving to game screen");
			game.setScreen(new GameScreen(game));
		}
		
	}

	@Override
	public void resize(int width, int height) {
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
