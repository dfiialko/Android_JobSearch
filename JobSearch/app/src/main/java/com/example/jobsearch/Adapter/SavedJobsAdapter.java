package com.example.jobsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobsearch.Interface.ItemClickListener;
import com.example.jobsearch.R;
import com.example.jobsearch.RSS.Item;
import com.example.jobsearch.RSS.RssObject;
import com.example.jobsearch.SavedJobs;
import com.example.jobsearch.SharedFunctiion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SavedJobsAdapter extends RecyclerView.Adapter<SavedJobsAdapter.FeedViewHolder>  {
    private RssObject rssObject;
    private Context context;
    private LayoutInflater layoutInflater;



    public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ItemClickListener itemClickListener;
        public TextView title, description, date;
        public RelativeLayout recycler;
        public Button dbBtnDelete;

        public FeedViewHolder(View view) {
            super(view);

            title = (TextView) itemView.findViewById(R.id.jobTitle);
            description = (TextView) itemView.findViewById(R.id.jobDescription);
            date = (TextView) itemView.findViewById(R.id.jobPublished);
            recycler = itemView.findViewById(R.id.dbRecycledView);
            dbBtnDelete = itemView.findViewById(R.id.dbBtnDelete);

            dbBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    removeItem(i);

                    // Get user ID from shared pref
                    SharedFunctiion sharedFunctiion = new SharedFunctiion();
                    SharedPreferences sharedPref = context.getSharedPreferences("saved", Context.MODE_PRIVATE);
                    String userId = sharedPref.getString("userID",null);

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference ref = database.child(userId).child("items");
                    final Query query = ref.orderByChild("description").equalTo(description.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dummySnapshot: dataSnapshot.getChildren()) {
                                dummySnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
            view.setOnClickListener(this);
        }


        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            this.itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }


    public SavedJobsAdapter(RssObject rssObject, Context context) {
        this.rssObject = rssObject;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.searched_row,parent,false);
        return new FeedViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.title.setText(rssObject.getItems().get(position).getTitle());
        holder.description.setText(rssObject.getItems().get(position).getDescription());
        holder.date.setText(rssObject.getItems().get(position).getPubDate());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                String link = rssObject.getItems().get(position).link;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                context.startActivity(intent);
            }
        });
    }

    public void removeItem(int position) {
        rssObject.removeItems(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        rssObject.items.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}