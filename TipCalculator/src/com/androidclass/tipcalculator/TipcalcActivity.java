package com.androidclass.tipcalculator;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TipcalcActivity extends Activity {

	private double foodAmount=40;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipcalc);
	}

	public void onClickTipButtonHandler(View v){ 
		String str = new String();
		DecimalFormat df = new DecimalFormat("0.00");
		
		EditText etCheckAmount = (EditText) findViewById(R.id.etCheckAmount);
		TextView tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		
		if(etCheckAmount.getText().toString().length() != 0) {
				
			foodAmount = Double.parseDouble(etCheckAmount.getText().toString());
		
			switch(v.getId()){
		
			case R.id.btn10Per:        
				str = String.valueOf(df.format(foodAmount*0.1));
				break;
			
			case R.id.btn15Per:
				str = String.valueOf(df.format(foodAmount*0.15));
				break;
			
			case R.id.btn20Per:
				str = String.valueOf(df.format(foodAmount*0.2));
				break;
			}			
			tvTipAmount.setText("$" +str);
		} else{
			tvTipAmount.setText("$0.0");
			Toast.makeText(this, "Pls enter a valid amount", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipcalc, menu);
		return true;
	}

}
