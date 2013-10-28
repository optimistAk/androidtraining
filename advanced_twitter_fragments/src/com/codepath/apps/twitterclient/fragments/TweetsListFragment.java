package com.codepath.apps.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TweetAdapter;
import com.codepath.apps.twitterclient.models.Tweet;

public class TweetsListFragment extends Fragment {

	private ArrayList<Tweet> listoftweets;
	TweetAdapter adapter;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
      // Setup handles to view objects here
      // etFoo = (EditText) v.findViewById(R.id.etFoo);
      return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);
    	
      listoftweets = new ArrayList<Tweet>();
      
      ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
      adapter = new TweetAdapter(getActivity(), listoftweets);
      lvTweets.setAdapter(adapter);
    }
    
    public TweetAdapter getAdapter() {
    	return adapter;
    }
}
