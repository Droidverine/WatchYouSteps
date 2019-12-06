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

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    LocationListener locationListener;
    Chronometer chronometer, chronometerkm;
    String langlong;
    TextView lc, TxttotalRun, Txtavgspeed, Txtavgspeedperkm;
    long eachkm;
    ArrayList<Location> locationArrayList=new ArrayList<>();
    Double elapsed;
    Button btnstart, btnstop;
    static double distanceInMetres;
    static Location lastLocation = null;
    long millistart, millisend;

    //Distance to km= distance * 1000
    //Avg speed= distance/time.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lc = findViewById(R.id.locationttxt);
        setSupportActionBar(toolbar);
        chronometerkm = findViewById(R.id.chronometerperkm);
        chronometer = findViewById(R.id.chronometer);
        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        TxttotalRun = findViewById(R.id.txttotalrun);
        Txtavgspeed = findViewById(R.id.txtavgspeed);
        locationListener = LocServices();
        btnstop.setVisibility(View.GONE);
        FloatingActionButton floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GraphiewActivity.class));
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationManager != null) {
                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                    btnstart.setVisibility(View.GONE);
                    btnstop.setVisibility(View.VISIBLE);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    millistart = System.currentTimeMillis();
                    //  Date dt = new Date(dtMili);
                    Log.d("GetTime", "" + (millistart));
                }

            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationManager != null) {
                    chronometerkm.start();
                    locationManager.removeUpdates(locationListener);
                    Float ds=0.0f;
                    millisend = System.currentTimeMillis();

                    for (int i=1;i<locationArrayList.size();i++)
                    {
                        Location nextloc=null;
                   //     Log.d("Arrayofloc",""+locationArrayList.get(i));




                        ds += locationArrayList.get(i-1).distanceTo(locationArrayList.get(i));

                    }
                    //For getting total distance travveled
                    Log.d("Distanceala",""+ds);
                    //For getting total travel time
                    Log.d("Distaneala",""+(millisend - millistart) / 1000);

                    //For getting avg speed over whole run.

                    locationArrayList.clear();

                    // locationManager = null;
                    lc.setText("");
                    btnstop.setVisibility(View.GONE);
                    btnstart.setVisibility(View.VISIBLE);
                    elapsed = Double.valueOf(SystemClock.elapsedRealtime() - chronometer.getBase());
                    //Distance to km= distance * 1000
                    Double avghr = ((elapsed / 1000) / 3600);
                    Log.d("chrono Second", "" + (elapsed / 1000));
                    Log.d("chrono hour", "" + avghr);
                    Log.d("chrono distance in km", "" + distanceInMetres / 1000);
                    //Avg speed= distanceinkm/timeinhr.
                    Double avgspeed = (distanceInMetres / 1000) / avghr;
                    Log.d("chrono speed", "" + avgspeed);
                    Date currentTime = Calendar.getInstance().getTime();
                    File f = new File(getFilesDir(), currentTime + ".txt");

                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                        bw.write("Date and Time \t" + currentTime + "\n");
                        bw.write("Distance Travelled \t" + distanceInMetres / 1000 + "\n");
                        bw.write("Avg Speed \t" + avgspeed);

                        bw.write("Time Elapsed \t" + avghr);

                        Log.d("ZALA", "GHE");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TxttotalRun.setText("" + distanceInMetres / 1000);
                    Txtavgspeed.setText("" + avgspeed);

                    distanceInMetres = 0;

                    chronometer.stop();

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
                startActivity(new Intent(getApplicationContext(),GraphiewActivity.class));

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
                locationArrayList.add(location);
                langlong = String.valueOf(location.getLatitude());
                Log.d("loc", "currentloc " + location);
                Log.d("loc", "locationali " + lastLocation);


                if (lastLocation != null) {
                    distanceInMetres += location.distanceTo(lastLocation);
                    lc.setText("" +distanceInMetres );

                    if(distanceInMetres>1000)
                    {
                        distanceInMetres=0;
                        Log.d("Eachkm","ghe");
                    }

                    /*
                    Log.d("loc", "locationali " + distanceInMetres);
                    eachkm = Math.round(distanceInMetres / 1000);
                    //to get every km
                    if (eachkm > 1) {
                        Log.d("km complete", " km complete");
                        eachkm = 0;
                        millisend = System.currentTimeMillis();
                        //  Date dt = new Date(dtMili);
                        elapsed = Double.valueOf(SystemClock.elapsedRealtime() - chronometer.getBase());

                        Log.d("GetTime", "" + 1/(millisend - millistart) / 1000);
                      //  Log.d("GetTime", "" + elapsed / 1000);
                        // Log.d("chrono per km hour", "" + avghr);
                        // chronometerkm.stop();

                    }

                    lc.setText("" + (distanceInMetres / 1000));
                    */

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
                    lastLocation=null;
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

    void Timermethod() {
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //TextView tv = (TextView) findViewById(R.id.main_timer_text);
                        // tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                        //seconds -= 1;

                        //    if(seconds == 0)
                        //  {
                        //    tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));

                        //  seconds=60;
                        // minutes=minutes-1;

                        //    }


                    }

                });
            }

        }, 0, 1000000);

    }


}
