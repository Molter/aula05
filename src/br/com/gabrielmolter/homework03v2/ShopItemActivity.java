package br.com.gabrielmolter.homework03v2;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class ShopItemActivity extends FragmentActivity {

	EditText nameEditText;
	EditText descriptionEditText;
	private ShopItem mItem;

	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private long mId;
	private NumberPicker picker;
	
	private Activity mSelf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_item);

		dbHelper = new DbHelper(this);
		db = dbHelper.getWritableDatabase();

		mSelf = this;
		
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

							ContentValues values = new ContentValues();
							values.put("name", item.getName());
							values.put("description", item.getDescription());
							
							if (item.isCchecked()) {
								values.put("checked", 1);
							} else {
								values.put("checked", 0);
							}

							values.put("file", "");

							int total = picker.getValue();
							
							if(total >= 5){
								ProgressDialog progressDialog = new ProgressDialog(mSelf);
								
								progressDialog.setCancelable(false);
								
								progressDialog.setTitle("Aguarde...");
								
								progressDialog.show();
								
								CreateRecords task = new CreateRecords(mSelf, values, progressDialog);
								Integer[] params = new Integer[1];
								params[0] = total;
								
								task.execute(params);
								
								task.execute(params);
								
								
							}else{
								for (int i = 0; i < total; i++) {
									db.insert("shop_itens", null, values);
								}
								
								Toast.makeText(getApplicationContext(),
										getString(R.string.save_success),
										Toast.LENGTH_LONG).show();
								finish();
							}
							
						} else {

							ContentValues values = new ContentValues();
							values.put("name", mItem.getName());
							values.put("description", mItem.getDescription());
							if (mItem.isCchecked()) {
								values.put("checked", 1);
							} else {
								values.put("checked", 0);
							}

							int returned = db.update("shop_itens", values,
									"_id = ?",
									new String[] { String.valueOf(mId) });

							if (returned == 0) {
								Toast.makeText(getApplicationContext(),
										getString(R.string.save_error),
										Toast.LENGTH_LONG).show();
								finish();
							}
							
							Toast.makeText(getApplicationContext(),
									getString(R.string.save_success),
									Toast.LENGTH_LONG).show();
							finish();

						}

						
					}

				});

		if (getIntent().getExtras() != null) {
			mId = getIntent().getExtras().getLong("ID");

			Cursor result = db.query("shop_itens", new String[] { "_id",
					"name", "description", "checked", "file" }, "_id = ?",
					new String[] { String.valueOf(mId) }, null, null, null,
					null);

			if (result.getCount() == 0) {
				Toast.makeText(getApplicationContext(), "Wrong Item",
						Toast.LENGTH_LONG).show();
				finish();
			}
			result.moveToFirst();

			mItem = new ShopItem();

			mItem.setId(mId);

			String name = result.getString(result.getColumnIndex("name"));
			mItem.setName(name);

			nameEditText.setText(name);
			int index = nameEditText.getText().toString().length();
			nameEditText.setSelection(index);

			String description = result.getString(result
					.getColumnIndex("description"));
			mItem.setDescription(description);
			descriptionEditText.setText(description);

			String file = result.getString(result.getColumnIndex("file"));
			mItem.setFilePath(file);

			int checked = result.getInt(result.getColumnIndex("checked"));
			if (checked == 1) {
				mItem.setCchecked(true);
			} else {
				mItem.setCchecked(false);
			}

		}

		
		picker = (NumberPicker) findViewById(R.id.numberPicker);
		picker.setMaxValue(30);
		picker.setMinValue(1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mItem == null) {
			return false;
		}
		getMenuInflater().inflate(R.menu.shop_item_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.delete_item_menu:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Deseja realmente Excluir o registro ?");

			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							db.delete("shop_itens",
									"_id = ?",
									new String[] { String.valueOf(mId) });
							
							Toast.makeText(getApplicationContext(), "Item Removido",
									Toast.LENGTH_LONG).show();
							finish();

						}
					});

			builder.setNegativeButton("Não",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create();
			builder.show();
			break;

		}

		return true;
	}
}
