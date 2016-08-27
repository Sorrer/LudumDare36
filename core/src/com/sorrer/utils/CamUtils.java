package com.sorrer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CamUtils {
	public static OrthographicCamera curCam;
	
	public static Vector3 mouseWorldCoords(OrthographicCamera cam){
		return cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
	
	public static Vector3 screenCoordsToWorld(OrthographicCamera cam, Vector2 coords){
		return cam.unproject(new Vector3(coords, 0));
	}
	
	public static Vector3 screenCoordsToWorld(Vector2 coords){
		return curCam.unproject(new Vector3(coords, 0));
	}
	
	public static Rectangle getRectangle(OrthographicCamera cam){
		return new Rectangle(cam.position.x, cam.position.y, cam.viewportWidth, cam.viewportHeight);
	}
	
	public static Rectangle getRectangle(){
		return new Rectangle(curCam.position.x, curCam.position.y, curCam.viewportWidth, curCam.viewportHeight);
	}
}
