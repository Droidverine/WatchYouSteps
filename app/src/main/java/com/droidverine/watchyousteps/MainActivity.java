package com.droidverine.watchyousteps;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    LocationListener locationListener;
    String langlong;
    TextView lc;
    Button btnstart, btnstop;
    static double distanceInMetres;
    static Location lastLocation = null;

    //Distance to km= distance * 1000
    //Avg speed= distance/time.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lc = findViewById(R.id.locationttxt);
        setSupportActionBar(toolbar);
        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        locationListener = LocServices();
        btnstop.setVisibility(View.GONE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationManager != null) {
                    locationManager.requestLocationUpdates("gps", 0, 100, locationListener);
                    btnstart.setVisibility(View.GONE);
                    btnstop.setVisibility(View.VISIBLE);
                }

            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationManager != null) {
                    locationManager.removeUpdates(locationListener);
                    // locationManager = null;
                    lc.setText("");
                    distanceInMetres = 0;
                    btnstop.setVisibility(View.GONE);
                    btnstart.setVisibility(View.VISIBLE);

                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                return;
            }

        } else {
            methodtoreq();

        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            locationManager.requestLocationUpdates("gps", 0, 1, locationListener);
            methodtoreq();
            return;

        }
    }

    void methodtoreq() {
        locationManager.requestLocationUpdates("gps", 0, 1, locationListener);
        return;

    }

    LocationListener LocServices() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                langlong = String.valueOf(location.getLatitude());


                if (lastLocation != null && lastLocation != location) {
                    distanceInMetres += location.distanceTo(lastLocation);
                    Log.d("loc", "locationali" + distanceInMetres);
                    lc.setText("" + (distanceInMetres / 1000));
                }
                lastLocation = location;


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                if (s.equals(LocationManager.GPS_PROVIDER)) {
                    requestforgps();
                }


            }
        };
        return locationListener;
    }

    private void requestforgps() {
        Log.d("hechalla", "sdsds");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
