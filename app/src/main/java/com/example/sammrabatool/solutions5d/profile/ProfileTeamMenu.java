package com.example.sammrabatool.solutions5d.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;

import com.example.sammrabatool.solutions5d.leave.LeaveHr;
import com.example.sammrabatool.solutions5d.leave.LeaveTeam;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.example.sammrabatool.solutions5d.dialog.DialogAddReview;


public class ProfileTeamMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FloatingActionButton myprofile, document, leave, request, payslip;

    int lg, bg;
    boolean user_valid;
    boolean selected=false;
    ImageView profimg;
    TextView pname, position,directcount, totalcount;
    Spinner emplist;
    List<String> listemp = new ArrayList<String>();
    HashMap<Integer,String> hashSpinnerEmp = new HashMap<Integer, String>();
    String instanceStr, message, userID, token, profile, text;
    String basicsalary,yearofservice,name,personId,selected_empid="", employee_name,employee_position,direct, total, employee_number, hireDate, gender, dob, maritalStatus, nationality,
            email, officeNum, org, job, grade, location, status, manager, passNum, passIssue, passexpire, visaNum, visaIssue, visaExpire, laborNum,
            laborIssue, laborExpire, nationalId, nationalIdIssue, nationalIdExpire, pic;
    JSONArray empArray;
    JSONObject empdetail[];
    String teamEmpid, teamEmpname, teamEmpfname, teamEmppic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_team_menu);

        profimg = (ImageView) findViewById(R.id.image);
        myprofile = (FloatingActionButton) findViewById(R.id.teamprofile);
        document = (FloatingActionButton) findViewById(R.id.teamdoc);
        leave = (FloatingActionButton) findViewById(R.id.teamleave);
        request = (FloatingActionButton) findViewById(R.id.teamrequest);
        payslip = (FloatingActionButton) findViewById(R.id.teampay);
     //   Labor = (FloatingActionButton) findViewById(R.id.labor);
        pname=(TextView) findViewById(R.id.team_name);
        position=(TextView) findViewById(R.id.team_position);
        directcount=(TextView) findViewById(R.id.team_direct);
        totalcount=(TextView) findViewById(R.id.team_total);
        emplist=(Spinner) findViewById(R.id.teamSpinner);

        listemp.add("Select Employee");
        hashSpinnerEmp.put(0,"0");

        emplist.setOnItemSelectedListener(ProfileTeamMenu.this);

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listemp);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        emplist.setAdapter(typeAdapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        name=getIntent().getStringExtra("name");
    //    pname.setText(name);

        //--------------------getting profile info webservice---------------------------------------------
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getmyteamProfiledetail/" + bg + "/" + userID + "/" + token;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if (user_valid == true) {
                        message = data.getString("message");
                        userID = data.getString("user_id");
                        if( !data.isNull("profile")) {
                            profile = data.getString("profile");
                            JSONObject dataDetails = new JSONObject(profile.toString());


                            pname=(TextView) findViewById(R.id.team_name);
                            position=(TextView) findViewById(R.id.team_position);
                            directcount=(TextView) findViewById(R.id.team_direct);
                            totalcount=(TextView) findViewById(R.id.team_total);


                            if (dataDetails.getString("empname").equals("")) {

                                employee_name = "--";
                                pname.setText(employee_name);

                            } else {
                                employee_name = dataDetails.getString("empname");
                                pname.setText(employee_name);
                            }

                            if (dataDetails.getString("position").equals("")) {

                                employee_position = "--";
                                position.setText(employee_position);

                            } else {
                                employee_position = dataDetails.getString("position");
                                position.setText(employee_position);
                            }
                            if (dataDetails.getString("direct").equals("")) {

                                direct = "0";
                                String temp=directcount.getText().toString();
                                directcount.setText(temp+direct);

                            } else {
                                direct = dataDetails.getString("direct");
                                String temp=directcount.getText().toString();
                                directcount.setText(temp+direct);
                            }
                            if (dataDetails.getString("total").equals("")) {

                                total = "0";
                                String temp=totalcount.getText().toString();
                                totalcount.setText(temp+total);

                            } else {
                                total = dataDetails.getString("total");
                                String temp=totalcount.getText().toString();
                                totalcount.setText(temp+total);

                            }


                            int SDK_INT = Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                //your codes here
                                    // URL url = new URL(dataDetails.getString("pic"));
                                    //     team)pic=dataDetails.getString("pic");
                                    //      Toast.makeText(ProfileFabMenu.this, "picccc", Toast.LENGTH_SHORT).show();

                                    Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profimg);


                            }
                        }

                        if( !data.isNull("emp")) {
                            try {


                                if (data.getJSONArray("emp") != null && data.getJSONArray("emp").length() > 0) {

                                    empArray = data.getJSONArray("emp");
                                    //  if(otlPorjectArray!=null) {
                                    empdetail = new JSONObject[empArray.length()];
                                    for (int i = 0; i < empArray.length(); i++) {
                                        empdetail[i] = empArray.getJSONObject(i);
                                        teamEmpid = empdetail[i].getString("id");
                                        teamEmpname = empdetail[i].getString("name");
                                        teamEmpfname = empdetail[i].getString("fullname");
                                        teamEmppic = empdetail[i].getString("pic");
                                        listemp.add(teamEmpname);
                                        hashSpinnerEmp.put(i+1,teamEmpid);
                                    }
                                    //-------setting data to spinner----------------
                                    ArrayAdapter typeAdapter = new ArrayAdapter(ProfileTeamMenu.this, android.R.layout.simple_spinner_item, listemp);
                                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    emplist.setAdapter(typeAdapter);
                                    //--------------------------------------------------------------------------
                                }
                            }
                            catch(JSONException e)
                            {

                            }


                        }
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                        message = data.getString("message");
                        Toast.makeText(ProfileTeamMenu.this, message, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(ProfileTeamMenu.this, LoginCardOverlap.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                Toast.makeText(ProfileTeamMenu.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.hide();
            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);


        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();


        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(ProfileFabMenu.this, "employee num: " + employee_number, Toast.LENGTH_SHORT).show();
             //   showCustomDialog(ProfileFabMenu.this, "Basic information" ,employee_heading,employee_number, gender_heading,gender, martial_heading,maritalStatus, email_heading, email,hiredate_heading, hireDate,dob_heading, dob,nationality_heading, nationality,officeNum_heading, officeNum,"", "", "", "",picture);
            if(selected==true) {
                Intent i = new Intent(ProfileTeamMenu.this, teamProfile1.class);
                i.putExtra("name", employee_name);
                i.putExtra("gender", gender);
                i.putExtra("maritalStatus", maritalStatus);
                i.putExtra("email", email);
                i.putExtra("hireDate", hireDate);
                i.putExtra("dob", dob);
                i.putExtra("nationality", nationality);
                i.putExtra("officeNum", officeNum);
                i.putExtra("employee_number", employee_number);
                i.putExtra("manager", manager);
                i.putExtra("job", job);
                i.putExtra("grade", grade);
                i.putExtra("org", org);
                i.putExtra("location", location);
                i.putExtra("status", status);
                i.putExtra("yearofservice", yearofservice);
                i.putExtra("basicsalary", basicsalary);
                i.putExtra("pic", pic);
                startActivity(i);
            }
            else
                Toast.makeText(ProfileTeamMenu.this, "Please select any employee from the list", Toast.LENGTH_SHORT).show();

            }
        });

        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    showCustomDialog(ProfileFabMenu.this, "Employment information", employee_heading,employee_number, location_heading,location,status_heading, status, org_heading,org, manager_heading,manager, job_heading, job, grade_heading, grade, "", "", "", "","","",picture);
                if (selected == true){
                    Intent i = new Intent(ProfileTeamMenu.this, teamProfile2.class);
                i.putExtra("passNum", passNum);
                i.putExtra("passIssue", passIssue);
                i.putExtra("passexpire", passexpire);
                i.putExtra("visaNum", visaNum);
                i.putExtra("visaIssue", visaIssue);
                i.putExtra("visaExpire", visaExpire);
                i.putExtra("nationalId", nationalId);
                i.putExtra("nationalIdIssue", nationalIdIssue);
                i.putExtra("nationalIdExpire", nationalIdExpire);
                i.putExtra("laborNum", laborNum);
                i.putExtra("laborIssue", laborIssue);
                i.putExtra("laborExpire", laborExpire);
                i.putExtra("yearofservice", yearofservice);
                i.putExtra("basicsalary", basicsalary);
                i.putExtra("pic", pic);
                i.putExtra("name", employee_name);
                startActivity(i);
            }
              else
                      Toast.makeText(ProfileTeamMenu.this, "Please select any employee from the list", Toast.LENGTH_SHORT).show();

        }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected==true) {
                    Intent intent = new Intent(ProfileTeamMenu.this, LeaveTeam.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);
                    intent.putExtra("instance", instanceStr);
                    intent.putExtra("lg", lg);
                    intent.putExtra("bg", bg);
                    intent.putExtra("personId", personId);
                    intent.putExtra("employee_name", employee_name);
                    intent.putExtra("pic", pic);
                    startActivity(intent);
                }
                  else
                    Toast.makeText(ProfileTeamMenu.this, "Please select any employee from the list", Toast.LENGTH_SHORT).show();


                }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    showCustomDialog(ProfileFabMenu.this, "Visa information",visanum_heading, visaNum, visaissue_heading,visaIssue, visaexpire_heading,visaExpire,"", "", "", "", "", "", "", "", "", "","","","","",picture);
            }
        });

        payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showCustomDialog(ProfileFabMenu.this, "National ID information", nationalid_heading,nationalId, nationalIDissue_heading,nationalIdIssue, nationalIDexpire_heading,nationalIdExpire ,"", "", "", "", "", "", "", "", "", "","","","","",picture);


            }
        });

/*        Labor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Labour Card information", labornum_heading,laborNum,laborissue_heading,laborIssue,laborexpire_heading, laborExpire, "", "", "", "", "", "", "", "", "", "","","","","",picture);


            }
        });*/

        initToolbar();
    }
      private void initToolbar () {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //  toolbar.setNavigationIcon(R.drawable.ic_menu);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("My Team");
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Tools.setSystemBarColor(this);
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_profile_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            } else if (id == R.id.logout) {
                Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_LONG).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(ProfileTeamMenu.this, LoginCardOverlap.class);
                startActivity(intent);
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
//----------------------------spinner functions-------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if (parent.getId() == R.id.teamSpinner && position != 0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selected_empid = hashSpinnerEmp.get(emplist.getSelectedItemPosition());
            selected=true;

            //-----------------------------------------------------------------------------------------------------------
            RequestQueue MyRequestQueue1 = Volley.newRequestQueue(this);
            String url1 = "http://" + instanceStr + ".5dsurf.com/app/webservice/gethrProfiledataspecific/" + bg + "/" + userID + "/" +selected_empid+"/"+ token;
            StringRequest MyStringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject data = new JSONObject(response.toString());
                        user_valid = data.getBoolean("valid_user");
                        if (user_valid == true) {
                            message = data.getString("message");
                            userID = data.getString("user_id");
                            if( !data.isNull("profile")) {
                                profile = data.getString("profile");
                                JSONObject dataDetails = new JSONObject(profile.toString());

                                if (dataDetails.getString("personId").equals("")) {

                                    personId = "--";

                                } else {
                                    personId = dataDetails.getString("personId");
                                    //employee_p.setText(text);
                                }

                                if (dataDetails.getString("employeeName").equals("")) {

                                    employee_name = "--";

                                } else {
                                    employee_name = dataDetails.getString("employeeName");
                                    //employee_p.setText(text);
                                }


                                if (dataDetails.getString("employeeNo").equals("")) {

                                    employee_number = "--";

                                } else {
                                    employee_number = dataDetails.getString("employeeNo");
                                    //employee_p.setText(text);
                                }
                                if (dataDetails.getString("hireDate").equals("")) {
                                    hireDate = "--";
                                }
                                // hire_date.setText("Unknown");
                                else {
                                    hireDate = dataDetails.getString("hireDate");

                                }
                                if (dataDetails.getString("gender").equals("")) {
                                    gender = "--";

                                } else {
                                    gender = dataDetails.getString("gender");

                                }
                                if (dataDetails.getString("dateOfBirth").equals("")) {
                                    dob = "--";

                                } else {
                                    dob = dataDetails.getString("dateOfBirth");

                                }
                                if (dataDetails.getString("maritalStatus").equals("")) {
                                    maritalStatus = "--";

                                } else {
                                    maritalStatus = dataDetails.getString("maritalStatus");

                                }
                                if (dataDetails.getString("nationality").equals("")) {
                                    nationality = "--";

                                } else {
                                    nationality = dataDetails.getString("nationality");

                                }
                                if (dataDetails.getString("email").equals("")) {
                                    email = "--";

                                } else {
                                    email = dataDetails.getString("email");

                                }

                                if (dataDetails.getString("officeNumber").equals("")) {
                                    officeNum = "--";

                                } else {
                                    officeNum = dataDetails.getString("officeNumber");

                                }
                                if (dataDetails.getString("organization").equals("")) {
                                    org = "--";

                                } else {
                                    org = dataDetails.getString("organization");

                                }
                                if (dataDetails.getString("job").equals("")) {
                                    job = "Unknown";

                                } else {
                                    job = dataDetails.getString("job");

                                }
                                if (dataDetails.getString("grade").equals("")) {
                                    grade = "--";

                                } else {
                                    grade = dataDetails.getString("grade");

                                }
                                if (dataDetails.getString("location").equals("")) {
                                    location = "--";

                                } else {
                                    location = dataDetails.getString("location");

                                }
                                if (dataDetails.getString("status").equals("")) {
                                    status = "unknown";

                                } else {
                                    status = dataDetails.getString("status");

                                }
                                if (dataDetails.getString("manager").equals("")) {
                                    manager = "unknown";

                                } else {
                                    manager = dataDetails.getString("manager");

                                }
                                if (dataDetails.getString("passportno").equals("")) {
                                    passNum = "--";

                                } else {
                                    passNum = dataDetails.getString("passportno");

                                }

                                if (dataDetails.getString("passport_issuance").equals("")) {
                                    passIssue = "--";

                                } else {
                                    passIssue = dataDetails.getString("passport_issuance");

                                }
                                if (dataDetails.getString("passport_expiry").equals("")) {
                                    passexpire = "--";

                                } else {
                                    passexpire = dataDetails.getString("passport_expiry");

                                }
                                if (dataDetails.getString("visano").equals("")) {
                                    visaNum = "--";

                                } else {
                                    visaNum = dataDetails.getString("visano");

                                }
                                if (dataDetails.getString("visa_issuance").equals("")) {
                                    visaIssue = "--";

                                } else {
                                    visaIssue = dataDetails.getString("visa_issuance");

                                }
                                if (dataDetails.getString("visa_expiry").equals("")) {
                                    visaExpire = "--";

                                } else {
                                    visaExpire = dataDetails.getString("visa_expiry");

                                }
                                if (dataDetails.getString("labourno").equals("")) {
                                    laborNum = "--";

                                } else {
                                    laborNum = dataDetails.getString("labourno");

                                }
                                if (dataDetails.getString("labour_issuance").equals("")) {
                                    laborIssue = "--";

                                } else {
                                    laborIssue = dataDetails.getString("labour_issuance");
                                }
                                if (dataDetails.getString("labour_expiry").equals("")) {
                                    laborExpire = "--";

                                } else {
                                    laborExpire = dataDetails.getString("labour_expiry");

                                }
                                if (dataDetails.getString("nIdno").equals("")) {
                                    nationalId = "--";

                                } else {
                                    nationalId = dataDetails.getString("nIdno");

                                }
                                if (dataDetails.getString("nId_issuance").equals("")) {
                                    nationalIdIssue = "--";

                                } else {
                                    nationalIdIssue = dataDetails.getString("nId_issuance");

                                }
                                if (dataDetails.getString("nId_expiry").equals("")) {
                                    nationalIdExpire = "--";

                                } else {
                                    nationalIdExpire = dataDetails.getString("nId_expiry");

                                }

                                if (dataDetails.getString("pic").equals("")) {
                                    pic = "--";

                                } else {
                                    pic = dataDetails.getString("pic");

                                }

                                if (dataDetails.getString("yearofservice").equals("")) {
                                    yearofservice = "--";

                                } else {
                                    yearofservice = dataDetails.getString("yearofservice");

                                }

                                if (dataDetails.getString("basicsalary").equals("")) {
                                    basicsalary = "--";

                                } else {
                                    basicsalary = dataDetails.getString("basicsalary");

                                }

                               /* int SDK_INT = Build.VERSION.SDK_INT;
                                if (SDK_INT > 8) {
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                            .permitAll().build();
                                    StrictMode.setThreadPolicy(policy);
                                    //your codes here
                                    try {
                                        URL url = new URL(dataDetails.getString("pic"));
                                        //      Toast.makeText(ProfileFabMenu.this, "picccc", Toast.LENGTH_SHORT).show();

                                        Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profimg);

                                    } catch (MalformedURLException error) {
                                        Toast.makeText(ProfileTeamMenu.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

                                    }

                                }*/
                            }
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                        } else {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            message = data.getString("message");
                            Toast.makeText(ProfileTeamMenu.this, message, Toast.LENGTH_LONG).show();

                            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(ProfileTeamMenu.this, LoginCardOverlap.class);
                            startActivity(intent);
                        }
                  /*  if(user_valid==true)
                    {
                        user_valid=false;
                        //==============shared pref
                        Intent intent=new Intent(HrProfile.this, HrProfile.class);
                        intent.putExtra("userID",userID);
                        intent.putExtra("token",token);
                        intent.putExtra("instance", instanceStr);
                        // startActivity(intent);
                    }
                    */


                    } catch (JSONException e) {
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
                    Toast.makeText(ProfileTeamMenu.this, message, Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                }
            });
            MyStringRequest1.setShouldCache(false);
            MyRequestQueue1.add(MyStringRequest1);


            progressDialog.setCancelable(false);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please wait");
            progressDialog.show();


        }
        else  if (parent.getId() == R.id.teamSpinner && position == 0) {
            selected=false;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //------------------------------------spinner function end--------------------------------
    }

