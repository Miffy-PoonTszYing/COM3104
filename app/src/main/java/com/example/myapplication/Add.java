package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Add extends AppCompatActivity {
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account;
    RadioGroup RG;
    RadioButton rb_expence;
    Button bt_clear,bt_save;
    EditText et_note, et_amount;
    TextView et_date , et_category;
    DatePickerDialog datePickerDialog;
    DBOpenHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        et_date = findViewById(R.id.et_date);
        et_category = findViewById(R.id.et_category);
        bt_clear = findViewById(R.id.bt_clear);
        RG= findViewById(R.id.RG);
        et_note = findViewById(R.id.et_note);
        et_amount = findViewById(R.id.et_amount);
        bt_save = findViewById(R.id.bt_save);
        rb_expence = findViewById(R.id.rb_expence);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,Add.class);
                startActivity(intent);

            }
        });
        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,Wallet.class);
                startActivity(intent);

            }
        });
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,MainActivity.class);
                startActivity(intent);

            }
        });
        bt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,Plan.class);
                startActivity(intent);

            }
        });
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,Account.class);
                startActivity(intent);

            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RG.clearCheck();
                et_date.setText("(dd/mm/yyyy)");
                et_category.setText("Select category");
                et_note.setText("");
                et_amount.setText("");

            }
        });


        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Add.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayofmonth) {
                                et_date.setText(dayofmonth + "/" + (monthOfYear + 1 )+"/"+year);

                            }
                        },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });


        et_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Select Category");


                String[] category = getResources().getStringArray(R.array.category);
                int checkedItem = 1;
                builder.setSingleChoiceItems(category,checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        et_category.setText(category[i]);

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        myDb = new DBOpenHelper(this);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    boolean result ;
                    Log.d("DEBUG","rb_expence.isChecked()= " + rb_expence.isChecked());

                    if (rb_expence.isChecked()){
                        result = myDb.insertData("Expense",
                            "-"+et_amount.getText().toString(),
                            et_date.getText().toString(),
                            et_note.getText().toString(),
                            et_category.getText().toString());
                    }
                    else {result = myDb.insertData("Income",
                            et_amount.getText().toString(),
                            et_date.getText().toString(),
                            et_note.getText().toString(),
                            et_category.getText().toString());
                    }



                    Toast.makeText(Add.this,"Insertion = "+result, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(Add.this,"No input",Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(Add.this,Wallet.class);
                startActivity(intent);


            }

        });
    }



}