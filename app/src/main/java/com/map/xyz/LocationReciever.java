package com.map.xyz;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Provider;

import my.map.xyz.R;

public class LocationReciever extends FragmentActivity implements OnMapReadyCallback ,LocationListener{

    private GoogleMap mMap;
    Location l;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_location_reciever);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);


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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lm = (LocationManager) getSystemService (LOCATION_SERVICE);
        Criteria c = new Criteria ();
        String Provider = lm.getBestProvider (c, true);
        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng (-34, 151);
        mMap.addMarker (new MarkerOptions ().position (sydney).title ("Marker in Sydney"));
        mMap.moveCamera (CameraUpdateFactory.newLatLng (sydney));*/
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

            if (checkSelfPermission (Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission (Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        l = lm.getLastKnownLocation (Provider);
         if (l!=null) {
                onLocationChanged (l);
         }
             lm.requestLocationUpdates (Provider,1000,100, (LocationListener) this);
     }



    @Override
    public void onLocationChanged(Location location) {
        LatLng sydney = new LatLng (l.getLatitude (),l.getLongitude ());
        mMap.moveCamera (CameraUpdateFactory.zoomTo (50));
        mMap.addMarker (new MarkerOptions ().position (sydney).title ("my phone location"));
        mMap.moveCamera (CameraUpdateFactory.newLatLng (sydney));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

