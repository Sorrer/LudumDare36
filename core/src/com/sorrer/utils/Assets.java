package com.sorrer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

public class Assets {
	public static final AssetManager manager = new AssetManager();
	public static final AssetManager freeTypeManager = new AssetManager();
	
	public static final AssetDescriptor<FreeTypeFontGenerator> dialog = new AssetDescriptor<FreeTypeFontGenerator>("", FreeTypeFontGenerator.class);
	
	public static final AssetDescriptor<Texture> fire_pit_full = new AssetDescriptor<Texture>("fire_pit_full.png", Texture.class);
	public static final AssetDescriptor<Texture> fire_pit_empty = new AssetDescriptor<Texture>("fire_pit_empty.png", Texture.class);
	public static final ParticleEffect fire_particle = new ParticleEffect();
	
	public static void load(){
		fire_particle.load(Gdx.files.internal("fire.particle"), Gdx.files.internal(""));
		manager.load(fire_pit_empty);
		manager.load(fire_pit_full);
		freeTypeManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
	}
	
	public static void dipose(){
		
	}
}
