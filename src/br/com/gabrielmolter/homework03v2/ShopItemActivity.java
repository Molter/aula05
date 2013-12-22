package br.com.gabrielmolter.homework03v2;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ShopItemActivity extends Activity {

	EditText nameEditText;
	EditText descriptionEditText;
	private ShopItem mItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_item);

		nameEditText = (EditText) findViewById(R.id.shop_item_name);
		descriptionEditText = (EditText) findViewById(R.id.shop_item_desctiption);

		findViewById(R.id.submitButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						String name = nameEditText.getText().toString();
						String description = descriptionEditText.getText()
								.toString();

						if (name.isEmpty() || description.isEmpty()) {
							Toast.makeText(getApplicationContext(),
									getString(R.string.save_error),
									Toast.LENGTH_LONG).show();
							return;
						}
						ShopItem item;
						if (mItem != null) {
							item = mItem;
						} else {

							item = new ShopItem();
						}

						item.setName(name);
						item.setDescription(description);

						Random r = new Random();
						item.setId(r.nextLong());

						if (mItem == null) {
							
							DbHelper dbHelper = new DbHelper(getApplicationContext());
							SQLiteDatabase db = dbHelper.getWritableDatabase();
							
							ContentValues values = new ContentValues();
							values.put("name", item.getName());
							values.put("description", item.getDescription());
							if(item.isCchecked()){
								values.put("checked", 1);
							}else{
								values.put("checked", 0);
							}
							
							values.put("file", "");
							
							db.insert("shop_itens", null, values);
						}

						Toast.makeText(getApplicationContext(),
								getString(R.string.save_success),
								Toast.LENGTH_LONG).show();
						finish();
					}

				});

		if (getIntent().getExtras() != null) {
			long id = getIntent().getExtras().getLong("ID");

			ArrayList<ShopItem> itens = ShopItem.getList();
			for (int i = 0; i < itens.size(); i++) {
				if (itens.get(i).getId() == id) {
					nameEditText.setText(itens.get(i).getName());
					descriptionEditText.setText(itens.get(i).getDescription());

					mItem = itens.get(i);
					break;
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(mItem == null){
			return false;
		}
		getMenuInflater().inflate(R.menu.shop_item_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.delete_item_menu:
			ShopItem.getList().remove(mItem);
			finish();
			break;

		}

		return true;
	}
}
