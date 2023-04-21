package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Plan extends AppCompatActivity {
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    ImageView bt_tran;
    TextView input_curr , output_curr,outputnum ;
    EditText inptnum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        bt_tran = findViewById(R.id.bt_tran);
        input_curr = findViewById(R.id.input_curr);
        output_curr = findViewById(R.id.output_curr);
        outputnum = findViewById(R.id.outputnum);
        inptnum = findViewById(R.id.inptnum);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plan.this,Add.class);
                startActivity(intent);
            }
        });
        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plan.this,Wallet.class);
                startActivity(intent);
            }
        });
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plan.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plan.this,Plan.class);
                startActivity(intent);
            }
        });
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plan.this,Account.class);
                startActivity(intent);
            }
        });

        bt_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = String.valueOf(input_curr.getText());
                String out = String.valueOf(output_curr.getText());
                input_curr.setText(out);
                output_curr.setText(in);
            }
        });
    }
}