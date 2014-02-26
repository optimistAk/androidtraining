package com.androidclass.todoapp;

import java.util.ArrayList;

import com.androidclass.groceryassistapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {

	// Variables in global context for this app

	private ArrayList<String> itemList; 
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	public String horoscopeText="hello";

	private LocationManager locationManager;
	private String provider;
	private MyLocationListener mylistener;
	private Criteria criteria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		populateArraylistItems();
		lvItems = (ListView) findViewById(R.id.lvItems);

		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		lvItems.setAdapter(todoAdapter);

		AsyncHttpClient client = new AsyncHttpClient();

		client.get("http://pipes.yahoo.com/pipes/pipe.run?_id=4c472300378d711f542007004321a950&_render=json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {	
				JSONArray horoscopeJsonResults = null;
				try {				
					horoscopeJsonResults = response.getJSONObject("value").getJSONArray("items");				

					// Pass the index value based on what sunsign, here, 0->Aries, 1->Taurus ... etc				
					String horoscopeFullString = horoscopeJsonResults.getJSONObject(0).getString("description").toString();

					Document doc = Jsoup.parse(horoscopeFullString);
					Element p= doc.select("p").first();
					horoscopeText = p.text();

					// Replace this with birth date
					todoAdapter.add("Daily Horoscope for " + CommonLib.findZodiacSign("9", "6") + ": \n" + horoscopeText);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});



		//		client.get("http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&APPID=055a946d7193b12bb5ad491595be1124", new JsonHttpResponseHandler() {
		//			@Override
		//			public void onSuccess(JSONObject response) {	
		//				try {
		//					Log.d("DEBUG", response.getJSONObject("main").getString("temp").toString());
		//				} catch (JSONException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		});

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the location provider
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);	//default


		criteria.setCostAllowed(false); 
		// get the best provider depending on the criteria
		provider = locationManager.getBestProvider(criteria, false);

		// the last known location of this provider
		Location location = locationManager.getLastKnownLocation(provider);

		mylistener = new MyLocationListener();

		if (location != null) {
			mylistener.onLocationChanged(location);
		} else {
			// leads to the settings because there is no last known location
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
		// location updates: at least 1 meter and 200millsecs change
		//NETWORK_PROVIDER
		locationManager.requestLocationUpdates(provider, 200, 1, mylistener);


	}

	private void populateArraylistItems(){
		itemList = new ArrayList<String>();
		itemList.add("Color to wear today: " + CommonLib.colorToWear(64, false));
		itemList.add("Weather: cloudy, 77F, humid in the evening");
		//		itemList.add("Yoplait youghurt");
		//		itemList.add("Onions");
	}

	public void onClickAddTodoItem(View v){
		//EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		//this.todoAdapter.add(etNewItem.getText().toString());
		//etNewItem.setText("");
		Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// Initialize the location fields

			AsyncHttpClient client_w = new AsyncHttpClient();

			client_w.get("http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&APPID=055a946d7193b12bb5ad491595be1124", new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					Log.d("DEBUG", "weather0");
					//String weatherResult = null;
					try{
						String weatherResult = response.getJSONObject("main").getString("temp").toString();
						String cloudResult = response.getJSONObject("clouds").getString("all").toString();
						String cdescResult = response.getJSONArray("weather").getJSONObject(0).getString("description");
						Log.d("DEBUG", weatherResult);
						Log.d("DEBUG", cloudResult);
						Log.d("DEBUG", cdescResult);

					} catch (JSONException e) {
						Log.d("DEBUG", "weather2");
						e.printStackTrace();
					}
				}

			});
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(TodoActivity.this, provider + "'s status changed to "+status +"!",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(TodoActivity.this, "Provider " + provider + " enabled!",
					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(TodoActivity.this, "Provider " + provider + " disabled!",
					Toast.LENGTH_SHORT).show();
		}

	}
}
