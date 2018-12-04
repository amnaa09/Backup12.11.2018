package com.example.sammrabatool.solutions5d.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
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
LinearLayout Basic,Employee,Passport,Visa,National,Labor;
FloatingActionButton basi;
int lg, bg;
boolean user_valid;
    ImageView profimg;

    String   instanceStr, message, userID, token, profile, text;
    String employee_number, hireDate, gender, dob, maritalStatus, nationality, email, officeNum, org, job, grade, location, status, manager, passNum, passIssue, passexpire, visaNum, visaIssue, visaExpire, laborNum, laborIssue, laborExpire, nationalId, nationalIdIssue, nationalIdExpire, pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fab_menu);
        basi=(FloatingActionButton)findViewById(R.id.basicc);
        profimg=(ImageView) findViewById(R.id.image);
Basic=(LinearLayout)findViewById(R.id.basic);
Employee=(LinearLayout)findViewById(R.id.employeemnt);
Passport=(LinearLayout)findViewById(R.id.passport);
Visa=(LinearLayout)findViewById(R.id.visa);
National=(LinearLayout)findViewById(R.id.national);
Labor=(LinearLayout)findViewById(R.id.labor);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        lg=getIntent().getIntExtra("lg",0);
        bg=getIntent().getIntExtra("bg",0);
        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);


        String url = "http://"+instanceStr+".5dsurf.com/app/webservice/gethrProfiledata/"+bg+"/"+userID+"/"+token;
        StringRequest MyStringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if(user_valid==true){
                        message = data.getString("message");
                        userID = data.getString("user_id");
                        profile=data.getString("profile");
                        JSONObject dataDetails = new JSONObject(profile.toString());

                        if(dataDetails.getString("employeeNo").equals("")) {
                            employee_number="Employee Number: --";
                        }
                        else {
                            employee_number="Employee Number: "+dataDetails.getString("employeeNo");

                        }

                        if(dataDetails.getString("hireDate").equals("")){
                            hireDate = "--";
                        }
                        // hire_date.setText("Unknown");
                        else {
                            hireDate =dataDetails.getString("hireDate");

                        }
                        if(dataDetails.getString("gender").equals("")) {
                            gender = "--";

                        }
                        else {
                            gender =dataDetails.getString("gender");

                        }

                        if(dataDetails.getString("dateOfBirth").equals("")) {
                            dob = "--";

                        }
                        else {
                            dob=dataDetails.getString("dateOfBirth");

                        }

                        if(dataDetails.getString("maritalStatus").equals("")) {
                            maritalStatus="--";

                        }
                        else {
                            maritalStatus=dataDetails.getString("maritalStatus");

                        }

                        if(dataDetails.getString("nationality").equals("")) {
                            nationality="--";

                        }
                        else {
                            nationality=dataDetails.getString("nationality");

                        }

                        if(dataDetails.getString("email").equals("")) {
                            email="--";

                        }
                        else {
                            email=dataDetails.getString("email");

                        }

                        if(dataDetails.getString("officeNumber").equals("")) {
                            officeNum="--";

                        }
                        else {
                            officeNum=dataDetails.getString("officeNumber");

                        }

                        if(dataDetails.getString("organization").equals("")) {
                            org="--";

                        }
                        else{
                            org=dataDetails.getString("organization");

                        }

                        if(dataDetails.getString("job").equals("")) {
                            job="Unknown";

                        }
                        else {
                            job=dataDetails.getString("job");

                        }

                        if(dataDetails.getString("grade").equals("")) {
                            grade="--";

                        }
                        else {
                            grade=dataDetails.getString("grade");

                        }

                        if(dataDetails.getString("location").equals("")) {
                            location="--";

                        }
                        else {
                            location=dataDetails.getString("location");

                        }

                        if(dataDetails.getString("status").equals("")) {
                            status="unknown";

                        }
                        else {
                            status=dataDetails.getString("status");

                        }

                        if(dataDetails.getString("manager").equals("")) {
                            manager="unknown";

                        }
                        else {
                            manager=dataDetails.getString("manager");

                        }

                        if(dataDetails.getString("passportno").equals("")) {
                            passNum="--";

                        }
                        else {
                            passNum=dataDetails.getString("passportno");

                        }


                        if(dataDetails.getString("passport_issuance").equals("")) {
                            passIssue="--";

                        }
                        else {
                            passIssue=dataDetails.getString("passport_issuance");

                        }

                        if(dataDetails.getString("passport_expiry").equals("")) {
                            passexpire="--";

                        }
                        else {
                            passexpire=dataDetails.getString("passport_expiry");

                        }

                        if(dataDetails.getString("visano").equals("")) {
                            visaNum="--";

                        }
                        else {
                            visaNum=dataDetails.getString("visano");

                        }

                        if(dataDetails.getString("visa_issuance").equals("")) {
                            visaIssue="--";

                        }
                        else {
                            visaIssue+=dataDetails.getString("visa_issuance");

                        }

                        if(dataDetails.getString("visa_expiry").equals("")) {
                            visaExpire="--";

                        }
                        else {
                            visaExpire=dataDetails.getString("visa_expiry");

                        }

                        if(dataDetails.getString("labourno").equals("")) {
                            laborNum="--";

                        }
                        else {
                            laborNum=dataDetails.getString("labourno");

                        }

                        if(dataDetails.getString("labour_issuance").equals("")) {
                            laborIssue="--";

                        }
                        else {
                            laborIssue=dataDetails.getString("labour_issuance");
                        }

                        if(dataDetails.getString("labour_expiry").equals("")) {
                            laborExpire="--";

                        }
                        else {
                            laborExpire=dataDetails.getString("labour_expiry");

                        }

                        if(dataDetails.getString("nIdno").equals("")) {
                            nationalId="--";

                        }
                        else {
                            nationalId=dataDetails.getString("nIdno");

                        }

                        if(dataDetails.getString("nId_issuance").equals("")) {
                            nationalIdIssue="--";

                        }
                        else {
                            nationalIdIssue=dataDetails.getString("nId_issuance");

                        }

                        if(dataDetails.getString("nId_expiry").equals("")) {
                            nationalIdExpire="--";

                        }
                        else {
                            nationalIdExpire=dataDetails.getString("nId_expiry");

                        }
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here
                            try {
                                URL url = new URL(dataDetails.getString("pic"));
                                   Toast.makeText(ProfileFabMenu.this, "picccc", Toast.LENGTH_SHORT).show();

                                Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profimg);

                            }
                            catch (MalformedURLException error) {
                                Toast.makeText(ProfileFabMenu.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }
                        if ( progressDialog.isShowing())
                            progressDialog.hide();
                    }
                    else
                    {
                        if ( progressDialog.isShowing())
                            progressDialog.hide();
                        message = data.getString("message");
                        Toast.makeText(ProfileFabMenu.this, message, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent=new Intent(ProfileFabMenu.this,LoginCardOverlap.class);
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
        Toast.makeText(ProfileFabMenu.this, "employee num: "+employee_number, Toast.LENGTH_SHORT).show();
        showCustomDialog(ProfileFabMenu.this, "Basic information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);


    }
});

Employee.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showCustomDialog(ProfileFabMenu.this, "Employment information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);

    }
});

Passport.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showCustomDialog(ProfileFabMenu.this, "Passport information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);

    }
});

Visa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showCustomDialog(ProfileFabMenu.this, "Visa information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);

    }
});

National.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showCustomDialog(ProfileFabMenu.this, "National ID information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);

    }
});

        Labor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(ProfileFabMenu.this, "Labor Card information",employee_number, gender, maritalStatus, email, hireDate, dob, nationality, officeNum);

            }
        });

       initToolbar();
    }

    private void showCustomDialog(Context ctx, String heading,String employee_number1, String gender1, String maritalStatus1, String email1, String hireDate1, String dob1, String nationality1, String officeNum1)
    {

        final TextView headingText,t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13;


        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.dialog_add_review);
        dialog.setCancelable(true);

        headingText=(TextView) dialog.findViewById(R.id.textHeading) ;
        t1=(TextView) dialog.findViewById(R.id.text1);
        t2=(TextView) dialog.findViewById(R.id.text2);
        t3=(TextView) dialog.findViewById(R.id.text3);
        t4=(TextView) dialog.findViewById(R.id.text4);
        t5=(TextView) dialog.findViewById(R.id.text5);
        t6=(TextView) dialog.findViewById(R.id.text6);
        t7=(TextView) dialog.findViewById(R.id.text7);
        t8=(TextView) dialog.findViewById(R.id.text8);
        t9=(TextView) dialog.findViewById(R.id.text9);
        t10=(TextView) dialog.findViewById(R.id.text10);
        t11=(TextView) dialog.findViewById(R.id.text11);
        t12=(TextView) dialog.findViewById(R.id.text12);
        t13=(TextView) dialog.findViewById(R.id.text13);


        final ImageButton close=(ImageButton) dialog.findViewById(R.id.bt_Close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        headingText.setText(heading);
        t1.setText(employee_number1);
        t2. setText(gender1);
        t3. setText(maritalStatus1);
        t4. setText(email1);
        t5. setText(hireDate1);
        t6. setText(dob1);
        t7.setText(nationality1);
        t8.setText(officeNum1);

        t9.setVisibility(View.GONE);
        t10.setVisibility(View.GONE);
        t11.setVisibility(View.GONE);
        t12.setVisibility(View.GONE);
        t13.setVisibility(View.GONE);

        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //  final EditText et_post = (EditText) dialog.findViewById(R.id.et_post);


        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HR Profile");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout){
            Toast.makeText(this,"You have successfully logged out",Toast.LENGTH_LONG).show();
            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent=new Intent(ProfileFabMenu.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
