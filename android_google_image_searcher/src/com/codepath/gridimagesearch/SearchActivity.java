package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	String imgsize = new String("small"); //default
	String imgcolor = new String("blue"); //default

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onSettingsAction(MenuItem mi){
		Toast.makeText(this, "Settings Icon Clicked", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(SearchActivity.this, SearchSettingsActivity.class);
		startActivityForResult(i, 1);
		
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this,
				"Values of the Settings: " + 
		                "\nSpinner 1 : "+  imgcolor +
		                "\nSpinner 2 : "+ imgsize,
					Toast.LENGTH_SHORT).show();
		
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0
				+ "&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + imgcolor + "&imgsz=" + imgsize, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray(
							"results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == 1) {	     
		  
		  	imgcolor = data.getExtras().getString("colorChoice");
			imgsize = data.getExtras().getString("filterChoice");
			
			Toast.makeText(this,
			"Values Selected from The Settings Activity: " + 
	                "\nSpinner 1 : "+  imgcolor +
	                "\nSpinner 2 : "+ imgsize,
				Toast.LENGTH_SHORT).show();
			
	  }
	} 

}
