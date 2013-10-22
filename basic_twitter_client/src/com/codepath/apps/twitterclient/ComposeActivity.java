package com.codepath.apps.twitterclient;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	private EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void composeClickAction(View v){
		etTweet = (EditText) findViewById(R.id.etNewTweet);
		String strTweet = etTweet.getText().toString();
		
		Toast.makeText(this, "Button clicked & tweet is: "+strTweet, Toast.LENGTH_SHORT).show();
		
		RestClientApp.getRestClient().postTweet(strTweet, new JsonHttpResponseHandler(){
			
			@Override
            public void onSuccess(JSONObject status) {
				Log.d("DEBUG", "Tweet posted successfully " + status);
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				finish();
            }  
			
		});
	}

}
