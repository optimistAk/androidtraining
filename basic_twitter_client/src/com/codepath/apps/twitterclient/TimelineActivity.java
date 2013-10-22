package com.codepath.apps.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
		
	public void onClickRefreshHandler(MenuItem mi){
		Toast.makeText(this, "Refresh clicked", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickComposeHandler(MenuItem mi){
		Toast.makeText(this, "Compose clicked", Toast.LENGTH_SHORT).show();
	}

}
