package com.example.maps;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final int MY_LOCATION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    //handles the result of the location permission request by implementing the
    // ActivityCompat.OnRequestPermissionsResultCallback
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("Jody", "onRequestPermissionsResult");
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //either check to see if permission is available, or handle a potential SecurityException before calling mMap.setMyLocationEnabled
                try {
                    mMap.setMyLocationEnabled(true);
                } catch(SecurityException e) {
                    Log.d("Jody", "SecurityException in MapsActivity.onRequestPermissionsResult: " + e.getMessage());
                }
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(MapsActivity.this, "Permission to access your location was denied so your location cannot be displayed on the map.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.kyiv:
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.4,30.5),15));
                return true;
            case R.id.budapest:
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.5,19.4),15));
                return true;
            case R.id.vancouver:
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.12,-123.12),15));
                return true;
            default:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //turning on the my location layer
        //(runtime location permission is required)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("Jody", "enabling my location");
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(50.4,30.5,"kyiv","Kyiv",R.drawable.ic_map_kyiv));
        locations.add(new Location(49.12,-123.12,"vancouver","Vancouver",R.drawable.ic_map_vancouver));
        locations.add(new Location(47.5,19.04,"budapest","Budapest",R.drawable.ic_map_budapest));

        for (Location location: locations) {
            LatLng loc = new LatLng(location.Longtitude(),location.Latittude());
            mMap.addMarker(new MarkerOptions().position(loc).title(location.Title()).icon(BitmapDescriptorFactory.fromResource(location.Resource())));
        }

        mMap.setMyLocationEnabled(true);


        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );


    }

}

