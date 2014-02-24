package com.androidclass.todoapp;

import java.util.ArrayList;

import com.androidclass.groceryassistapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		populateArraylistItems();
		lvItems = (ListView) findViewById(R.id.lvItems);

		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		lvItems.setAdapter(todoAdapter);
		itemList.add("Daily Horoscope" + CommonLib.dailyHoroscope());
		
		AsyncHttpClient client = new AsyncHttpClient();
		
//		client.get("http://pipes.yahoo.com/pipes/pipe.run?_id=4c472300378d711f542007004321a950&_render=json", new AsyncHttpResponseHandler() {
//		    @Override
//		    public void onSuccess(String response) {
//		        System.out.println("AKKKKK" + response);
//		        Log.d("DEBUG", "AKKK" + response.toString());
//		    }
//		});
		
		client.get("http://pipes.yahoo.com/pipes/pipe.run?_id=4c472300378d711f542007004321a950&_render=json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {	
				Log.d("DEBUG", "AKK");
				JSONArray horoscopeJsonResults = null;
				try {
					Log.d("DEBUG", "AKK");
					//Log.d("DEBUG", response.getJSONObject("value").getJSONArray("items").toString());
					
					horoscopeJsonResults = response.getJSONObject("value").getJSONArray("items");
					
					Log.d("DEBUG", horoscopeJsonResults.getJSONObject(0).getString("description").toString());
					
					itemList.add("Aravind is good");
					//itemList.add(horoscopeJsonResults.getJSONObject(0).getString("description").toString());
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void populateArraylistItems(){
		itemList = new ArrayList<String>();
		itemList.add("Color to wear today:" + CommonLib.colorToWear());
		itemList.add("Weather: cloudy, 77F, humid in the evening");
//		itemList.add("Yoplait youghurt");
//		itemList.add("Onions");
	}
	
	public void onClickAddTodoItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		this.todoAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}
