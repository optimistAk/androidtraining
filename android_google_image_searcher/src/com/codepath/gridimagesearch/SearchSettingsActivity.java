package com.codepath.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchSettingsActivity extends Activity {

	private Spinner spinner1, spinner2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_settings, menu);
		return true;
	}
	
	public void onSettingsSubmit(View v) {
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		
//		Toast.makeText(this,
//				"Settings Selected : " + 
//		                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) + 
//		                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
//					Toast.LENGTH_SHORT).show();
		
		Intent data = new Intent();
		data.putExtra("colorChoice", String.valueOf(spinner1.getSelectedItem()));
		data.putExtra("filterChoice", String.valueOf(spinner2.getSelectedItem()));
		
		setResult(RESULT_OK, data); 
		finish();
	}

}
