package com.example.jobsearch.RSS;

import com.example.jobsearch.Feed;
import com.example.jobsearch.RSS.Item;
import com.example.jobsearch.SavedJobs;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by User on 3/23/2018.
 */

public class RssObject{
        @Expose
        public String status;
        @Expose
        public Object feed;
        @Expose
        public List<Item> items;

        public RssObject(){

        }
    public RssObject(String status, Object feed, List<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void removeItems(int position){items.remove(position); }
}
