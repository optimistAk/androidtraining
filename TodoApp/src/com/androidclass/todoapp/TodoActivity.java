package com.androidclass.todoapp;

import java.util.ArrayList;

import com.androidclass.groceryassistapp.R;

import android.os.Bundle;
import android.app.Activity;
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
		itemList.add("organic 2% milk");
	}

	private void populateArraylistItems(){
		itemList = new ArrayList<String>();
		itemList.add("AA eggs");
		itemList.add("Yoplait youghurt");
		itemList.add("Onions");
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
