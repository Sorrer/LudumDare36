package com.sorrer.utils;

public class Units {
	public static int PixelPerMeter = 500;
	
	public static float metersToPixels(float meters){
		return Units.PixelPerMeter * meters;
	}
	
	public static float millisToPixels(float mm){
		return Units.PixelPerMeter * (mm * 1000);
	}
}
