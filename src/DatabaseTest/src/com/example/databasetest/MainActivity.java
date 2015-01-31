package com.example.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        Button addData = (Button) findViewById(R.id.add_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		SQLiteDatabase db = dbHelper.getWritableDatabase();
        		
        		Cursor cursor = db.query("Book", null, null, null, null, null, null);
        		if(cursor.moveToFirst()){
        			do{
        				String name = cursor.getString(cursor.getColumnIndex("name"));
        				String author = cursor.getString(cursor.getColumnIndex("author"));
        				int pages = cursor.getInt(cursor.getColumnIndex("pages"));
        				double price = cursor.getDouble(cursor.getColumnIndex("price"));
        				Log.d("hehe", "book name is " + name);
        				Log.d("hehe", "book author is " + author);
        				Log.d("hehe", "book pages is " + pages);
        				Log.d("hehe", "book price is " + price);
        				
        			}while(cursor.moveToNext());
        		}
        	}
        });
        deleteData.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		SQLiteDatabase db = dbHelper.getWritableDatabase();
        		db.delete("Book","pages > ?", new String[]{"500"});
        	}
        });
        updateData.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		SQLiteDatabase db = dbHelper.getWritableDatabase();
        		ContentValues values = new ContentValues();
        		values.put("price", 10.99);
        		db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
        	}
        });
        addData.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		SQLiteDatabase db = dbHelper.getWritableDatabase();
        		ContentValues values = new ContentValues();
        		values.put("name", "The Da Vinci Code");
        		values.put("author", "Dan Brown");
        		values.put("pages", 454);
        		values.put("price", 16.69);
        		db.insert("Book", null, values);
        		values.clear();
        		
        		values.put("name", "The Lost Symbol");
        		values.put("author", "Dan Brown");
        		values.put("pages" , 510);
        		values.put("price", 19.95);
        		db.insert("Book", null, values);
        	}
        });
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
