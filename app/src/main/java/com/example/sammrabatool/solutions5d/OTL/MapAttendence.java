package com.example.sammrabatool.solutions5d.OTL;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.sammrabatool.solutions5d.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MapAttendence extends FragmentActivity implements
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    Location loc=null;
    Double projectLat, projectLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_attendence);

        projectLong=getIntent().getDoubleExtra("projectLong",0);
        projectLat=getIntent().getDoubleExtra("projectLat",0);

        // Toolbar tb = findViewById(R.id.toolbar);
        //  (AppCompatActivity)getApplication().setSupportActionBar(tb);
        // tb.setSubtitle("Your Location");
        Button confirmLocation= (Button) findViewById(R.id.confirmLocation);
        confirmLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loc!=null)
                {
                    Calendar cal = Calendar.getInstance();
                    TimeZone tz = cal.getTimeZone();
                    Date d = new Date(loc.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/ kk:mm:ss");
                    sdf.setTimeZone(tz);
                    String time= sdf.format(d);
                    Toast.makeText(MapAttendence.this, "time="+loc.getLongitude(), Toast.LENGTH_LONG).show();
                    double lo=loc.getLongitude();
                    double la=loc.getLatitude();
                    Intent intent=new Intent(MapAttendence.this, CheckIn.class);
                    intent.putExtra("Longitude", lo);
                    intent.putExtra("Latitude", la);
                    intent.putExtra("Attendence", 1);
                    intent.putExtra("Time", time);
                    startActivity(intent);


                }
                else
                {
                    Toast.makeText(MapAttendence.this, "Please specify your location on the map", Toast.LENGTH_SHORT).show();
                }

            }
        });
        boolean flag;

        flag = displayGpsStatus();
        if (flag) {

            //   Log.v(TAG, "onClick");

            //    editLocation.setText("Please!! move your device to"+
            //           " see the changes in coordinates."+"\nWait..");

//            pb.setVisibility(View.VISIBLE);
            //   locationListener = new MyLocationListener();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                //  googleMap.setMyLocationEnabled(true);
                //  googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                //    locationMangaer.requestLocationUpdates(LocationManager
                //          .GPS_PROVIDER, 5000, 10,locationListener);


            } else {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }

            //   locationMangaer.requestLocationUpdates(LocationManager
            //         .GPS_PROVIDER, 5000, 10,locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.current_location);
        mapFragment.getMapAsync(this);


    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }


    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(12);

                    Location location =getLocation();
                    if(location!=null) {
                        //        Toast.makeText(MapAttendence.this, "longitude: "+location.getLongitude()+"latitude: "+location.getLatitude(), Toast.LENGTH_SHORT).show();
                        MarkerOptions markerOptions = new MarkerOptions();

                        MarkerOptions markerOptions2 = new MarkerOptions();
                        CircleOptions circleOptions = new CircleOptions();
                        CircleOptions circleOptions2 = new CircleOptions();


                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng latLng2 = new LatLng(projectLat,projectLong );

                    markerOptions.position(latLng);
                    markerOptions2.position(latLng2);


                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                    circleOptions2.center(new LatLng(projectLat,projectLong ));

                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(500);
                    circleOptions2.radius(500);

                    circleOptions.fillColor(0x50DC143C);
                    circleOptions2.fillColor(0x5032CD32);

                    circleOptions.strokeWidth(3);

                    circleOptions2.strokeWidth(3);

                    mMap.addCircle(circleOptions2);
                    mMap.addMarker(markerOptions2.title("Project Location").visible(true)).showInfoWindow();
                    mMap.addCircle(circleOptions);
                    //  mMap.addMarker(markerOptions);
                    mMap.addMarker(markerOptions.title("Your Location").visible(true)).showInfoWindow();

                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), projectLat, projectLong, results);
                    float distanceInMeters = results[0];
                        Toast.makeText(MapAttendence.this, "result="+distanceInMeters, Toast.LENGTH_SHORT).show();
                    boolean within500m = distanceInMeters < 500;
                    if (within500m) {
                        Toast.makeText(MapAttendence.this, "present", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MapAttendence.this, "absent", Toast.LENGTH_SHORT).show();
                    }


                    }
                        return false;

                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    loc=location;


                    mMap.setMinZoomPreference(12);

                    MarkerOptions markerOptions = new MarkerOptions();

                    MarkerOptions markerOptions2 = new MarkerOptions();
                    CircleOptions circleOptions = new CircleOptions();
                    CircleOptions circleOptions2 = new CircleOptions();


                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng latLng2 = new LatLng(33.5342, 73.1128);

                    markerOptions.position(latLng);
                    markerOptions2.position(latLng2);


                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                    circleOptions2.center(new LatLng(33.5342, 73.1128));

                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(500);
                    circleOptions2.radius(500);

                    circleOptions.fillColor(0x50DC143C);
                    circleOptions2.fillColor(0x5032CD32);

                    circleOptions.strokeWidth(3);

                    circleOptions2.strokeWidth(3);

                    //        Toast.makeText(MainActivity.this, "longitude: "+location.getLongitude()+"latitude: "+location.getLatitude(), Toast.LENGTH_SHORT).show();

               /*     IconGenerator iconFactory = new IconGenerator(MainActivity.this);


                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Your Location"))).
                            position(latLng).
                            anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                    markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Project Location"))).
                            position(latLng2).
                            anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());  */

                    mMap.addCircle(circleOptions);
                    //  mMap.addMarker(markerOptions);
                    mMap.addMarker(markerOptions.title("Your Location").visible(true)).showInfoWindow();

                    mMap.addCircle(circleOptions2);
                    mMap.addMarker(markerOptions2.title("Project Location").visible(true)).showInfoWindow();
                    //  mMap.addMarker(markerOptions2);


                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), 33.5352, 73.1108, results);
                    float distanceInMeters = results[0];
                    //    Toast.makeText(MainActivity.this, "result="+distanceInMeters, Toast.LENGTH_SHORT).show();
                    boolean within500m = distanceInMeters < 500;
                    if (within500m) {
                        Toast.makeText(MapAttendence.this, "present", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MapAttendence.this, "absent", Toast.LENGTH_SHORT).show();
                    }
                }
            };

    private Location getLocation() {
        boolean flag;

        flag = displayGpsStatus();
        if (flag) {

            //   Log.v(TAG, "onClick");

            //    editLocation.setText("Please!! move your device to"+
            //           " see the changes in coordinates."+"\nWait..");

//            pb.setVisibility(View.VISIBLE);
            //   locationListener = new MyLocationListener();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                //  googleMap.setMyLocationEnabled(true);
                //  googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                //    locationMangaer.requestLocationUpdates(LocationManager
                //          .GPS_PROVIDER, 5000, 10,locationListener);


            } else {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }

            //   locationMangaer.requestLocationUpdates(LocationManager
            //         .GPS_PROVIDER, 5000, 10,locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        return locationManager.getLastKnownLocation(locationManager.getBestProvider(c, false));
    }




}
