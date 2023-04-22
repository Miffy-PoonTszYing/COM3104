package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String amount_record[];
    String date_record[];
    String note_record[];
    String category_record[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] amount_record, String[] date_record, String[] note_record, String[] category_record) {
        this.context = context;
        this.amount_record = amount_record;
        this.date_record = date_record;
        this.note_record = note_record;
        this.category_record = category_record;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return amount_record.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_list_view, null);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
        TextView tv_category = (TextView) view.findViewById(R.id.tv_category);
        TextView tv_note = (TextView) view.findViewById(R.id.tv_note);
        TextView tv_amount = (TextView) view.findViewById(R.id.tv_amount);

        tv_date.setText(date_record[i]);
        tv_category.setText(category_record[i]);
        tv_note.setText(note_record[i]);
        tv_amount.setText(amount_record[i]);

        return view;
    }
}