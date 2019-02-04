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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.example.sammrabatool.solutions5d.list.ViewHtml;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class ProfileFabMenu extends AppCompatActivity {
    FloatingActionButton Basic, Employee, Passport, Visa, National, Labor;
    FloatingActionButton basic;
    int lg, bg;
    boolean user_valid;
    ImageView profimg;
    TextView pname;

    String instanceStr, message, userID, token, profile, text;
    String picture,name, employee_number,employee_heading, hireDate,hiredate_heading,gender_heading, gender,martial_heading, dob,dob_heading,email_heading, maritalStatus, nationality,nationality_heading,
            email, officeNum,officeNum_heading, org,org_heading, job,job_heading, grade,grade_heading, location,location_heading, status,status_heading, manager,manager_heading, passNum,passnum_heading, passIssue,passissue_heading, passexpire,passexpire_heading, visaNum,visanum_heading, visaIssue,visaissue_heading, visaExpire,visaexpire_heading, laborNum,labornum_heading,
            laborIssue,laborissue_heading, laborExpire,laborexpire_heading, nationalId,nationalid_heading, nationalIdIssue,nationalIDissue_heading, nationalIdExpire,nationalIDexpire_heading, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fab_menu);
        basic = (FloatingActionButton) findViewById(R.id.basicc);
        profimg = (ImageView) findViewById(R.id.image);
       // Basic = (FloatingActionButton) findViewById(R.id.basic);
        Employee = (FloatingActionButton) findViewById(R.id.employeemnt);
        Passport = (FloatingActionButton) findViewById(R.id.passport);
        Visa = (FloatingActionButton) findViewById(R.id.visa);
        National = (FloatingActionButton) findViewById(R.id.national);
        Labor = (FloatingActionButton) findViewById(R.id.labor);
        pname=(TextView) findViewById(R.id.profile_name);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        name=getIntent().getStringExtra("name");
        pname.setText(name);

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

                        employee_heading="<b>" + "Employee Number: " + "</b> ";

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
                                Toast.makeText(ProfileFabMenu.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                        message = data.getString("message");
                        Toast.makeText(ProfileFabMenu.this, message, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(ProfileFabMenu.this, LoginCardOverlap.class);
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
                Toast.makeText(ProfileFabMenu.this, message, Toast.LENGTH_SHORT).show();
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

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileFabMenu.this, "employee num: " + employee_number, Toast.LENGTH_SHORT).show();
                showCustomDialog(ProfileFabMenu.this, "Basic information" ,employee_heading,employee_number, gender_heading,gender, martial_heading,maritalStatus, email_heading, email,hiredate_heading, hireDate,dob_heading, dob,nationality_heading, nationality,officeNum_heading, officeNum,"", "", "", "",picture);


            }
        });

        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Employment information", employee_heading,employee_number, location_heading,location,status_heading, status, org_heading,org, manager_heading,manager, job_heading, job, grade_heading, grade, "", "", "", "","","",picture);

            }
        });

        Passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Passport information", passnum_heading,passNum, passissue_heading,passIssue, passexpire_heading, passexpire, "", "", "", "", "", "", "", "", "", "","","","","",picture);


            }
        });

        Visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Visa information",visanum_heading, visaNum, visaissue_heading,visaIssue, visaexpire_heading,visaExpire,"", "", "", "", "", "", "", "", "", "","","","","",picture);
            }
        });

        National.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "National ID information", nationalid_heading,nationalId, nationalIDissue_heading,nationalIdIssue, nationalIDexpire_heading,nationalIdExpire ,"", "", "", "", "", "", "", "", "", "","","","","",picture);


            }
        });

        Labor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Labour Card information", labornum_heading,laborNum,laborissue_heading,laborIssue,laborexpire_heading, laborExpire, "", "", "", "", "", "", "", "", "", "","","","","",picture);


            }
        });

        initToolbar();
    }
    private void showCustomDialog(Context ctx, String heading, String text1, String text2, String text3, String text4, String text5, String text6, String text7, String text8, String text9, String text10, String text11, String text12, String text13,String text14,String text15,String text16,String text17,String text18,String text19,String text20, String pic12) {

        final TextView headingHide, headingText, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20;
        final ImageView img;

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.activity_ploygon_profile);
        dialog.setCancelable(true);


        headingText = (TextView) dialog.findViewById(R.id.heading);
        t1 = (TextView) dialog.findViewById(R.id.text1);
        t2 = (TextView) dialog.findViewById(R.id.text2);
        t3 = (TextView) dialog.findViewById(R.id.text3);
        t4 = (TextView) dialog.findViewById(R.id.text4);
        t5 = (TextView) dialog.findViewById(R.id.text5);
        t6 = (TextView) dialog.findViewById(R.id.text6);
        t7 = (TextView) dialog.findViewById(R.id.text7);
        t8 = (TextView) dialog.findViewById(R.id.text8);
        t9 = (TextView) dialog.findViewById(R.id.text9);
        t10 = (TextView) dialog.findViewById(R.id.text10);
        t11 = (TextView) dialog.findViewById(R.id.text11);
        t12 = (TextView) dialog.findViewById(R.id.text12);
        t13 = (TextView) dialog.findViewById(R.id.text13);
        t14 = (TextView) dialog.findViewById(R.id.text14);
        t15=(TextView)dialog.findViewById(R.id.text15);
        t16=(TextView)dialog.findViewById(R.id.text16);
        t17=(TextView)dialog.findViewById(R.id.text17);
        t18=(TextView)dialog.findViewById(R.id.text18);

        img = (ImageView) dialog.findViewById(R.id.image);

     //   Toast.makeText(ctx, "url="+pic12, Toast.LENGTH_SHORT).show();
if(!(pic12.equals(""))){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                URL url = new URL(pic12);
               // Toast.makeText(ProfileFabMenu.this, "picccc inn", Toast.LENGTH_SHORT).show();

                Picasso.get().load(pic12).transform(new CircleTransform()).into(img);

            } catch (MalformedURLException error) {
                Toast.makeText(ProfileFabMenu.this, "Error this:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }


//            final ImageButton close = (ImageButton) dialog.findViewById(R.id.bt_Close);
//
//            close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });


            headingText.setText(heading);
            t1.setText(Html.fromHtml(text1));
            t2.setText(Html.fromHtml(text2));
            t3.setText(Html.fromHtml(text3));
            t4.setText(Html.fromHtml(text4));
            t5.setText(Html.fromHtml(text5));
            t6.setText(Html.fromHtml(text6));
            t7.setText(Html.fromHtml(text7));
            t8.setText(Html.fromHtml(text8));
            t9.setText(Html.fromHtml(text9));
            t10.setText(Html.fromHtml(text10));
            t11.setText(Html.fromHtml(text11));
            t12.setText(Html.fromHtml(text12));
            t13.setText(Html.fromHtml(text13));
            t14.setText(Html.fromHtml(text14));
            t15.setText(Html.fromHtml(text15));
            t16.setText(Html.fromHtml(text16));
            t17.setText(Html.fromHtml(text17));
            t18.setText(Html.fromHtml(text18));


            if (t4.getText().equals(""))
                t4.setVisibility(View.GONE);
            if (t5.getText().equals(""))
                t5.setVisibility(View.GONE);
            if (t6.getText().equals(""))
                t6.setVisibility(View.GONE);
            if (t7.getText().equals(""))
                t7.setVisibility(View.GONE);
            if (t8.getText().equals(""))
                t8.setVisibility(View.GONE);
            if (t9.getText().equals(""))
                t9.setVisibility(View.GONE);
            if (t10.getText().equals(""))
                t10.setVisibility(View.GONE);
            if (t11.getText().equals(""))
                t11.setVisibility(View.GONE);
            if (t12.getText().equals(""))
                t12.setVisibility(View.GONE);
            if (t13.getText().equals(""))
                t13.setVisibility(View.GONE);
    if (t14.getText().equals(""))
        t14.setVisibility(View.GONE);
    if (t15.getText().equals(""))
        t15.setVisibility(View.GONE);
    if (t16.getText().equals(""))
        t16.setVisibility(View.GONE);
    if (t17.getText().equals(""))
        t17.setVisibility(View.GONE);
    if (t18.getText().equals(""))
        t18.setVisibility(View.GONE);



            final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            //  final EditText et_post = (EditText) dialog.findViewById(R.id.et_post);


            dialog.show();
            dialog.getWindow().setAttributes(lp);


        }
    }
        private void initToolbar () {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //  toolbar.setNavigationIcon(R.drawable.ic_menu);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("HR Profile");
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
                Intent intent = new Intent(ProfileFabMenu.this, LoginCardOverlap.class);
                startActivity(intent);
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    }

