package com.example.a7a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 2/9/2018.
 */

public class MobileListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListItem> values;

    public MobileListAdapter(Context context, ArrayList<ListItem> values) {
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.activity_main, parent, false);
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.title);

//        TextView subtitleTextView =
//                (TextView) rowView.findViewById(R.id.des);
//
//        TextView detailTextView =
//                (TextView) rowView.findViewById(R.id.date);
        ListItem item = (ListItem) getItem(position);
        titleTextView.setText(item.title);
//        subtitleTextView.setText(item.description);
//        detailTextView.setText(item.pubDate);
        return rowView;
    }


}