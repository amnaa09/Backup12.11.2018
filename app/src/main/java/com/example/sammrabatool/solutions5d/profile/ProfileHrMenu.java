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
import android.widget.ImageView;
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
//import com.example.sammrabatool.solutions5d.dialog.DialogAddReview;

import com.example.sammrabatool.solutions5d.leave.LeaveHr;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class ProfileHrMenu extends AppCompatActivity {
    FloatingActionButton myprofile, document, leave, request, payslip;
    FloatingActionButton basic;
    int lg, bg;
    boolean user_valid;
    ImageView profimg;
    TextView pname,yos, bsalary;

    String instanceStr, message, userID, token, profile, text;
    String    personId,yearofservice, basicsalary,picture,name,employee_name, employee_number,employee_heading, hireDate,hiredate_heading,gender_heading, gender,martial_heading, dob,dob_heading,email_heading, maritalStatus, nationality,nationality_heading,
            email, officeNum,officeNum_heading, org,org_heading, job,job_heading, grade,grade_heading, location,location_heading, status,status_heading, manager,manager_heading, passNum,passnum_heading, passIssue,passissue_heading, passexpire,passexpire_heading, visaNum,visanum_heading, visaIssue,visaissue_heading, visaExpire,visaexpire_heading, laborNum,labornum_heading,
            laborIssue,laborissue_heading, laborExpire,laborexpire_heading, nationalId,nationalid_heading, nationalIdIssue,nationalIDissue_heading, nationalIdExpire,nationalIDexpire_heading, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hr_menu);
        myprofile = (FloatingActionButton) findViewById(R.id.basicc);
        profimg = (ImageView) findViewById(R.id.image);
       // Basic = (FloatingActionButton) findViewById(R.id.basic);
        document = (FloatingActionButton) findViewById(R.id.employeemnt);
        leave = (FloatingActionButton) findViewById(R.id.passport);
        request = (FloatingActionButton) findViewById(R.id.visa);
        payslip = (FloatingActionButton) findViewById(R.id.national);
     //   Labor = (FloatingActionButton) findViewById(R.id.labor);
        pname=(TextView) findViewById(R.id.profile_name);
        yos=(TextView) findViewById(R.id.yos);
        bsalary=(TextView) findViewById(R.id.bsalary);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        name=getIntent().getStringExtra("name");
      //  pname.setText(name);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);


        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/gethrProfiledata/" + bg + "/" + userID + "/" + token;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if (user_valid == true) {
                        message = data.getString("message");
                        userID = data.getString("user_id");
                        profile = data.getString("profile");
                        JSONObject dataDetails = new JSONObject(profile.toString());

                        if (dataDetails.getString("personId").equals("")) {

                            personId = "0";


                        } else {
                            personId =  dataDetails.getString("personId");

                            //employee_p.setText(text);
                        }

                        if (dataDetails.getString("yearofservice").equals("")) {

                            yearofservice = "0";
                            yos.setText(yos.getText()+yearofservice);

                        } else {
                            yearofservice =  dataDetails.getString("yearofservice");
                            yos.setText(yos.getText()+yearofservice);
                            //employee_p.setText(text);
                        }

                        if (dataDetails.getString("basicsalary").equals("")) {

                            basicsalary = "0";
                            bsalary.setText(bsalary.getText()+basicsalary);

                        } else {
                            basicsalary =  dataDetails.getString("basicsalary");
                            bsalary.setText(bsalary.getText()+basicsalary);
                            //employee_p.setText(text);
                        }

                        if (dataDetails.getString("employeeName").equals("")) {

                            employee_name = "Unknown";
                            pname.setText(employee_name);

                        } else {
                            employee_name =  dataDetails.getString("employeeName");
                            pname.setText(employee_name);
                            //employee_p.setText(text);
                        }
                        if (dataDetails.getString("employeeNo").equals("")) {

                            employee_number = "--";

                        } else {
                            employee_number =  dataDetails.getString("employeeNo");
                            //employee_p.setText(text);
                        }
                        hiredate_heading="<b>" + "Hire Date: " + "</b> ";
                        if (dataDetails.getString("hireDate").equals("")) {
                            hireDate =   "--";
                        }
                        // hire_date.setText("Unknown");
                        else {
                            hireDate = dataDetails.getString("hireDate");

                        }
                        gender_heading="<b>" + "Gender: " + "</b> ";
                        if (dataDetails.getString("gender").equals("")) {
                            gender =   "--";

                        } else {
                            gender = dataDetails.getString("gender");

                        }
                        dob_heading="<b>" + "Date Of Birth: " + "</b> ";
                        if (dataDetails.getString("dateOfBirth").equals("")) {
                            dob =   "--";

                        } else {
                            dob = dataDetails.getString("dateOfBirth");

                        }
                        martial_heading="<b>" + "Marital Status: " + "</b> ";
                        if (dataDetails.getString("maritalStatus").equals("")) {
                            maritalStatus =  "--";

                        } else {
                            maritalStatus =  dataDetails.getString("maritalStatus");

                        }
                        nationality_heading= "<b>" + "Nationality:" + "</b> ";
                        if (dataDetails.getString("nationality").equals("")) {
                            nationality =   "--";

                        } else {
                            nationality =  dataDetails.getString("nationality");

                        }
                        email_heading="<b>" + "Email: " + "</b> ";
                        if (dataDetails.getString("email").equals("")) {
                            email =   "--";

                        } else {
                            email =  dataDetails.getString("email");

                        }
                        officeNum_heading="<b>" + "Phone No:" + "</b> ";
                        if (dataDetails.getString("officeNumber").equals("")) {
                            officeNum =   "--";

                        } else {
                            officeNum =  dataDetails.getString("officeNumber");

                        }
                        org_heading= "<b>" + "Organization:" + "</b> ";
                        if (dataDetails.getString("organization").equals("")) {
                            org =  "--";

                        } else {
                            org = dataDetails.getString("organization");

                        }
                        job_heading="<b>" + "Job:" + "</b> ";
                        if (dataDetails.getString("job").equals("")) {
                            job = "Unknown";

                        } else {
                            job = dataDetails.getString("job");

                        }
                        grade_heading="<b>" + "Grade :" + "</b> ";
                        if (dataDetails.getString("grade").equals("")) {
                            grade =   "--";

                        } else {
                            grade = dataDetails.getString("grade");

                        }
                        location_heading="<b>" + "Location :" + "</b> ";
                        if (dataDetails.getString("location").equals("")) {
                            location =  "--";

                        } else {
                            location =  dataDetails.getString("location");

                        }
                        status_heading= "<b>" + "Status :" + "</b> ";
                        if (dataDetails.getString("status").equals("")) {
                            status =   "unknown";

                        } else {
                            status = dataDetails.getString("status");

                        }
                        manager_heading= "<b>" + "Manager :" + "</b> ";
                        if (dataDetails.getString("manager").equals("")) {
                            manager =  "unknown";

                        } else {
                            manager =  dataDetails.getString("manager");

                        }
                        passnum_heading= "<b>" + "PassPort No :" + "</b> ";
                        if (dataDetails.getString("passportno").equals("")) {
                            passNum =   "--";

                        } else {
                            passNum =  dataDetails.getString("passportno");

                        }

                        passissue_heading= "<b>" + "PassPort Issuance :" + "</b> ";
                        if (dataDetails.getString("passport_issuance").equals("")) {
                            passIssue =  "--";

                        } else {
                            passIssue = dataDetails.getString("passport_issuance");

                        }
                        passexpire_heading= "<b>" + "PassPort Expiry :" + " </b>";
                        if (dataDetails.getString("passport_expiry").equals("")) {
                            passexpire =  "--";

                        } else {
                            passexpire =  dataDetails.getString("passport_expiry");

                        }
                        visanum_heading= "<b>" + "Visa No :" + " </b>";
                        if (dataDetails.getString("visano").equals("")) {
                            visaNum =  "--";

                        } else {
                            visaNum =  dataDetails.getString("visano");

                        }
                        visaissue_heading= "<b>" + "Visa Issuance :" + " </b>";
                        if (dataDetails.getString("visa_issuance").equals("")) {
                            visaIssue =   "--";

                        } else {
                            visaIssue = dataDetails.getString("visa_issuance");

                        }
                        visaexpire_heading="<b>" + "Visa Expiry :" + " </b>";
                        if (dataDetails.getString("visa_expiry").equals("")) {
                            visaExpire =  "--";

                        } else {
                            visaExpire = dataDetails.getString("visa_expiry");

                        }
                        labornum_heading= "<b>" + "Labour No :" + " </b>";
                        if (dataDetails.getString("labourno").equals("")) {
                            laborNum =   "--";

                        } else {
                            laborNum = dataDetails.getString("labourno");

                        }
                        laborissue_heading= "<b>" + "Labour Issuance :" + " </b>";
                        if (dataDetails.getString("labour_issuance").equals("")) {
                            laborIssue =  "--";

                        } else {
                            laborIssue =  dataDetails.getString("labour_issuance");
                        }
                        laborexpire_heading= "<b>" + "Labour Expiry :" + " </b>";
                        if (dataDetails.getString("labour_expiry").equals("")) {
                            laborExpire =  "--";

                        } else {
                            laborExpire =  dataDetails.getString("labour_expiry");

                        }
                        nationalid_heading= "<b>" + "National Id No :" + " </b>";
                        if (dataDetails.getString("nIdno").equals("")) {
                            nationalId =  "--";

                        } else {
                            nationalId =  dataDetails.getString("nIdno");

                        }
                        nationalIDissue_heading= "<b>" + "National Id Issuance :" + " </b>";
                        if (dataDetails.getString("nId_issuance").equals("")) {
                            nationalIdIssue = "--";

                        } else {
                            nationalIdIssue = dataDetails.getString("nId_issuance");

                        }
                        nationalIDexpire_heading= "<b>" + "National Id Expiry :" + " </b>";
                        if (dataDetails.getString("nId_expiry").equals("")) {
                            nationalIdExpire =   "--";

                        } else {
                            nationalIdExpire = dataDetails.getString("nId_expiry");

                        }
                        int SDK_INT = Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here
                            try {
                                URL url = new URL(dataDetails.getString("pic"));
                                picture=dataDetails.getString("pic");
                          //      Toast.makeText(ProfileFabMenu.this, "picccc", Toast.LENGTH_SHORT).show();

                                Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profimg);

                            } catch (MalformedURLException error) {
                                Toast.makeText(ProfileHrMenu.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                        message = data.getString("message");
                        Toast.makeText(ProfileHrMenu.this, message, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(ProfileHrMenu.this, LoginCardOverlap.class);
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
                Toast.makeText(ProfileHrMenu.this, message, Toast.LENGTH_SHORT).show();
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
        Intent i=new Intent(ProfileHrMenu.this, hrProfile1.class);
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

        startActivity(i);

            }
        });

        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    showCustomDialog(ProfileFabMenu.this, "Employment information", employee_heading,employee_number, location_heading,location,status_heading, status, org_heading,org, manager_heading,manager, job_heading, job, grade_heading, grade, "", "", "", "","","",picture);
                Intent i=new Intent(ProfileHrMenu.this, hrProfile2.class);
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
                startActivity(i);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   showCustomDialog(ProfileFabMenu.this, "Passport information", passnum_heading,passNum, passissue_heading,passIssue, passexpire_heading, passexpire, "", "", "", "", "", "", "", "", "", "","","","","",picture);
                Intent intent=new Intent(ProfileHrMenu.this, LeaveHr.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("lg",lg);
                intent.putExtra("bg",bg);
                intent.putExtra("personId", personId);
                intent.putExtra("employee_name", employee_name);
                intent.putExtra("pic",picture);
                startActivity(intent);

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
            getSupportActionBar().setTitle("My Information");
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
                Intent intent = new Intent(ProfileHrMenu.this, LoginCardOverlap.class);
                startActivity(intent);
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    }

