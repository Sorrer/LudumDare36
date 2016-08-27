package com.sorrer.game;

import com.badlogic.gdx.Game;
import com.sorrer.game.screens.SplashScreen;

public class CoreGame extends Game{
	
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
