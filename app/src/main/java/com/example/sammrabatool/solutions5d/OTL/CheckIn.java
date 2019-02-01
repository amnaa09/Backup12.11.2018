package com.example.sammrabatool.solutions5d.OTL;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.UploadSignature;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CheckIn extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {


    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Location loc;
    double user_latitude;
    double user_longitude;
    protected LocationManager locationManager;
    TextView txt,empNameText;
    private static  int uploadCounter=0;
    Toolbar toolbar;
    Button in, out, checkin, test;
    CircularImageView map;
    int flagForCheckin=0;
    Spinner type, task, project, activity;
    List<String> listtype = new ArrayList<String>();
    List<String> listtask = new ArrayList<String>();
    List<String> listproject = new ArrayList<String>();
    List<String> listactivity = new ArrayList<String>();
    HashMap<Integer,String> hashSpinnerType = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerProject = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerTask = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerActivity = new HashMap<Integer, String>();
    //  public static final int GET_FROM_GALLERY = 3;
    private static int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog progressDialog ;
    boolean user_valid=false;
    String message;
    String instanceStr,  userID, token;
    int lg,bg;
    JSONObject otlProjectdetail[], otlTypedetail[], otlEmpDetail,otlTimeSheetDetail, otlProjectLocationDetail, otlTaskdetail[], otlActivitydetail[];
    JSONArray otlPorjectArray, otltypeArray, otlPorjectTaskArray, otlPorjectTaskActivityArray;
    String projecrID, projectName,timePeriodID,timePeriodCurrentMonth,timePeriodCurrentYear,timePeriodStartDate, timePeriodEndDate,typeId, typeName, empId,
            empName, empGrade, empOrg, empPic, selectedTypeID="0",selectedProjectID="0", taskID, taskName, selectedTaskID="0", activityID, activityName, selectedActivityID="0";
    ImageView profileImage;
    Double projectLong, projectLat;
    String locationBasedProject;
    private JSONObject jsonObject;
    private static int counterImages=0;
    private static int picCounter=0;
   public static Bitmap bm1,bm2,bm3,bm4,bm5,bm6,bm7;
    int attendence=0;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);

        user_longitude = getIntent().getDoubleExtra("Longitude", 0);
        user_latitude = getIntent().getDoubleExtra("Latitude", 0);
         attendence = getIntent().getIntExtra("Attendence", 0);
         time = getIntent().getStringExtra("Time");
        picCounter=0;
        counterImages=0;
        instanceStr=getIntent().getStringExtra("instance");
        userID=getIntent().getStringExtra("userID");
        token=getIntent().getStringExtra("token");
        lg=getIntent().getIntExtra("lg", 0);
        bg=getIntent().getIntExtra("bg", 0);

        jsonObject = new JSONObject();

        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt = (TextView) findViewById(R.id.date);
        empNameText=(TextView) findViewById(R.id.empName);
        in = (Button) findViewById(R.id.attendenceIn);
        out = (Button) findViewById(R.id.attendenceOut);
        map = (CircularImageView) findViewById(R.id.mapButton);
        checkin = (Button) findViewById(R.id.checkin);

        type=(Spinner) findViewById(R.id.spinnerType);
        task=(Spinner) findViewById(R.id.spinnerTask);
        project=(Spinner) findViewById(R.id.spinnerProject);
        activity=(Spinner) findViewById(R.id.spinnerActivity);
        profileImage=(ImageView) findViewById(R.id.profileImage);
//-----------------------------------setting spinners--------------------------------------
        listtype.add("Select type");
        hashSpinnerType.put(0, "0");
        listtask.add("Select task");
        hashSpinnerTask.put(0,"0");
        listproject.add("Select project");
        hashSpinnerProject.put(0,"0");
        listactivity.add("Select activity");
        hashSpinnerActivity.put(0,"0");

        type.setOnItemSelectedListener(this);
        task.setOnItemSelectedListener(this);
        project.setOnItemSelectedListener(this);
        activity.setOnItemSelectedListener(this);

        ArrayAdapter typeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listtype);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        type.setAdapter(typeAdapter);

        ArrayAdapter taskAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listtask);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        task.setAdapter(taskAdapter);

        ArrayAdapter projectAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listproject);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        project.setAdapter(projectAdapter);

        ArrayAdapter activityAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listactivity);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        activity.setAdapter(activityAdapter);

//-----------------------------------end setting spinners--------------------------------------
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txt.setText(currentdate);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView txt1 = (TextView) findViewById(R.id.date2);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = sdf.format(date);
                                txt1.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "map", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CheckIn.this, MapAttendence.class);
                  intent.putExtra("userID",userID);
                  intent.putExtra("token",token);
                  intent.putExtra("instance", instanceStr);
                  intent.putExtra("lg",lg);
                  intent.putExtra("bg",bg);
                intent.putExtra("projectLong",projectLong);
                intent.putExtra("projectLat",projectLat);
                startActivity(intent);

            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(CheckIn.this, "type="+selectedTypeID+"project="+selectedProjectID+"task="+selectedTaskID+"activity="+selectedActivityID, Toast.LENGTH_SHORT).show();
                //  Toast.makeText(MainActivity.this, "date="+new GetDateAndTime().execute(""), Toast.LENGTH_SHORT).show();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this, "outtttttttt", Toast.LENGTH_SHORT).show();
                if (flagForCheckin == 1) {
                    Intent intent = new Intent(CheckIn.this, MainActivityOut.class);
                    //  intent.putExtra("userID",userID);
                    //  intent.putExtra("token",token);
                    //  intent.putExtra("instance", instanceStr);
                    //  intent.putExtra("lg",lg);
                    //  intent.putExtra("bg",bg);
                    startActivity(intent);
                    flagForCheckin = 0;
                } else
                    Toast.makeText(CheckIn.this, "Cannot checkout now", Toast.LENGTH_SHORT).show();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new MyLocationListener()
            );
        }

      //  Toast.makeText(MainActivity.this, "back in hceckin", Toast.LENGTH_SHORT).show();
        checkin = (Button) findViewById(R.id.checkin);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckIn.this, "type="+selectedTypeID+"project="+selectedProjectID+"task="+selectedTaskID+"activity="+selectedActivityID, Toast.LENGTH_SHORT).show();

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                showCurrentLocation();
               // if(loc!=null)
              //  Toast.makeText(MainActivity.this, "location lat="+latitude, Toast.LENGTH_SHORT).show();
                flagForCheckin = 1;
                progressDialog.dismiss();


                //  Toast.makeText(MainActivity.this, "Longitude="+longitude+"Latitude="+latitude+"Time="+time, Toast.LENGTH_LONG).show();
             //   showOTLDialog(latitude, longitude);
            }

        });
//--------------------calling webservice-------------------------------


        RequestQueue MyRequestQueue = Volley.newRequestQueue(CheckIn.this);
        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getTimeAndAttendancePageOpeningDetail/" + bg + "/" + lg  + "/" + userID+"/"+token;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject data = new JSONObject(response.toString());

                   // user_valid = data.getBoolean("valid_user");
                  //  if(user_valid==true) {}
                  //  else {
                    if(data.getJSONArray("project")!=null){
                    otlPorjectArray=data.getJSONArray("project");
                  //  if(otlPorjectArray!=null) {
                        otlProjectdetail = new JSONObject[otlPorjectArray.length()];
                        for (int i = 0; i < otlPorjectArray.length(); i++) {
                            otlProjectdetail[i] = otlPorjectArray.getJSONObject(i);
                            projecrID = otlProjectdetail[i].getString("projecrID");
                            projectName = otlProjectdetail[i].getString("ProjectName");
                            listproject.add(projectName);
                            hashSpinnerProject.put(i + 1, projecrID);
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }

                        //-------setting data to spinner----------------
                        ArrayAdapter projectAdapter = new ArrayAdapter(CheckIn.this, android.R.layout.simple_spinner_item, listproject);
                        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        project.setAdapter(projectAdapter);
                        //--------------------------------------------------------------------------
                    }
                    if(data.getJSONArray("type")!=null){
                    otltypeArray=data.getJSONArray("type");
                   // if(otltypeArray!=null) {
                        otlTypedetail = new JSONObject[otltypeArray.length()];
                        for (int i = 0; i < otltypeArray.length(); i++) {
                            otlTypedetail[i] = otltypeArray.getJSONObject(i);
                            typeId = otlTypedetail[i].getString("id");
                            typeName = otlTypedetail[i].getString("name");
                            listtype.add(typeName);
                            hashSpinnerType.put(i + 1, typeId);
                        }
                        //-------setting data to spinner----------------
                        ArrayAdapter typeAdapter = new ArrayAdapter(CheckIn.this, android.R.layout.simple_spinner_item, listtype);
                        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        type.setAdapter(typeAdapter);
                        //--------------------------------------------------------------------------
                    }
                    otlEmpDetail=data.getJSONObject("employeeDetail");
                    if(otlEmpDetail!=null) {
                        empId = otlEmpDetail.getString("personID");
                        empName = otlEmpDetail.getString("employeeName");
                        empPic = otlEmpDetail.getString("employeePic");
                        //  Toast.makeText(CheckIn.this, "pic="+empPic, Toast.LENGTH_SHORT).show();

                        empNameText.setText(empName);
                        int SDK_INT = Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here
                            try {
                                //   URL url = new URL(data.getString(""));
                                //    Toast.makeText(ProfilePurple.this, "picccc", Toast.LENGTH_SHORT).show();

                                Picasso.get().load(otlEmpDetail.getString("employeePic")).transform(new CircleTransform()).into(profileImage);

                            } catch (JSONException error) {
                                Toast.makeText(CheckIn.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }

                    otlTimeSheetDetail=data.getJSONObject("timeSheetDetail");
                    if(otlTimeSheetDetail!=null) {
                        timePeriodID = otlTimeSheetDetail.getString("timePeriodID");
                        timePeriodCurrentMonth = otlTimeSheetDetail.getString("timePeriodCurrentMonth");
                        timePeriodCurrentYear = otlTimeSheetDetail.getString("timePeriodCurrentYear");
                        timePeriodStartDate = otlTimeSheetDetail.getString("timePeriodStartDate");
                        timePeriodEndDate = otlTimeSheetDetail.getString("timePeriodEndDate");
                   //     Toast.makeText(CheckIn.this, "timedetail="+timePeriodID+timePeriodCurrentMonth+timePeriodCurrentYear+timePeriodStartDate+timePeriodEndDate, Toast.LENGTH_SHORT).show();

                    }
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                     //   message = data.getString("message");
                       // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                   // }

                }
                catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(CheckIn.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(CheckIn.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
    //    progressDialog.show();

      //  Toast.makeText(this, "long="+projectLong+"lat="+projectLat, Toast.LENGTH_SHORT).show();

        //---------------------------end calling webservice--------------------
            }



   //----------------------spinner methods----------------
   //Performing action onItemSelected and onNothing selected
   @Override
   public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
       String text=parent.getItemAtPosition(position).toString();

       if (parent.getId() == R.id.spinnerType && position!=0) {
           selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());

       }
       if (parent.getId() == R.id.spinnerType && position==0) {
           selectedTypeID="0";

       }
       if (parent.getId() == R.id.spinnerProject && position==0) {
           selectedProjectID="0";

       }
       if (parent.getId() == R.id.spinnerProject && position!=0) {

     //      Toast.makeText(this, "id="+hashSpinnerProject.get(project.getSelectedItemPosition())+"name="+project.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//--------------------calling webservice for tasks-------------------------------

           selectedProjectID=hashSpinnerProject.get(project.getSelectedItemPosition());


           RequestQueue MyRequestQueue = Volley.newRequestQueue(CheckIn.this);
           String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getProjectTasks/" + bg + "/" + lg  + "/" + userID+"/"+token+"/"+selectedProjectID;
           StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

                   try {
                       JSONObject data = new JSONObject(response.toString());

                       listtask.clear();
                       hashSpinnerTask.clear();
                       listtask.add("Select task");
                       hashSpinnerTask.put(0,"0");

                       listactivity.clear();
                       hashSpinnerActivity.clear();
                       listactivity.add("Select activity");
                       hashSpinnerActivity.put(0,"0");

                        if(data.getString("tasks")!="null"){

                       otlPorjectTaskArray=data.getJSONArray("tasks");
                      // if(otlPorjectTaskArray!=null) {
                           otlTaskdetail = new JSONObject[otlPorjectTaskArray.length()];
                           for (int i = 0; i < otlPorjectTaskArray.length(); i++) {
                               otlTaskdetail[i] = otlPorjectTaskArray.getJSONObject(i);
                               taskID = otlTaskdetail[i].getString("TaskActivityid");
                               taskName = otlTaskdetail[i].getString("TaskActivityNAME");
                               listtask.add(taskName);
                               hashSpinnerTask.put(i + 1, taskID);
                               //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                           }

                           //-------setting data to spinner----------------
                        }

                           ArrayAdapter taskAdapter1 = new ArrayAdapter(CheckIn.this, android.R.layout.simple_spinner_item, listtask);
                            taskAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            task.setAdapter(taskAdapter1);
                            taskAdapter1.notifyDataSetChanged();

                       ArrayAdapter activityAdapter1 = new ArrayAdapter(CheckIn.this, android.R.layout.simple_spinner_item, listactivity);
                       activityAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       //Setting the ArrayAdapter data on the Spinner
                       activity.setAdapter(activityAdapter1);
                       activityAdapter1.notifyDataSetChanged();


                       otlProjectLocationDetail=data.getJSONObject("projectDetail");
                       if(otlProjectLocationDetail!=null) {
                          // if((otlProjectLocationDetail.getBoolean("locationBasedEntry"))=="")
                           if(otlProjectLocationDetail.getString("locationBasedEntry")!="")
                           locationBasedProject = otlProjectLocationDetail.getString("locationBasedEntry");
                           if(otlProjectLocationDetail.getString("latitude")!="null")
                           projectLat = otlProjectLocationDetail.getDouble("latitude");
                           if(otlProjectLocationDetail.getString("longitude")!="null")
                           projectLong = otlProjectLocationDetail.getDouble("longitude");

                         //  Toast.makeText(CheckIn.this, "long=" + projectLong + "lat=" + projectLat, Toast.LENGTH_SHORT).show();
                       }

                       if (progressDialog.isShowing())
                           progressDialog.hide();
                       //   message = data.getString("message");
                       // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                       // }

                   }
                   catch (JSONException e) {
                       if (progressDialog.isShowing())
                           progressDialog.hide();
                       e.printStackTrace();
                       //
                       //                            //  instance.setText("error= " + e.getMessage());
                       Toast.makeText(CheckIn.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
               @Override
               public void onErrorResponse(VolleyError error) {
                   //This code is executed if there is an error.
                   String message = null;
                   if (error instanceof NetworkError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof ServerError) {
                       message = "The server could not be found. Please try again after some time!!";
                   } else if (error instanceof AuthFailureError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof ParseError) {
                       message = "Parsing error! Please try again after some time!!";
                   } else if (error instanceof NoConnectionError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof TimeoutError) {
                       message = "Connection TimeOut! Please check your internet connection.";
                   }
                   Toast.makeText(CheckIn.this, message, Toast.LENGTH_SHORT).show();
               }
           });
           MyStringRequest.setShouldCache(false);
           MyRequestQueue.add(MyStringRequest);

           progressDialog.setCancelable(false);
           progressDialog.setTitle("Loading...");
           progressDialog.setMessage("Please wait");
           //    progressDialog.show();

           //---------------------------end calling webservice--------------------
       }

       if(parent.getId() == R.id.spinnerTask && position==0)
       {
           selectedTaskID="0";

       }
        if(parent.getId() == R.id.spinnerTask && position!=0)
       {
           //Toast.makeText(this, "id="+hashSpinnerTask.get(task.getSelectedItemPosition())+"name="+task.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//--------------------calling webservice for tasks-------------------------------

           selectedTaskID=hashSpinnerTask.get(task.getSelectedItemPosition());


           RequestQueue MyRequestQueue = Volley.newRequestQueue(CheckIn.this);
           String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getProjectTasksActivities/" + bg + "/" + lg  + "/" + userID+"/"+token+"/"+selectedTaskID;
           StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

                   try {
                       JSONObject data = new JSONObject(response.toString());

                       // user_valid = data.getBoolean("valid_user");
                       //  if(user_valid==true) {}
                       //  else {
                       listactivity.clear();
                       hashSpinnerActivity.clear();
                       listactivity.add("Select activity");
                       hashSpinnerActivity.put(0,"0");
                        if(data.getJSONArray("taskActivity")!=null){



                       otlPorjectTaskActivityArray=data.getJSONArray("taskActivity");
                      // if(otlPorjectTaskActivityArray!=null) {
                           otlActivitydetail = new JSONObject[otlPorjectTaskActivityArray.length()];
                           for (int i = 0; i < otlPorjectTaskActivityArray.length(); i++) {
                               otlActivitydetail[i] = otlPorjectTaskActivityArray.getJSONObject(i);
                               activityID = otlActivitydetail[i].getString("TaskActivityid");
                               activityName = otlActivitydetail[i].getString("TaskActivityNAME");
                               listactivity.add(activityName);
                               hashSpinnerActivity.put(i + 1, activityID);
                               //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                           }
                        }
                           //-------setting data to spinner----------------
                           ArrayAdapter activityAdapter = new ArrayAdapter(CheckIn.this, android.R.layout.simple_spinner_item, listactivity);
                           activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                           //Setting the ArrayAdapter data on the Spinner
                           activity.setAdapter(activityAdapter);



                       if (progressDialog.isShowing())
                           progressDialog.hide();
                       //   message = data.getString("message");
                       // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                       // }

                   }
                   catch (JSONException e) {
                       if (progressDialog.isShowing())
                           progressDialog.hide();
                       e.printStackTrace();
                       //
                       //                            //  instance.setText("error= " + e.getMessage());
                       Toast.makeText(CheckIn.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
               @Override
               public void onErrorResponse(VolleyError error) {
                   //This code is executed if there is an error.
                   String message = null;
                   if (error instanceof NetworkError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof ServerError) {
                       message = "The server could not be found. Please try again after some time!!";
                   } else if (error instanceof AuthFailureError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof ParseError) {
                       message = "Parsing error! Please try again after some time!!";
                   } else if (error instanceof NoConnectionError) {
                       message = "Cannot connect to Internet...Please check your connection!";
                   } else if (error instanceof TimeoutError) {
                       message = "Connection TimeOut! Please check your internet connection.";
                   }
                   Toast.makeText(CheckIn.this, message, Toast.LENGTH_SHORT).show();
               }
           });
           MyStringRequest.setShouldCache(false);
           MyRequestQueue.add(MyStringRequest);

           progressDialog.setCancelable(false);
           progressDialog.setTitle("Loading...");
           progressDialog.setMessage("Please wait");
           //    progressDialog.show();

           //---------------------------end calling webservice--------------------
       }
        if(parent.getId() == R.id.spinnerActivity && position!=0)
       {
           selectedActivityID=hashSpinnerActivity.get(activity.getSelectedItemPosition());
       }
       if(parent.getId() == R.id.spinnerActivity && position==0)
       {
           selectedActivityID="0";
       }

   }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    //----------------end spinner methods--------------------
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
    protected void showCurrentLocation() {

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


                //   locationMangaer.requestLocationUpdates(LocationManager
                  //        .GPS_PROVIDER, 5000, 10,locationListener);


            } else {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }

            //   locationMangaer.requestLocationUpdates(LocationManager
            //         .GPS_PROVIDER, 5000, 10,locationListener);


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        if (location != null) {
       //     String message = String.format(

          //  showOTLDialog(location.getLatitude(), location.g     "Current Location \n Longitude: %1$s \n Latitude: %2$s",
            //                    location.getLongitude(), location.getLatitude()
            //            );etLongitude());
            user_longitude=location.getLongitude();
            user_latitude=location.getLatitude();
    if(locationBasedProject=="true") {
        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), projectLat, projectLong, results);
        float distanceInMeters = results[0];
        Toast.makeText(CheckIn.this, "result=" + distanceInMeters, Toast.LENGTH_SHORT).show();
        boolean within500m = distanceInMeters < 500;
             if (within500m) {
                    Toast.makeText(CheckIn.this, "Location confirmed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckIn.this, OTLDialogActivity.class);
                    intent.putExtra("instanceStr", instanceStr);
                    intent.putExtra("lg", lg);
                    intent.putExtra("bg", bg);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);
                    intent.putExtra("timesheetID", timePeriodID);
                    intent.putExtra("personID", empId);
                    intent.putExtra("attendanceDate", time);
                    intent.putExtra("type", selectedTypeID);
                    intent.putExtra("project", selectedProjectID);
                    intent.putExtra("task", selectedTaskID);
                    intent.putExtra("activity", selectedActivityID);
                    intent.putExtra("checkinTime", time);
                    intent.putExtra("longitude", user_longitude);
                    intent.putExtra("latitude", user_latitude);
                    intent.putExtra("empName", empName);
                    intent.putExtra("empPic", empPic);
                    startActivity(intent);
                } else {
             Toast.makeText(CheckIn.this, "Cannot check in. You are too far from the project site.", Toast.LENGTH_SHORT).show();
            }
            }
            else
            {
                Intent intent = new Intent(CheckIn.this, OTLDialogActivity.class);
                intent.putExtra("instanceStr", instanceStr);
                intent.putExtra("lg", lg);
                intent.putExtra("bg", bg);
                intent.putExtra("userID", userID);
                intent.putExtra("token", token);
                intent.putExtra("timesheetID", timePeriodID);
                intent.putExtra("personID", empId);
                intent.putExtra("attendanceDate", time);
                intent.putExtra("type", selectedTypeID);
                intent.putExtra("project", selectedProjectID);
                intent.putExtra("task", selectedTaskID);
                intent.putExtra("activity", selectedActivityID);
                intent.putExtra("checkinTime", time);
                intent.putExtra("longitude", user_longitude);
                intent.putExtra("latitude", user_latitude);
                intent.putExtra("empName", empName);
                intent.putExtra("empPic", empPic);
                startActivity(intent);

            }
       //     Toast.makeText(MainActivity.this, message,
         //           Toast.LENGTH_LONG).show();
        }
        else {
           // Toast.makeText(this, "null otl", Toast.LENGTH_SHORT).show();
            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    if(location!=null) {
                        loc = location;
                        // getting location of user
                        user_latitude = loc.getLatitude();
                        user_longitude = loc.getLongitude();
                     //   Toast.makeText(CheckIn.this, "lat111111=" + latitude + "log=" + longitude, Toast.LENGTH_SHORT).show();
                    locationManager.removeUpdates(this);
                        if ( progressDialog.isShowing())
                            progressDialog.hide();

                        if(locationBasedProject=="true") {

                            float[] results = new float[1];
                            Location.distanceBetween(location.getLatitude(), location.getLongitude(), projectLat, projectLong, results);
                            float distanceInMeters = results[0];
                            Toast.makeText(CheckIn.this, "result=" + distanceInMeters, Toast.LENGTH_SHORT).show();
                            boolean within500m = distanceInMeters < 500;
                            if (within500m) {
                                Toast.makeText(CheckIn.this, "Location confirmed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CheckIn.this, OTLDialogActivity.class);
                                intent.putExtra("instanceStr", instanceStr);
                                intent.putExtra("lg", lg);
                                intent.putExtra("bg", bg);
                                intent.putExtra("userID", userID);
                                intent.putExtra("token", token);
                                intent.putExtra("timesheetID", timePeriodID);
                                intent.putExtra("personID", empId);
                                intent.putExtra("attendanceDate", time);
                                intent.putExtra("type", selectedTypeID);
                                intent.putExtra("project", selectedProjectID);
                                intent.putExtra("task", selectedTaskID);
                                intent.putExtra("activity", selectedActivityID);
                                intent.putExtra("checkinTime", time);
                                intent.putExtra("longitude", user_longitude);
                                intent.putExtra("latitude", user_latitude);
                                intent.putExtra("empName", empName);
                                intent.putExtra("empPic", empPic);
                                startActivity(intent);
                                //   finish();
                                //   showOTLDialog(latitude, longitude);
                            } else {
                                Toast.makeText(CheckIn.this, "Cannot check in. You are too far from the project site.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            {
                                Intent intent = new Intent(CheckIn.this, OTLDialogActivity.class);
                                intent.putExtra("instanceStr", instanceStr);
                                intent.putExtra("lg", lg);
                                intent.putExtra("bg", bg);
                                intent.putExtra("userID", userID);
                                intent.putExtra("token", token);
                                intent.putExtra("timesheetID", timePeriodID);
                                intent.putExtra("personID", empId);
                                intent.putExtra("attendanceDate", time);
                                intent.putExtra("type", selectedTypeID);
                                intent.putExtra("project", selectedProjectID);
                                intent.putExtra("task", selectedTaskID);
                                intent.putExtra("activity", selectedActivityID);
                                intent.putExtra("checkinTime", time);
                                intent.putExtra("longitude", user_longitude);
                                intent.putExtra("latitude", user_latitude);
                                intent.putExtra("empName", empName);
                                intent.putExtra("empPic", empPic);
                                startActivity(intent);
                            }
                    }
                    else
                        Toast.makeText(CheckIn.this, "location null", Toast.LENGTH_SHORT).show();
                    //do something with Lat and Lng
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                    //when user enables the GPS setting, this method is triggered.
                }

                @Override
                public void onProviderDisabled(String provider) {
                    //when no provider is available in this case GPS provider, trigger your gpsDialog here.
                    alertbox("Gps Status!!", "Your GPS is: OFF");
                }
            };

            //update location every 10sec in 500m radius with both provider GPS and Network.

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
           // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");

        }

    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
          //  Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
      //      Toast.makeText(MainActivity.this, "Provider status changed",
        //            Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(CheckIn.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(CheckIn.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    //    LayoutInflater inflater = LayoutInflater.from(CheckIn.this);
    //    final View yourCustomView = inflater.inflate(R.layout.dialog_otl_remarks, null);

    //    final ImageView imageview1=(ImageView) yourCustomView.findViewById(R.id.imageView_pic);
      /*  final ImageView imageview2=(ImageView) yourCustomView.findViewById(R.id.imageView_pic2);
        final ImageView imageview3=(ImageView) yourCustomView.findViewById(R.id.imageView_pic3);
        final ImageView imageview4=(ImageView) yourCustomView.findViewById(R.id.imageView_pic4);
        final ImageView imageview5=(ImageView) yourCustomView.findViewById(R.id.imageView_pic5);
        final ImageView imageview6=(ImageView) yourCustomView.findViewById(R.id.imageView_pic6);
        final ImageView imageview7=(ImageView) yourCustomView.findViewById(R.id.imageView_pic7);*/
   //   final EditText ed=(EditText) yourCustomView.findViewById(R.id.et_post);
   //   ed.setText("helloooo");

      //ImageView imageview1=findViewById(R.id.imageView_pic);

      /*  if (requestCode == Utils.REQCODE && resultCode == RESULT_OK && data != null) {
        picCounter++;
        try {
            if (picCounter == 1) {
                bm1 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
               //  imageview1.setVisibility(View.VISIBLE);
                // imageview1.setImageBitmap(bm1);
           //     Toast.makeText(this, "visible1", Toast.LENGTH_SHORT).show();
            } else if (picCounter == 2)
                bm2 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview2.setVisibility(View.VISIBLE);
            else if (picCounter == 3)
                bm3 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview3.setVisibility(View.VISIBLE);
            else if (picCounter == 4)
                bm4 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview4.setVisibility(View.VISIBLE);
            else if (picCounter == 5)
                bm5 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview5.setVisibility(View.VISIBLE);
            else if (picCounter == 6)
                bm6 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview6.setVisibility(View.VISIBLE);
            else if (picCounter == 7)
                bm7 = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//            imageview7.setVisibility(View.VISIBLE);
        }catch (IOException e)
        {

        }

        Uri selectedImageUri=null;


            selectedImageUri = data.getData();
            String [] proj={MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery( selectedImageUri,
                    proj, // Which columns to return
                    null, // WHERE clause; which rows to return (all rows)
                    null, // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s= cursor.getString(column_index);
            String ImName = s.substring(s.lastIndexOf("/") + 1);

/*

try {
    Bitmap image1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
    //   Bitmap image1 = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
    dialog.show();
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
    try {
        jsonObject.put(Utils.imageName, ImName);
        Log.e("Image name", etxtUpload.getText().toString().trim());
        jsonObject.put(Utils.image, encodedImage1);
    } catch (JSONException e) {
        Log.e("JSONObject Here", e.toString());
    }
    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, Utils.urlUpload, jsonObject,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.e("Message from server", jsonObject.toString());
                    dialog.dismiss();
                    messageText.setText("Image Uploaded Successfully");
                    Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("Message from server:   ", volleyError.toString());
            messageText.setText("Message from server:   " + volleyError.toString());
            dialog.dismiss();
        }
    });
    jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    Volley.newRequestQueue(this).add(jsonObjectRequest1);
}
catch (IOException e)
{
    Toast.makeText(this, "Error uploading image: Please select an image first.", Toast.LENGTH_SHORT).show();
}

        }
    }  */
}


