package com.example.jobsearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobsearch.Interface.ItemClickListener;
import com.example.jobsearch.R;
import com.example.jobsearch.RSS.RssObject;
import com.example.jobsearch.RSS.Item;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>  {
    private RssObject rssObject;
    private Context context;
    private LayoutInflater layoutInflater;



    public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ItemClickListener itemClickListener;
        public TextView titleFound, descriptionFound, pubDateFound;
        public RelativeLayout foreGround, backGround;


        public FeedViewHolder(View view) {
            super(view);
            titleFound = (TextView) itemView.findViewById(R.id.titleFound);
            descriptionFound = (TextView) itemView.findViewById(R.id.descriptionFound);
            pubDateFound = (TextView) itemView.findViewById(R.id.pubDateFound);
            foreGround = itemView.findViewById(R.id.view_foreground);
            backGround = itemView.findViewById(R.id.view_background);

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


    public FeedAdapter(RssObject rssObject, Context context) {
        this.rssObject = rssObject;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.titleFound.setText(rssObject.getItems().get(position).getTitle());
        holder.descriptionFound.setText(rssObject.getItems().get(position).getDescription());
        holder.pubDateFound.setText(rssObject.getItems().get(position).getPubDate());

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
