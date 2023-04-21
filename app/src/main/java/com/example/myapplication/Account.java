package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Account extends AppCompatActivity {
    DBOpenHelper myDb;
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    Button bt_csv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        bt_csv = findViewById(R.id.bt_csv);
        myDb = new DBOpenHelper(this);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Add.class);
                startActivity(intent);

            }
        });
        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Wallet.class);
                startActivity(intent);

            }
        });
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,MainActivity.class);
                startActivity(intent);

            }
        });
        bt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Plan.class);
                startActivity(intent);

            }
        });
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this,Account.class);
                startActivity(intent);

            }
        });
        bt_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor= myDb.getAllData();
                int resultCounts = cursor.getCount();

                if (resultCounts == 0 || !cursor.moveToFirst()){
                    Toast.makeText(Account.this, "no data", Toast.LENGTH_LONG).show();
                }else{
                    int index_type = 0;
                    int index_amount = 0;
                    int index_date = 0;
                    int index_note = 0;
                    int index_category = 0;
                    ArrayList record = new ArrayList();
                    String str_row = null;
                    Log.d("DB: ", "row "+ str_row);

                    try {
                        PrintWriter writer = new PrintWriter(new File("/data/data/com.example.myapplication/output.csv"));
                        for(int i=0; i<resultCounts;i++){
                            index_type = cursor.getColumnIndex(DBOpenHelper.KEY_Type);
                            index_amount = cursor.getColumnIndex(DBOpenHelper.KEY_Amount);
                            index_date = cursor.getColumnIndex(DBOpenHelper.KEY_Date);
                            index_note = cursor.getColumnIndex(DBOpenHelper.KEY_Note);
                            index_category = cursor.getColumnIndex(DBOpenHelper.KEY_Category);

                            String date = cursor.getString(index_date);
                            String type = cursor.getString(index_type);
                            String category = cursor.getString(index_category);
                            String note = cursor.getString(index_note);
                            int amount = cursor.getInt(index_amount);
                            Log.d("DB: ", "row "+i+" = "+ str_row);
                            writer.printf("%s,%s,%s,%s,%d\n", date, type, category, note, amount);
                            cursor.moveToNext();
                        }
                        writer.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }



                }
            }
        });


    }
}