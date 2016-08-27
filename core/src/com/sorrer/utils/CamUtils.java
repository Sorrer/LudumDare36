package com.sorrer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CamUtils {
	public static Vector3 mouseWorldCoords(OrthographicCamera cam){
		return cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
}