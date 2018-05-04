package com.example.jobsearch.Swipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telecom.Call;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;

import com.example.jobsearch.Adapter.FeedAdapter;
import com.example.jobsearch.Feed;
import com.example.jobsearch.Interface.SwipeControllerListener;
import com.example.jobsearch.MainActivity;
import com.example.jobsearch.R;

import static android.support.v7.widget.helper.ItemTouchHelper.*;
/**
 * Created by User on 3/27/2018.
 */

public class SwipeController extends ItemTouchHelper.Callback {
    private SwipeControllerListener listener;

    public SwipeController(int dragDirs, int swipeDirs, SwipeControllerListener listener) {
       // super(dragDirs, swipeDirs);
        this.listener = listener;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((FeedAdapter.FeedViewHolder) viewHolder).foreGround;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((FeedAdapter.FeedViewHolder) viewHolder).foreGround;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((FeedAdapter.FeedViewHolder) viewHolder).foreGround;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }
}
