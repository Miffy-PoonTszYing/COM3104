package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends AppCompatActivity {
    DBOpenHelper myDb;
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    ListView lv_show,lv_show2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        //lv_show = findViewById(R.id.lv_show);
        lv_show2 = findViewById(R.id.lv_show2);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Add.class);
                startActivity(intent);

            }
        });
        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Wallet.class);
                startActivity(intent);

            }
        });
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,MainActivity.class);
                startActivity(intent);

            }
        });
        bt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Plan.class);
                startActivity(intent);

            }
        });
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Account.class);
                startActivity(intent);

            }
        });


        myDb = new DBOpenHelper(this);
        Cursor cursor= myDb.getAllData();
        int resultCounts = cursor.getCount();

        if (resultCounts == 0 || !cursor.moveToFirst()){
            Toast.makeText(Wallet.this, "no data", Toast.LENGTH_LONG).show();
        }else{
            int index_type = 0;
            int index_amount = 0;
            int index_date = 0;
            int index_note = 0;
            int index_category = 0;

            ArrayList record = new ArrayList();
            ArrayList amount_record = new ArrayList();
            ArrayList date_record = new ArrayList();
            ArrayList note_record = new ArrayList();
            ArrayList category_record = new ArrayList();


            String str_row = null;
            Log.d("DB: ", "row "+ str_row);

            for(int i=0; i<resultCounts;i++){
                index_type = cursor.getColumnIndex(DBOpenHelper.KEY_Type);
                index_amount = cursor.getColumnIndex(DBOpenHelper.KEY_Amount);
                index_date = cursor.getColumnIndex(DBOpenHelper.KEY_Date);
                index_note = cursor.getColumnIndex(DBOpenHelper.KEY_Note);
                index_category = cursor.getColumnIndex(DBOpenHelper.KEY_Category);

                str_row = cursor.getString(index_date)
                        +"\n"+cursor.getString(index_type)
                        +"\n"+cursor.getString(index_category)
                        +"\n"+cursor.getString(index_note)
                        +"\n"+cursor.getInt(index_amount);
                Log.d("DB: ", "row "+i+" = "+ str_row);
                record.add(str_row);

                String str_date = cursor.getString(index_date);
                String str_amount = cursor.getString(index_amount);
                String str_note = cursor.getString(index_note);
                String str_category = cursor.getString(index_category);

                 amount_record.add(str_amount);
                 date_record.add(str_date);
                 note_record.add(str_note);
                 category_record.add(str_category);
                cursor.moveToNext();
            }


/*

            ArrayAdapter adapter = new ArrayAdapter(Wallet.this,
                    android.R.layout.simple_list_item_1, record);
            lv_show.setAdapter(adapter);
*/


            List<String> list_amount = amount_record;
            String[] list_1 = list_amount.toArray(new String[0]);
            List<String> list_date = date_record;
            String[] list_2 = list_date.toArray(new String[0]);
            List<String> list_note = note_record;
            String[] list_3 = list_note.toArray(new String[0]);
            List<String> list_category = category_record;
            String[] list_4 = list_category.toArray(new String[0]);

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), list_1, list_2,list_3,list_4);



            lv_show2.setAdapter(customAdapter);


        }


    }
}