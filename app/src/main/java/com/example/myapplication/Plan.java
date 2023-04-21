package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Plan extends AppCompatActivity {
    ImageButton bt_add , bt_wallet ,bt_home, bt_plan, bt_account ;
    EditText et_inptnum;
    Button bt_go;
    TextView tv_outputnum, tv_input_curr, tv_output_curr;
    Handler mHandler;
    private static final String BASE_URL = "https://api.frankfurter.app/latest?";

    private static final String BASE= "base";

    private static final String Symbols = "symbols";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        bt_add = findViewById(R.id.bt_add);
        bt_wallet = findViewById(R.id.bt_wallet);
        bt_home = findViewById(R.id.bt_home);
        bt_plan = findViewById(R.id.bt_plan);
        bt_account = findViewById(R.id.bt_account);
        et_inptnum = findViewById(R.id.inptnum);
        bt_go = findViewById(R.id.button);
        tv_outputnum = findViewById(R.id.outputnum);
        tv_input_curr = findViewById(R.id.input_curr);
        tv_output_curr = findViewById(R.id.output_curr);

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
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String base = tv_input_curr.getText().toString();
                String symbols = tv_output_curr.getText().toString();
                Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(BASE, base)
                        .appendQueryParameter(Symbols, symbols)
                        .build();
                String uri_string = builtURI.toString();
                Thread thread = new Thread(new MyTxhreadSearch(uri_string));
                thread.start();

                mHandler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        String JSONString = msg.obj.toString();

                        JSONObject jsonObject1 = null;
                        JSONObject jsonObject2 = null;
                        try {
                            jsonObject1 = new JSONObject(JSONString);
                            Log.d("DEBUG", JSONString);
                            jsonObject2 = jsonObject1.getJSONObject("rates");
                            Log.d("DEBUG", String.valueOf(jsonObject1));
                            Log.d("DEBUG", String.valueOf(jsonObject2));

                            double rate = jsonObject2.getDouble(symbols);
                            String input = et_inptnum.getText().toString();
                            double inputnum = Double.parseDouble(input);
                            Log.d("DEBUG", String.valueOf(inputnum));
                            double outcome = rate * inputnum;
                            String result = String.valueOf(outcome);
                            tv_outputnum.setText(result);
                            Log.d("DEBUG", String.valueOf(rate));

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
            }
        });

    }

    private class MyTxhreadSearch implements Runnable {
        String uri_string;
        public MyTxhreadSearch(String uri_string) {
            this.uri_string = uri_string;
        }

        @Override
        public void run() {
            URL url = null; // a null URL object
            HttpURLConnection httpURLConnection = null; // a null HttpURLConnection object
            BufferedReader reader = null;
            String JSONString = null;

            try {
                url = new URL(uri_string); // Init the URL object from a string
                Log.d("DEBUG", "url string is " + url);
                httpURLConnection = (HttpURLConnection) url.openConnection(); // connect to the URL through the HttpURLConnection object
                Log.d("DEBUG: ", "response got");
                InputStream inputStream = httpURLConnection.getInputStream();// get the response from the HttpURLConnection object
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return;
                }
                JSONString = buffer.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Message message = Message.obtain();// get a Message object from recycled pool
            message.obj = JSONString;
            mHandler.sendMessage(message);// send out the message to the handler in another thread
            Log.d("DEBUG", JSONString);
        }
    }
}