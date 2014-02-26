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

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		populateArraylistItems();
		lvItems = (ListView) findViewById(R.id.lvItems);

		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		lvItems.setAdapter(todoAdapter);
		itemList.add("Daily Horoscope: " + CommonLib.dailyHoroscope());
		
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

					Log.d("DEBUG", horoscopeText);
					
					todoAdapter.add("Daily Horoscope" + horoscopeText);
					
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

}
