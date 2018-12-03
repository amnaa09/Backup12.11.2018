package com.example.sammrabatool.solutions5d.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.sammrabatool.solutions5d.ViewAnimation;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class HrProfile extends AppCompatActivity {

    private View parent_view;
    private NestedScrollView nested_scroll_view;
    private TextView tv_booking_code;
    private ImageButton bt_toggle_employ_info, bt_toggle_extra, bt_toggle_text;
    private Button bt_hide_info;
    private View lyt_expand_employ_info, lyt_expand_extra,lyt_expand_text;
    TextView quote;

    ImageView profimg;
    TextView profnm,profjb,employee_No,hire_date,gender,birth_date,martial_status,nationality,email,
    phone_no,organization,job,grade,location,status,manager,passport_no,passport_inssuance,passport_expire,visa_no,visa_inssuance,
            visa_expire,labor_card,labor_insuance,labor_expiry,nid_no,nid_inssuance,nid_expiry,emirate_id;
    int lg,bg;
    String   instanceStr, message, userID, token, profile, image,text;
    boolean user_valid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hr);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        profimg=(ImageView)findViewById(R.id.Profileimage);
        profnm=(TextView)findViewById(R.id.profileName);
        profjb=(TextView)findViewById(R.id.profileJob);
        employee_No=(TextView)findViewById(R.id.emplyno);
        hire_date=(TextView)findViewById(R.id.hired);
        gender=(TextView)findViewById(R.id.gend);
        birth_date=(TextView)findViewById(R.id.bithd);
        martial_status=(TextView)findViewById(R.id.martilstat);
        nationality=(TextView)findViewById(R.id.national);
        email=(TextView)findViewById(R.id.EMAIL);
        phone_no=(TextView)findViewById(R.id.phoneno);
        organization=(TextView)findViewById(R.id.organ);
        job=(TextView)findViewById(R.id.JOB);
        grade=(TextView)findViewById(R.id.grad);
        location=(TextView)findViewById(R.id.loc);
        status=(TextView)findViewById(R.id.stat);
        manager=(TextView)findViewById(R.id.mang);
        passport_no=(TextView)findViewById(R.id.passportno);
        passport_inssuance=(TextView)findViewById(R.id.passpinsuan);
        passport_expire=(TextView)findViewById(R.id.passpoexpire);
        visa_no=(TextView)findViewById(R.id.visano);
        visa_inssuance=(TextView)findViewById(R.id.visainsuance);
        visa_expire=(TextView)findViewById(R.id.visaexpiredate);
        labor_card=(TextView)findViewById(R.id.laborcard);
        labor_insuance=(TextView)findViewById(R.id.labourinsuan);
        labor_expiry=(TextView)findViewById(R.id.labourexpiry);
        emirate_id=(TextView)findViewById(R.id.emirateid);
        nid_no=(TextView)findViewById(R.id.nidno);
        nid_inssuance=(TextView)findViewById(R.id.nidinsuan);
        nid_expiry=(TextView)findViewById(R.id.nidexpiry);

        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");

        parent_view = findViewById(android.R.id.content);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_content);

//        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedprefSignup.edit();
//        editor.putInt("LG", lg);
//        editor.putInt("BG",bg);
//        editor.commit();

        lg=getIntent().getIntExtra("lg",0);
        bg=getIntent().getIntExtra("bg",0);

        //volleypart
        RequestQueue MyRequestQueue = Volley.newRequestQueue(HrProfile.this);
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
                       text= employee_No.getText().toString();
                        if(dataDetails.getString("employeeNo").equals("")) {
                            text += "--";
                          //  text += dataDetails.getString("employeeNo");
                            employee_No.setText(text);
                          //  employee_No.setText("Unknown");
                        }
                        else {
                            text += dataDetails.getString("employeeNo");
                            employee_No.setText(text);

                        }
                        text="";
                        text= hire_date.getText().toString();
                        if(dataDetails.getString("hireDate").equals("")){
                            text += "--";
                            hire_date.setText(text);
                        }
                           // hire_date.setText("Unknown");
                        else {
                            text +=dataDetails.getString("hireDate");
                            hire_date.setText(text);
                        }
                        text="";
                        text= gender.getText().toString();
                        if(dataDetails.getString("gender").equals("")) {
                            text += "--";
                            gender.setText("Unknown");
                        }
                        else {
                            text +=dataDetails.getString("gender");
                            gender.setText(text);
                        }
                        text="";
                        text= birth_date.getText().toString();
                        if(dataDetails.getString("dateOfBirth").equals("")) {
                            text += "--";
                            birth_date.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("dateOfBirth");
                            birth_date.setText(text);
                        }
                        text="";
                        text= martial_status.getText().toString();
                        if(dataDetails.getString("maritalStatus").equals("")) {
                            text+="--";
                            martial_status.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("maritalStatus");
                            martial_status.setText(text);
                        }
                        text="";
                        text=nationality.getText().toString();
                        if(dataDetails.getString("nationality").equals("")) {
                            text+="--";
                            nationality.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("nationality");
                            nationality.setText(text);
                        }
                        text="";
                        text=email.getText().toString();
                        if(dataDetails.getString("email").equals("")) {
                            text+="--";
                            email.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("email");
                            email.setText(text);
                        }
                        text="";
                        text=phone_no.getText().toString();
                        if(dataDetails.getString("officeNumber").equals("")) {
                            text+="--";
                            phone_no.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("officeNumber");
                            phone_no.setText(text);
                        }
                        text="";
                        text=organization.getText().toString();
                        if(dataDetails.getString("organization").equals("")) {
                            text+="--";
                            organization.setText(text);
                        }
                        else{
                            text+=dataDetails.getString("organization");
                            organization.setText(text);
                        }
                        text="";
                        text=job.getText().toString();
                        if(dataDetails.getString("job").equals("")) {
                            text+="Unknown";
                            job.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("job");
                            job.setText(text);
                        }
                        text="";
                        text=grade.getText().toString();
                        if(dataDetails.getString("grade").equals("")) {
                            text+="--";
                            grade.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("grade");
                            grade.setText(text);
                        }
                        text="";
                        text=location.getText().toString();
                        if(dataDetails.getString("location").equals("")) {
                            text+="--";
                            location.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("location");
                            grade.setText(text);
                        }
                        text="";
                        text=status.getText().toString();
                        if(dataDetails.getString("status").equals("")) {
                            text+="unknown";
                            status.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("status");
                            status.setText(text);
                        }
                        text="";
                        text=manager.getText().toString();
                        if(dataDetails.getString("manager").equals("")) {
                            text+="unknown";
                            manager.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("manager");
                            manager.setText(text);
                        }
                        text="";
                        text=passport_no.getText().toString();
                        if(dataDetails.getString("passportno").equals("")) {
                            text+="--";
                            passport_no.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("passportno");
                            passport_no.setText(text);
                        }
                        text="";
                        text=passport_inssuance.getText().toString();
                        if(dataDetails.getString("passport_issuance").equals("")) {
                        text+="--";
                        passport_inssuance.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("passport_issuance");
                            passport_inssuance.setText(text);
                        }
                        text="";
                        text=passport_expire.getText().toString();
                        if(dataDetails.getString("passport_expiry").equals("")) {
                            text+="--";
                            passport_expire.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("passport_expiry");
                            passport_expire.setText(text);
                        }
                        text="";
                        text=visa_no.getText().toString();
                        if(dataDetails.getString("visano").equals("")) {
                            text+="--";
                            visa_no.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("visano");
                            visa_no.setText(text);
                        }
                        text="";
                        text=visa_inssuance.getText().toString();
                        if(dataDetails.getString("visa_issuance").equals("")) {
                            text+="--";
                            visa_inssuance.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("visa_issuance");
                            visa_inssuance.setText(text);
                        }
                        text="";
                        text=visa_expire.getText().toString();
                        if(dataDetails.getString("visa_expiry").equals("")) {
                            text+="--";
                            visa_expire.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("visa_expiry");
                            visa_expire.setText(text);
                        }
                        text="";
                        text=labor_card.getText().toString();
                        if(dataDetails.getString("labourno").equals("")) {
                            text+="--";
                            labor_card.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("labourno");
                            labor_card.setText(text);
                        }
                        text="";
                        text=labor_insuance.getText().toString();
                        if(dataDetails.getString("labour_issuance").equals("")) {
                            text+="--";
                            labor_insuance.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("labour_issuance");labor_insuance.setText(text);
                        }
                        text="";
                        text=labor_expiry.getText().toString();
                        if(dataDetails.getString("labour_expiry").equals("")) {
                            text+="--";
                            labor_expiry.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("labour_expiry");
                            labor_expiry.setText(text);
                        }
                        text="";
                        text=nid_no.getText().toString();
                        if(dataDetails.getString("nIdno").equals("")) {
                            text+="--";
                            nid_no.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("nIdno");
                            nid_no.setText(text);
                        }
                        text="";
                        text=nid_inssuance.getText().toString();
                        if(dataDetails.getString("nId_issuance").equals("")) {
                            text+="--";
                            nid_inssuance.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("nId_issuance");
                            nid_inssuance.setText(text);
                        }
                        text="";
                        text=nid_expiry.getText().toString();
                        if(dataDetails.getString("nId_expiry").equals("")) {
                            text+="--";
                            nid_expiry.setText(text);
                        }
                        else {
                            text+=dataDetails.getString("nId_expiry");
                            nid_expiry.setText(text);
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
                                //    Toast.makeText(ProfilePurple.this, "picccc", Toast.LENGTH_SHORT).show();

                                Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profimg);

                            }
                            catch (MalformedURLException error) {
                                Toast.makeText(HrProfile.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(HrProfile.this, message, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent=new Intent(HrProfile.this,LoginCardOverlap.class);
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
                Toast.makeText(HrProfile.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);


        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();






        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HR Profile");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        // info item_section
        bt_toggle_text=(ImageButton) findViewById(R.id.bt_toggle_text);
        lyt_expand_text = (View) findViewById(R.id.lyt_expand_text);
        lyt_expand_text.setVisibility(View.GONE);

        bt_toggle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionText(bt_toggle_text);
            }
        });

        bt_toggle_employ_info = (ImageButton) findViewById(R.id.bt_toggle_employ_info);
      //  bt_hide_info = (Button) findViewById(R.id.bt_hide_info);
        lyt_expand_employ_info = (View) findViewById(R.id.lyt_expand_employ_info);
        lyt_expand_employ_info.setVisibility(LinearLayout.GONE);
        bt_toggle_employ_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionEmploymentInfo(bt_toggle_employ_info);
            }
        });


        bt_toggle_extra = (ImageButton) findViewById(R.id.bt_toggle_extra);
        lyt_expand_extra = (View) findViewById(R.id.lyt_expand_extra);
        lyt_expand_extra.setVisibility(LinearLayout.GONE);

        bt_toggle_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionExtraInfo(bt_toggle_extra);
            }
        });

        // copy to clipboard

     /*   bt_copy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.copyToClipboard(getApplicationContext(), tv_booking_code.getText().toString());
            }
        }); */


    }

    private void toggleSectionText(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_text);
        }
    }

    private void toggleSectionEmploymentInfo(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_employ_info, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_employ_info);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_employ_info);
        }
    }

    private void toggleSectionExtraInfo(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_extra, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_extra);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_extra);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
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
            Intent intent=new Intent(HrProfile.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
