package com.codepath.apps.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	ListView lvTweets;
	JsonHttpResponseHandler tweetDisplayHandler;
	RestClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		Log.d("DEBUG", "Testing Debug");
		callTimelineRefresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	private void callTimelineRefresh(){
		RestClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                    super.onSuccess(jsonTweets);
                   
                    ArrayList<Tweet> listoftweets = Tweet.fromJson(jsonTweets);
                    
                    Log.d("DEBUG", "timeline Success: But this was a bit stupid" + jsonTweets.toString());
                    
                    ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
                    TweetAdapter adapter = new TweetAdapter(getBaseContext(), listoftweets);
                    lvTweets.setAdapter(adapter);
                    
                    Log.d("DEBUG", jsonTweets.toString());
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject result) {
                    super.onFailure(throwable, result);
                    Log.d("DEBUG", "timeline Failure: ");
                    throwable.printStackTrace();         
            }
            
		});
	}
		
	public void onClickRefreshHandler(MenuItem mi){
		Toast.makeText(this, "Fetching new tweets", Toast.LENGTH_SHORT).show();
		callTimelineRefresh();
	}
	
	public void onClickComposeHandler(MenuItem mi){
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, 1);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode == RESULT_OK && requestCode == 1) {    	  
    	  callTimelineRefresh();
      }
    } 


}
