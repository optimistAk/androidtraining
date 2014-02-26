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
	
	public static String findZodiacSign (String month, String day) {
        int M = Integer.parseInt(month);
        int D = Integer.parseInt(day);
        if ((M == 12 && D >= 22 && D <= 31) || (M ==  1 && D >= 1 && D <= 19))
            return "Capricorn";
        else if ((M ==  1 && D >= 20 && D <= 31) || (M ==  2 && D >= 1 && D <= 17))
            return "Aquarius";
        else if ((M ==  2 && D >= 18 && D <= 29) || (M ==  3 && D >= 1 && D <= 19))
            return "Pisces";
        else if ((M ==  3 && D >= 20 && D <= 31) || (M ==  4 && D >= 1 && D <= 19))
            return "Aries";
        else if ((M ==  4 && D >= 20 && D <= 30) || (M ==  5 && D >= 1 && D <= 20))
            return "Taurus";
        else if ((M ==  5 && D >= 21 && D <= 31) || (M ==  6 && D >= 1 && D <= 20))
            return "Gemini";
        else if ((M ==  6 && D >= 21 && D <= 30) || (M ==  7 && D >= 1 && D <= 22))
            return "Cancer";
        else if ((M ==  7 && D >= 23 && D <= 31) || (M ==  8 && D >= 1 && D <= 22))
            return "Leo";
        else if ((M ==  8 && D >= 23 && D <= 31) || (M ==  9 && D >= 1 && D <= 22))
            return "Virgo";
        else if ((M ==  9 && D >= 23 && D <= 30) || (M == 10 && D >= 1 && D <= 22))
            return "Libra";
        else if ((M == 10 && D >= 23 && D <= 31) || (M == 11 && D >= 1 && D <= 21))
            return "Scorpio";
        else if ((M == 11 && D >= 22 && D <= 30) || (M == 12 && D >= 1 && D <= 21))
            return "Sagittarius";
        else
            return "Illegal date";

    }

	
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
