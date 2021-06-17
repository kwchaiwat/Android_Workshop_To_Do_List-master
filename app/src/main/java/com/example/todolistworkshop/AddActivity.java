package com.example.todolistworkshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase sqliteMyDB;
    MyDbHelper myDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button addData = (Button) findViewById(R.id.btn_submit);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddNew()) {
                    Intent showPage = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(showPage);
                }
            }
        });
    }

    public boolean AddNew() {
        final EditText editTextTitle = (EditText) findViewById(R.id.txt_add_title);
        final EditText editTextDetail = (EditText) findViewById(R.id.txt_add_desc);
        final AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        AlertDialog alertMsg = alertBox.create();
        //Check Filter
        if (editTextTitle.getText().length() == 0) {
            alertMsg.setMessage("Please insert Movie's Title");
            alertMsg.show();
            editTextTitle.requestFocus();
            return false;
        }
        if (editTextDetail.getText().length() == 0) {
            alertMsg.setMessage("Please insert Movie's Detail");
            alertMsg.show();
            editTextDetail.requestFocus();
            return false;
        }
        myDataHelper = new MyDbHelper(this);
        sqliteMyDB = myDataHelper.getWritableDatabase();
        ContentValues Val = new ContentValues();
        Val.put("title", editTextTitle.getText().toString());
        Val.put("description", editTextDetail.getText().toString());
        long rows = sqliteMyDB.insert("notes", null, Val);
        sqliteMyDB.close();
        Toast.makeText(AddActivity.this, "Success!", Toast.LENGTH_SHORT).show();
        return true;
    }
}
