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

import java.util.ArrayList;

public class Account extends AppCompatActivity {
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    Button bt_display;
    ListView lv_show;
    DBOpenHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        lv_show = findViewById(R.id.lv_show);
        bt_display = findViewById(R.id.bt_display);

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

        bt_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor= myDb.getAllData(); // get the query result from db and save it in a Cursor object
                int resultCounts = cursor.getCount();
                // cursor.moveToFirst returns false if the cursor is empty
                if (resultCounts == 0 || !cursor.moveToFirst()){
                    Toast.makeText(Account.this, "no data", Toast.LENGTH_LONG).show();
                }else{
                    // in a for loop get the name and number for each contact
                    // merge the info into one string
                    // append the string to an arraylist
                    int index_amount = 0;
                    int index_category = 0;
                    ArrayList contacts = new ArrayList();
                    String str_row = null;
                    for(int i=0; i<resultCounts;i++){
                        index_amount = cursor.getColumnIndex(DBOpenHelper.KEY_Amount);
                        index_category = cursor.getColumnIndex(DBOpenHelper.KEY_Category);
                        str_row = cursor.getString(index_amount)+"\n"+cursor.getInt(index_category);
                        Log.d("DB: ", "row "+i+" = "+ str_row);
                        contacts.add(str_row);
                        cursor.moveToNext();
                    }

                    // standard use of ListView
                    // build an adapter with the built-in layout simple_list_item_1
                    // bind the layout with the dataset
                    // bind your adapter to ListView lv_show
                    ArrayAdapter adapter = new ArrayAdapter(Account.this,
                            android.R.layout.simple_list_item_1, contacts);
                    lv_show.setAdapter(adapter);
                }
            }
        });
    }

}