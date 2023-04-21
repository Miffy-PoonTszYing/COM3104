package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    DBOpenHelper myDb;
    TextView tv_total;
    ListView lv_summary;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /******************* Start OnClickListener for mean bar********************************/
        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        tv_total = findViewById(R.id.tv_total);
        lv_summary = findViewById(R.id.lv_summary);


        bt_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivity(intent);
            }
        });
        bt_wallet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Wallet.class);
                startActivity(intent);
            }
        });
        bt_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bt_plan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Plan.class);
                startActivity(intent);
            }
        });
        bt_account.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Account.class);
                startActivity(intent);
            }
        });
        /******************* End OnClickListener for mean bar********************************/

        // Calculation of the expense
        myDb = new DBOpenHelper(this);
        Cursor cursor= myDb.getAllData();
        int resultCounts = cursor.getCount();

        if (resultCounts == 0 || !cursor.moveToFirst()){
            Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_LONG).show();
        }else{
            int index_type = 0;
            int index_amount = 0;
            int index_date = 0;
            int index_category = 0;
            ArrayList record = new ArrayList();
            //Output value
            int total = 0;
            Log.d("DB: ", "Total"+ total);
            int Food = 0;
            Log.d("DB: ", "Food Expense"+ Food);
            int Transportation = 0;
            Log.d("DB: ", "Transportation Expense"+ Transportation);
            int Bill = 0;
            Log.d("DB: ", "Bill"+ Bill);
            int Pet = 0;
            Log.d("DB: ", "Pet Expense"+ Pet);
            int Salary = 0;
            Log.d("DB: ", "Salary "+ total);
            int Gift = 0;
            Log.d("DB: ", "Gift Expense"+ total);
            String category = null;

            for(int i=0; i<resultCounts;i++){
                index_type = cursor.getColumnIndex(DBOpenHelper.KEY_Type);
                index_amount = cursor.getColumnIndex(DBOpenHelper.KEY_Amount);
                index_date = cursor.getColumnIndex(DBOpenHelper.KEY_Date);
                index_category = cursor.getColumnIndex(DBOpenHelper.KEY_Category);

                total = total + cursor.getInt(index_amount);
                Log.d("DB: ", "total"+ i +" = "+ total);
                category = cursor.getString(index_category);
                switch (category){
                    case "Food":
                        Food = Food + cursor.getInt(index_amount);
                        break;
                    case "transportation":
                        Transportation = Transportation + cursor.getInt(index_amount);
                        break;
                    case "Bill":
                        Bill = Bill + cursor.getInt(index_amount);
                        break;
                    case "Pet":
                        Pet = Pet + cursor.getInt(index_amount);
                        break;
                    case "Salary":
                        Salary = Salary + cursor.getInt(index_amount);
                        break;
                    case "Gift":
                        Gift = Gift + cursor.getInt(index_amount);
                        break;
                }
                cursor.moveToNext();

            }
            record.add("Food Expense = " + Food);
            record.add("Transportation Expense = "+ Transportation);
            record.add("Bill = "+ Bill);
            record.add("Pet Expense = "+ Pet);
            record.add("Salary = "+ Salary);
            record.add("Gift Expense = "+ Gift);

            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1, record);
            lv_summary.setAdapter(adapter);
            tv_total.setText(Integer.toString(total));
        }

    }
}