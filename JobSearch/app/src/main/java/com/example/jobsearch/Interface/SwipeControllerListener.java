package com.example.jobsearch.Interface;

import android.support.v7.widget.RecyclerView;

/**
 * Created by User on 3/28/2018.
 */

public interface SwipeControllerListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
