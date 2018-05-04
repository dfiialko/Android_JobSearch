package com.example.a7a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by User on 2/27/2018.
 */

public class CustomAdapter extends ArrayAdapter {


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
