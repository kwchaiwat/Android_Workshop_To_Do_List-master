package com.example.todolistworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    SQLiteDatabase sqliteMyDB;
    MyDbHelper myDataHelper;
    Cursor myDBCursor;
    public String query_string;
    public EditText txt_edit_title, txt_edit_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String txt_get_title = intent.getStringExtra("title");
        myDataHelper = new MyDbHelper(this);
        sqliteMyDB = myDataHelper.getWritableDatabase();

        myDBCursor = sqliteMyDB.rawQuery("SELECT * FROM notes WHERE title= '" + txt_get_title + "'", null);
        myDBCursor.moveToFirst();

        txt_edit_title = (EditText) findViewById(R.id.txt_edit_title);
        txt_edit_desc = (EditText) findViewById(R.id.txt_edit_desc);
        txt_edit_title.setText(myDBCursor.getString(myDBCursor.getColumnIndex("title")));
        txt_edit_desc.setText(myDBCursor.getString(myDBCursor.getColumnIndex("description")));

        final Button updateBtn = (Button) findViewById(R.id.btnUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(txt_get_title);
            }
        });

        final Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteRow(txt_get_title);
            }
        });
    }

    private long update(String txt_get_title) {
        try {
            myDataHelper = new MyDbHelper(this);
            sqliteMyDB = myDataHelper.getWritableDatabase();
            ContentValues Val = new ContentValues();
            txt_edit_title = (EditText) findViewById(R.id.txt_edit_title);
            txt_edit_desc = (EditText) findViewById(R.id.txt_edit_desc);
            Val.put("title", txt_edit_title.getText().toString());
            Val.put("description", txt_edit_desc.getText().toString());
            long rows = sqliteMyDB.update("notes", Val, "title=?", new
                    String[]{String.valueOf(txt_get_title)});
            sqliteMyDB.close();
            Toast.makeText(DetailActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent showPage = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(showPage);
            return rows;
        } catch (Exception e) {
            return -1;
        }
    }


    private long DeleteRow(String txt_get_title) {
        try {
            myDataHelper = new MyDbHelper(this);
            ContentValues Val = new ContentValues();
            sqliteMyDB = myDataHelper.getWritableDatabase();
            long rows = sqliteMyDB.delete("notes", "title=?", new String[]{
                    String.valueOf(txt_get_title)});
            sqliteMyDB.close();
            Toast.makeText(DetailActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent showPage = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(showPage);
            return rows;
        } catch (Exception e) {
            return -1;
        }
    }

}
