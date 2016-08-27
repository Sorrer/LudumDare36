package com.sorrer.utils.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.sorrer.utils.Config;

public class EntitySaver{
	
	public static void save(EntityManager e, String fileName){
		FileHandle file = Gdx.files.local(Config.DATA_LOCATION + fileName);
		Json json = new Json();
		file.writeString(json.toJson(e, EntityManager.class), false);
	}
	
	public static EntityManager load(String fileName){
		FileHandle file = Gdx.files.local(Config.DATA_LOCATION + fileName);
		Json json = new Json();
		return json.fromJson(EntityManager.class, file);
	}
	
	
}
