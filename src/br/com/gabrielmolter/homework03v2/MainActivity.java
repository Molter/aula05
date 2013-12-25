package br.com.gabrielmolter.homework03v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.NumberPicker;

public class MainActivity extends Activity {

	CheckBoxedAdapter adapter;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView)
				findViewById(R.id.listView);
		
		adapter = new CheckBoxedAdapter(this); 
		listView.setAdapter(adapter);
		
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				Intent intent = new Intent(getApplicationContext(), ShopItemActivity.class);
				intent.putExtra("ID", id);
				startActivity(intent);
			}
		
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_item_menu:
			Intent intent = new Intent(this, ShopItemActivity.class);
			startActivity(intent);
			break;

		}
		
		return true;
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		adapter.refreshData(this);
	}

}
