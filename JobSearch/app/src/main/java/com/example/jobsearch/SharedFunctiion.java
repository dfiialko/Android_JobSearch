package com.example.jobsearch;

import com.example.jobsearch.RSS.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SharedFunctiion {
    private ArrayList<Item> items;

    public ArrayList<Item> getItems(String passed) {
        items = new ArrayList<Item>();
        try {
            JSONObject jsonObject = new JSONObject(passed);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i= 0;i< jsonArray.length();i++){
                JSONObject js = jsonArray.getJSONObject(i);
                String title = js.getString("title");
                String pubDate = js.getString("pubDate");
                String link = js.getString("link");
                String guid = js.getString("guid");
                String author = js.getString("author");
                String thumbnail = js.getString("thumbnail");
                String description = js.getString("description");
                String content = js.getString("content");
                Item item = new Item( title,pubDate,link,guid,author,thumbnail,description,content);
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
}
