package br.com.gabrielmolter.homework03v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	public DbHelper(Context context){
		super(context, "database", null, 1);
	}
	
	private static final String CREATE_DB = " create table shop_itens(" +
			" _id INTEGER PRIMARY KEY not null, " +
			" name TEXT not null ," +
			" description TEXT not null ," +
			" checked INTEGER not null default 0," +
			" file TEXT not null " +
			");";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}