package com.map.xyz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import my.map.xyz.R;

public class Verifier extends AppCompatActivity {
    Button b;
    TextView tv;
    String msg,from;
    SharedPreferences sp;
    LocationManager locationManager;
    String lattitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier);
        tv = findViewById(R.id.textView);
        b = findViewById(R.id.button);

        Bundle bu = getIntent().getExtras();
        msg = bu.getString("msg");
        from = bu.getString("from");
        tv.setText(msg);
        Toast.makeText(this, "Sent Current Location", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location!=null) {
            double latti = location.getLatitude();
            double longi = location.getLongitude();
            lattitude = String.valueOf(latti);
            longitude = String.valueOf(longi);
        }
            if (msg.equalsIgnoreCase("12345")) {
                String data="https://www.google.com/maps/search/?api=1&query=" + lattitude + ","+ longitude;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(from, null, ""+data, null, null);
        //smsManager.sendTextMessage("+918009618807", null, ""+data, null, null);

        Toast.makeText(this, ""+data, Toast.LENGTH_SHORT).show();}

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}




