package com.androidclass.todoapp;

import java.util.Random;

import org.json.JSONObject;


public class CommonLib {

	public static String dailyHoroscope(){
		JSONObject jObj = new JSONObject();
		return "Today you will rock Mr. Raj !!";

	}
	/*
	 * Gender??
	 * 
	 * (nice temp & clear sky)
	 *  Happy: Pink, light blue, green, maybe purple
		  (cloudy) Bored: Light colors, black or gray, white
		
		(warm & clear) Hyper/ sporty: Team colors, orange, red, blue
		(extremes) Crazy: Yellow, bright orange, maybe neon
	
	 *
	 */
	public static String colorToWear(int temperature, boolean isCloudy){
		
		String dressColor = "blue";
		
		if(temperature>60 && temperature<75){
			if(!isCloudy){
				String[] happyColors = {"pink", "light blue", "green", "purple"};
				int idx = new Random().nextInt(happyColors.length);
				dressColor = (happyColors[idx]);			
			}
			else{
				String[] boredColors = {"light color", "black", "grey", "white"};
				int idx = new Random().nextInt(boredColors.length);
				dressColor = (boredColors[idx]);
			}
		}
		
		return dressColor;
	}
}
