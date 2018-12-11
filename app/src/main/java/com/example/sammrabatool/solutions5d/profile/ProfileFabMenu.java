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
import com.example.sammrabatool.solutions5d.dialog.DialogAddReview;
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
    LinearLayout Basic, Employee, Passport, Visa, National, Labor;
    FloatingActionButton basi;
    int lg, bg;
    boolean user_valid;
    ImageView profimg;

    String instanceStr, message, userID, token, profile, text;
    String picture,employee_number,employee_p, hireDate,hire_date,gender_fm, gender,martial_mu, dob,dob_full,email_com, maritalStatus, nationality,nationality_p,
            email, officeNum,office_phone, org,org_organization, job, grade, location,loaction_d, status,status_t, manager,manager_d, passNum,pass_num, passIssue,pass_issue, passexpire,pass_expire, visaNum,visa_num, visaIssue,visa_issue, visaExpire,visa_expire, laborNum,labor_num,
            laborIssue,labor_issue, laborExpire,labor_expire, nationalId,national_id, nationalIdIssue,national_issue, nationalIdExpire,national_expire, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fab_menu);
        basi = (FloatingActionButton) findViewById(R.id.basicc);
        profimg = (ImageView) findViewById(R.id.image);
        Basic = (LinearLayout) findViewById(R.id.basic);
        Employee = (LinearLayout) findViewById(R.id.employeemnt);
        Passport = (LinearLayout) findViewById(R.id.passport);
        Visa = (LinearLayout) findViewById(R.id.visa);
        National = (LinearLayout) findViewById(R.id.national);
        Labor = (LinearLayout) findViewById(R.id.labor);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");

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

                        if (dataDetails.getString("employeeNo").equals("")) {
                            employee_number = "<b>" + "Employee Number: " + "</b> ";
                            employee_p="--";
                        } else {
                            employee_number = "<b>" + "Employee Number: " + "</b> " + dataDetails.getString("employeeNo");
                            //employee_p.setText(text);
                        }

                        if (dataDetails.getString("hireDate").equals("")) {
                            hireDate = "<b>" + "Hire Date: " + "</b> " + "--";
                        }
                        // hire_date.setText("Unknown");
                        else {
                            hireDate = "<b>" + "Hire Date: " + "</b> " + dataDetails.getString("hireDate");

                        }
                        if (dataDetails.getString("gender").equals("")) {
                            gender = "<b>" + "Gender: " + "</b> " + "--";

                        } else {
                            gender = "<b>" + "Gender: " + "</b> " + dataDetails.getString("gender");

                        }

                        if (dataDetails.getString("dateOfBirth").equals("")) {
                            dob = "<b>" + "Date Of Birth: " + "</b> " + "--";

                        } else {
                            dob = "<b>" + "Date Of Birth: " + "</b> " + dataDetails.getString("dateOfBirth");

                        }

                        if (dataDetails.getString("maritalStatus").equals("")) {
                            maritalStatus = "<b>" + "Martial Status: " + "</b> " + "--";

                        } else {
                            maritalStatus = "<b>" + "Martial Status: " + "</b> " + dataDetails.getString("maritalStatus");

                        }

                        if (dataDetails.getString("nationality").equals("")) {
                            nationality = "<b>" + "Nationality:" + "</b> " + "--";

                        } else {
                            nationality = "<b>" + "Nationality:" + "</b> " + dataDetails.getString("nationality");

                        }

                        if (dataDetails.getString("email").equals("")) {
                            email = "<b>" + "Email: " + "</b> " + "--";

                        } else {
                            email = "<b>" + "Email:" + "</b> " + dataDetails.getString("email");

                        }

                        if (dataDetails.getString("officeNumber").equals("")) {
                            officeNum = "<b>" + "Phone No:" + "</b> " + "--";

                        } else {
                            officeNum = "<b>" + "Phone No:" + "</b> " + dataDetails.getString("officeNumber");

                        }

                        if (dataDetails.getString("organization").equals("")) {
                            org = "<b>" + "Organization:" + "</b> " + "--";

                        } else {
                            org = "<b>" + "Organization:" + "</b> " + dataDetails.getString("organization");

                        }

                        if (dataDetails.getString("job").equals("")) {
                            job = "<b>" + "Job:" + "</b> " + "Unknown";

                        } else {
                            job = "<b>" + "Job:" + "</b> " + dataDetails.getString("job");

                        }

                        if (dataDetails.getString("grade").equals("")) {
                            grade = "<b>" + "Grade :" + "</b> " + "--";

                        } else {
                            grade = "<b>" + "Grade :" + "</b> " + dataDetails.getString("grade");

                        }

                        if (dataDetails.getString("location").equals("")) {
                            location = "<b>" + "Location :" + "</b> " + "--";

                        } else {
                            location = "<b>" + "Location :" + "</b> " + dataDetails.getString("location");

                        }

                        if (dataDetails.getString("status").equals("")) {
                            status = "<b>" + "Status :" + "</b> " + "unknown";

                        } else {
                            status = "<b>" + "Status :" + "</b> " + dataDetails.getString("status");

                        }

                        if (dataDetails.getString("manager").equals("")) {
                            manager = "<b>" + "Manager :" + "</b> " + "unknown";

                        } else {
                            manager = "<b>" + "Manager :" + "</b> " + dataDetails.getString("manager");

                        }

                        if (dataDetails.getString("passportno").equals("")) {
                            passNum = "<b>" + "PassPort No :" + "</b> " + "--";

                        } else {
                            passNum = "<b>" + "PassPort No :" + "</b> " + dataDetails.getString("passportno");

                        }


                        if (dataDetails.getString("passport_issuance").equals("")) {
                            passIssue = "<b>" + "PassPort Issuance :" + "</b> " + "--";

                        } else {
                            passIssue = "<b>" + "PassPort Issuance :" + "</b>" + dataDetails.getString("passport_issuance");

                        }

                        if (dataDetails.getString("passport_expiry").equals("")) {
                            passexpire = "<b>" + "PassPort Expiry :" + " </b>" + "--";

                        } else {
                            passexpire = "<b>" + "PassPort Expiry :" + " </b>" + dataDetails.getString("passport_expiry");

                        }

                        if (dataDetails.getString("visano").equals("")) {
                            visaNum = "<b>" + "Visa No :" + " </b>" + "--";

                        } else {
                            visaNum = "<b>" + "Visa No :" + " </b>" + dataDetails.getString("visano");

                        }

                        if (dataDetails.getString("visa_issuance").equals("")) {
                            visaIssue = "<b>" + "Visa Issuance :" + " </b>" + "--";

                        } else {
                            visaIssue += "<b>" + "Visa Issuance :" + " </b>" + dataDetails.getString("visa_issuance");

                        }

                        if (dataDetails.getString("visa_expiry").equals("")) {
                            visaExpire = "<b>" + "Visa Expiry :" + " </b>" + "--";

                        } else {
                            visaExpire = "<b>" + "Visa Expiry :" + " </b>" + dataDetails.getString("visa_expiry");

                        }

                        if (dataDetails.getString("labourno").equals("")) {
                            laborNum = "<b>" + "Lanour No :" + " </b>" + "--";

                        } else {
                            laborNum = "<b>" + "Lanour No :" + " </b>" + dataDetails.getString("labourno");

                        }

                        if (dataDetails.getString("labour_issuance").equals("")) {
                            laborIssue = "<b>" + "Lanour Issuance :" + " </b>" + "--";

                        } else {
                            laborIssue = "<b>" + "Lanour Issuance :" + " </b>" + dataDetails.getString("labour_issuance");
                        }

                        if (dataDetails.getString("labour_expiry").equals("")) {
                            laborExpire = "<b>" + "Lanour Expiry :" + " </b>" + "--";

                        } else {
                            laborExpire = "<b>" + "Lanour Expiry :" + " </b>" + dataDetails.getString("labour_expiry");

                        }

                        if (dataDetails.getString("nIdno").equals("")) {
                            nationalId = "<b>" + "National Id No :" + " </b>" + "--";

                        } else {
                            nationalId = "<b>" + "National Id No :" + " </b>" + dataDetails.getString("nIdno");

                        }

                        if (dataDetails.getString("nId_issuance").equals("")) {
                            nationalIdIssue = "<b>" + "National Id Issuance :" + " </b>" + "--";

                        } else {
                            nationalIdIssue = "<b>" + "National Id Issuance :" + " </b>" + dataDetails.getString("nId_issuance");

                        }

                        if (dataDetails.getString("nId_expiry").equals("")) {
                            nationalIdExpire = "<b>" + "National Id Expiry :" + " </b>" + "--";

                        } else {
                            nationalIdExpire = "<b>" + "National Id Expiry :" + dataDetails.getString("nId_expiry");

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
                                Toast.makeText(ProfileFabMenu.this, "picccc", Toast.LENGTH_SHORT).show();

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
            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);


        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        basi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileFabMenu.this, "employee num: " + employee_number, Toast.LENGTH_SHORT).show();
                showCustomDialog(ProfileFabMenu.this, "Basic information", employee_number,employee_p, gender,gender_fm, maritalStatus,martial_mu, email,email_com, hireDate,hire_date, dob,dob_full, nationality,nationality_p, officeNum,office_phone,"", "", "", "",picture);


            }
        });

        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Employment information", employee_number,employee_p, location,loaction_d, status,status_t, org,org_organization, manager,manager_d, "", "", "", "", "", "", "", "","","",picture);

            }
        });

        Passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Passport information", passNum,pass_num, passIssue,pass_issue, passexpire,pass_expire, "", "", "", "", "", "", "", "", "", "","","","","",picture);

            }
        });

        Visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Visa information", visaNum,visa_num, visaIssue,visa_issue, visaExpire,visa_expire,"", "", "", "", "", "", "", "", "", "","","","","",picture);

            }
        });

        National.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "National ID information", nationalId,national_id, nationalIdIssue,national_issue, nationalIdExpire,national_issue ,"", "", "", "", "", "", "", "", "", "","","","","",picture);

            }
        });

        Labor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Labor Card information", laborNum,labor_num,laborIssue,labor_issue, laborExpire,labor_expire, "", "", "", "", "", "", "", "", "", "","","","","",picture);

            }
        });

        initToolbar();
    }
    private void showCustomDialog(Context ctx, String heading, String text1, String text2, String text3, String text4, String text5, String text6, String text7, String text8, String text9, String text10, String text11, String text12, String text13,String text14,String text15,String text16,String text17,String text18,String text19,String text20, String pic12) {

        final TextView headingText, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20;
        final ImageView img;

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.activity_ploygon_profile);
        dialog.setCancelable(true);

        headingText = (TextView) dialog.findViewById(R.id.unknown);
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
        t19=(TextView)dialog.findViewById(R.id.text19);
        t20=(TextView)dialog.findViewById(R.id.text20);
        img = (ImageView) dialog.findViewById(R.id.image);

        Toast.makeText(ctx, "url="+pic12, Toast.LENGTH_SHORT).show();
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
    t19.setText(Html.fromHtml(text19));
    t20.setText(Html.fromHtml(text20));

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
    if (t19.getText().equals(""))
        t19.setVisibility(View.GONE);
    if (t20.getText().equals(""))
        t20.setVisibility(View.GONE);


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

