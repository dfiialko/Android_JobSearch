package com.example.a7a;

import java.io.Serializable;
import java.text.DateFormat;

/**
 * Created by User on 2/7/2018.
 */

public class ListItem implements Serializable{
    public String title;
    public String description;
    public String pubDate;
    public String link;


    ListItem(String title,String description,String pubDate,String link){
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }

    ListItem(){

    }

    public void SetTitle(String title){this.title = title;}
    public void SetDescription(String description){this.description = title;}
    public void SetDate(String description){this.description = title;}

    @Override
    public String toString() {
        return this.title;
    }
}
