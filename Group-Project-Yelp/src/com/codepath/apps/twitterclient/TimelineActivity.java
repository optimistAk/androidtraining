package com.codepath.apps.twitterclient;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.fragments.MentionsFragment;
import com.codepath.apps.twitterclient.fragments.TweetsListFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener{

	ListView lvTweets;
	JsonHttpResponseHandler tweetDisplayHandler;
	RestClient client;
	
	TweetsListFragment fragmentTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		
		fragmentTweets =
				(TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		
		setupNavigationTabs();
		// callTimelineRefresh();
		
//		RestClientApp.getRestClient().getHomeTimeline(
//			new JsonHttpResponseHandler() {
//				
//				@Override
//				public void onSuccess(JSONArray jsonTweets) {
//					ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
//					fragmentTweets.getAdapter().addAll(tweets);
//				}
//			});
		
	}
	
	private void setupNavigationTabs(){
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
				.setTabListener(this);
		
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment").setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
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
                   
//                    ArrayList<Tweet> listoftweets = Tweet.fromJson(jsonTweets);
//                    
//                    Log.d("DEBUG", "timeline Success: But this was a bit stupid" + jsonTweets.toString());
//                    
//                    ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
//                    TweetAdapter adapter = new TweetAdapter(getBaseContext(), listoftweets);
//                    lvTweets.setAdapter(adapter);
//                    
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		FragmentManager manager = getSupportFragmentManager();
		
		android.support.v4.app.FragmentTransaction fts =
				manager.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.fragmentTweets, new HomeTimelineFragment());
		} else{
			fts.replace(R.id.fragmentTweets,  new MentionsFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	} 


}
