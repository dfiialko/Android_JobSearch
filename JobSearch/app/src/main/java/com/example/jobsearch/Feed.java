package com.example.jobsearch;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.jobsearch.Adapter.FeedAdapter;
import com.example.jobsearch.Interface.SwipeControllerListener;
import com.example.jobsearch.RSS.RssObject;
import com.example.jobsearch.RSS.Item;
import com.example.jobsearch.Swipe.SwipeController;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity implements SwipeControllerListener {

    private RecyclerView recyclerView;
    private RssObject rssObject;
    private String userId;
    public FeedAdapter feedAdapter;
    private CoordinatorLayout coordinatorLayout;
    private List<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        // Receive JSON string passed from previous activity
        String passed = getIntent().getStringExtra("rssobject");
        passed.replaceAll("\"", "\\\"");

        // Use for repeated code
        SharedFunctiion sharedFunctiion = new SharedFunctiion();

        // Fill Item List with method createJSON
        items = sharedFunctiion.getItems(passed);
        //rssObject = new Gson().fromJson(passed,RssObject.class);

        // Create new RSSObject with passed values from Items List
        rssObject = new RssObject("OK",new Feed(),items);
        feedAdapter = new FeedAdapter(rssObject,getBaseContext());
        feedAdapter.notifyDataSetChanged();

        // Attach Swipe controller to control swipe actions on recycle view
        //SwipeController swipeController = new SwipeController();
        SwipeController itemTouchHelperCallback = new SwipeController(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        // Set Layout Manager to Vertical on current context
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FeedAdapter.FeedViewHolder) {
            // get the removed item name to display it in snack bar
            String name = items.get(viewHolder.getAdapterPosition()).getTitle();
            SharedPreferences sharedPref = getSharedPreferences("saved",MODE_PRIVATE);
            String userId = sharedPref.getString("userID",null);
            // backup of removed item for undo purpose
            final Item deletedItem = items.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.LEFT){
                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, name + " removed from list!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // undo is selected, restore the deleted item
                        feedAdapter.restoreItem(deletedItem, deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
            if(direction == ItemTouchHelper.RIGHT){
                Item item = items.get(viewHolder.getAdapterPosition());
                FirebaseDatabase.getInstance().getReference("users").child("/"+userId).child("items").push().setValue(item);
            }
// remove the item from recycler view
            feedAdapter.removeItem(viewHolder.getAdapterPosition());

        }
    }
}
