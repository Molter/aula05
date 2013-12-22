package br.com.gabrielmolter.homework03v2;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckBoxedAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater mInfalter;
	ArrayList<ShopItem> itens = new ArrayList<ShopItem>();
	private DbHelper mHelper;

	public CheckBoxedAdapter(Context context) {
		
		mContext = context;
		mInfalter = (LayoutInflater) mContext
				.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

		//refreshData(context);
		
	}
	

	public void refreshData(Context context) {
		mHelper = new DbHelper(context);
		
		itens.clear();
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		
		Cursor results = db.query("shop_itens",
				new String[]{"_id", "name", "description","checked","file"},
				null,
				null,
				null,
				null,
				"name ASC");
					
		
		if(results.getCount() == 0){
			return;
		}
		
		results.moveToFirst( );
		
		do{
			ShopItem item = new ShopItem();
			
			long id = results.getLong(results.getColumnIndex("_id"));
			item.setId(id);
			
			String name = results.getString(results.getColumnIndex("name"));
			item.setName(name);
			
			String description = results.getString(results.getColumnIndex("description"));
			item.setDescription(description);
			
			String file = results.getString(results.getColumnIndex("file"));
			item.setFilePath(file);
					
			int checked = results.getInt(results.getColumnIndex("checked"));
			if(checked == 1){
				item.setCchecked(true);
			}else{
				item.setCchecked(false);
			}
			
			itens.add(item);
		}while (results.moveToNext());
		
		notifyDataSetChanged();
		
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		ShopItem item = itens.get(position);
		return item.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = mInfalter.inflate(R.layout.item_list, null);
		
		ShopItem item = itens.get(position);
		final TextView item_name = (TextView)layout.findViewById(R.id.item_list_name);
		item_name.setText(item.getName());
		
		ImageView image = (ImageView) layout.findViewById(R.id.item_list_image);
		
		if(item.getFilePath().isEmpty()){
			image.setVisibility(View.INVISIBLE);
		}else{
			Bitmap bitmap = BitmapFactory.decodeFile(item.getFilePath());
			image.setImageBitmap(bitmap);
		}
		CheckBox box = (CheckBox) layout.findViewById(R.id.item_list_checkbox);
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					item_name.setPaintFlags(item_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				}else{
					item_name.setPaintFlags(item_name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
				}
			}
		});
		
		if(item.isCchecked){
			item_name.setPaintFlags(item_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		
		return layout;
	}
	
	

}
