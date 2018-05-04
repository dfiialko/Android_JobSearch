package com.example.maps;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by User on 1/31/2018.
 */

public class Location {
    private double longtitude;
    private double latitude;
    private String name;
    private String title;
    private int resource;

    public Location(double longtitude,double latitude,String name,String title,int resource){
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.name = name;
        this.title = title;
        this.resource = resource;
    }

    public double Longtitude(){return this.longtitude;}
    public void SetLongtitude(int longtitude){this.longtitude = longtitude;}
    public double Latittude(){return this.latitude;}
    public void SetLattitude(int latitude){this.latitude = latitude;}
    public String Name(){return this.name;}
    public void SetName(String name){this.name = name;}
    public String Title(){return this.title;}
    public void SetTitle(String title){this.title = title;}
    public int Resource(){return this.resource;}
    public void SetResource(int resource){this.resource = resource;}
}
