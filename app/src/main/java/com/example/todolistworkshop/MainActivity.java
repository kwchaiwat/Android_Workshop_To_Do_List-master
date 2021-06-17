package com.example.todolistworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqliteMyDB;
    MyDbHelper myDbHelper;
    Cursor myDBCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (android.widget.ListView) findViewById(R.id.listView);
        myDbHelper = new MyDbHelper(this);

        sqliteMyDB = myDbHelper.getWritableDatabase();
        myDBCursor = sqliteMyDB.rawQuery("SELECT * FROM notes", null);

        ArrayList<String> dirArray = new ArrayList<String>();
        myDBCursor.moveToFirst();
        while (!myDBCursor.isAfterLast()) {
            dirArray.add(myDBCursor.getString(myDBCursor.getColumnIndex("title")));
            myDBCursor.moveToNext();
        }
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirArray);
        listView.setAdapter(adapterDir);

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddActivity = new Intent(MainActivity.this, AddActivity.class);
                startActivity(toAddActivity);
            }
        });

        listView.setAdapter(adapterDir);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemValue = (String) listView.getItemAtPosition(i);
                Intent showPage = new Intent(MainActivity.this, DetailActivity.class);
                showPage.putExtra("title", "" + itemValue + "");
                startActivity(showPage);
            }
        });
    }
}
