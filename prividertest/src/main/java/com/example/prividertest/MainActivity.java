package com.example.prividertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.add_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(getApplicationContext(), "phonecontacts.db", null,1);
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","pzq");
                contentValues.put("gender","male");
                contentValues.put("number","10086");
                writableDatabase.insert("contacts",null,contentValues);
                Cursor contacts = writableDatabase.query("contacts", null, null, null, null, null, null);
                if (contacts!=null){
                    while (contacts.moveToNext()){
                        Log.d(TAG, "onClick: "+contacts.getString(1));
                    }
                }

            }
        });
    }
}