package com.example.jobsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jobsearch.Adapter.FeedAdapter;
import com.example.jobsearch.Adapter.SavedJobsAdapter;
import com.example.jobsearch.RSS.Item;
import com.example.jobsearch.RSS.RssObject;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SavedJobs extends AppCompatActivity {
    // Declarations
    private RecyclerView dbRecycled;
    private RssObject rssObject;
    private Button dbBtnDelete;
    private SavedJobsAdapter savedJobsAdapter;
    private ArrayList<Item> items;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);
        // Creating an instance
        dbRecycled = findViewById(R.id.dbRecycledView);



        // Get user ID from shared pref
        SharedFunctiion sharedFunctiion = new SharedFunctiion();
        SharedPreferences sharedPref = this.getSharedPreferences("saved",MODE_PRIVATE);
        String userId = sharedPref.getString("userID",null);

        items = new ArrayList<Item>();
        // Connect to Firebase - Users
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

        // Get ref by userId( Google Account ID)
        final DatabaseReference ref = database.child(userId).child("items");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()
                     ) {
                    items.add(d.getValue(Item.class));
                }
                rssObject = new RssObject("OK",new SavedJobs(),items);
                savedJobsAdapter = new SavedJobsAdapter(rssObject,getBaseContext());
                savedJobsAdapter.notifyDataSetChanged();
                Context context = SavedJobs.this;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
                dbRecycled.setAdapter(savedJobsAdapter);
                dbRecycled.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                dbRecycled.setItemAnimator(new DefaultItemAnimator());
                dbRecycled.setLayoutManager(layoutManager);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
