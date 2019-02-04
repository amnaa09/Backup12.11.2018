package com.example.sammrabatool.solutions5d.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class ProfilePurple extends AppCompatActivity {
    TextView name, job, directCount, fyrCount, fyiCount, userName,company, email, location;
    ImageView profileImage;
    String   instanceStr, message, userID, token, details, image;
    boolean user_valid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_purple);
//        initToolbar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        initComponent();
        final ProgressDialog progressDialog = new ProgressDialog(this);


        name=(TextView) findViewById(R.id.profileName);
        job=(TextView) findViewById(R.id.profileJob);
        directCount=(TextView) findViewById(R.id.profileDirectCount);
        fyrCount=(TextView) findViewById(R.id.profileFyrCount);
        fyiCount=(TextView) findViewById(R.id.profileFyiCount);
        userName=(TextView) findViewById(R.id.profileUser);
        email=(TextView) findViewById(R.id.profileEmail);
        company=(TextView) findViewById(R.id.profileCompany);
        location=(TextView) findViewById(R.id.profileLocation);
        profileImage=(ImageView) findViewById(R.id.Profileimage);



        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");

        RequestQueue MyRequestQueue = Volley.newRequestQueue(ProfilePurple.this);

        String url = "http://"+instanceStr+".5dsurf.com/app/webservice/getUserpersonaldetails/"+userID+"/"+token;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                // tx.setText("response: " + response.toString());
                //  Toast.makeText(Signup.this, "reponse=" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if (user_valid == true) {
                        message = data.getString("message");
                        userID = data.getString("user_id");
                        details = data.getString("details");
                        JSONObject dataDetails = new JSONObject(details.toString());
                        if (dataDetails.getString("name").equals(""))
                            name.setText("Unknown");
                        else
                            name.setText(dataDetails.getString("name"));
                        if (dataDetails.getString("job").equals(""))
                            job.setText("Unknown");
                        else
                            job.setText(dataDetails.getString("job"));
                        directCount.setText(dataDetails.getString("reporting"));
                        fyiCount.setText(dataDetails.getString("total_FYI"));
                        fyrCount.setText(dataDetails.getString("total_FYR"));

                        if (dataDetails.getString("email").equals(""))
                            email.setText("--");
                        else
                            email.setText(dataDetails.getString("email"));
                        if (dataDetails.getString("location").equals(""))
                            location.setText("--");
                        else
                            location.setText(dataDetails.getString("location"));
                        if (dataDetails.getString("company").equals(""))
                            company.setText("Unknown");
                        else
                            company.setText(dataDetails.getString("company"));
                        if (dataDetails.getString("name").equals(""))
                            userName.setText("Unknown");
                        else
                            userName.setText(dataDetails.getString("name"));

                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here
//                            try {
                            //                              URL url = new URL(dataDetails.getString(""));
                            //    Toast.makeText(ProfilePurple.this, "picccc", Toast.LENGTH_SHORT).show();

                            Picasso.get().load(dataDetails.getString("pic")).transform(new CircleTransform()).into(profileImage);

                        }
//                            catch (MalformedURLException error) {
                        //                              Toast.makeText(ProfilePurple.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

                        //                        }


                        if (progressDialog.isShowing())
                            progressDialog.hide();
                    }
                        else
                        {
                            if ( progressDialog.isShowing())
                                progressDialog.hide();
                            message = data.getString("message");
                            Toast.makeText(ProfilePurple.this, message, Toast.LENGTH_LONG).show();

                            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent=new Intent(ProfilePurple.this,LoginCardOverlap.class);
                            startActivity(intent);
                        }





                    //  tx.setText("response== " + name+ age);
                    //    Toast.makeText(Signup.this, "result="+user_valid, Toast.LENGTH_SHORT).show();
                    //   company.setText(name);
                    //     userId.setText(age);
                    if(user_valid==true)
                    {
                        user_valid=false;
                        //==============shared pref
                        Intent intent=new Intent(ProfilePurple.this, ProfilePurple.class);
                        intent.putExtra("userID",userID);
                        intent.putExtra("token",token);
                        intent.putExtra("instance", instanceStr);
                        // startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    if ( progressDialog.isShowing())
                        progressDialog.hide();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(ProfilePurple.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //                            // tx.setText( "Error: " + e.getMessage());

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
                Toast.makeText(ProfilePurple.this, message, Toast.LENGTH_SHORT).show();
                if ( progressDialog.isShowing())
                    progressDialog.hide();
            }
        })
        {
        };

        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();


    }

//    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("View Profile");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Tools.setSystemBarColor(this, R.color.purple_600);
//    }

    private void initComponent() {

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
            Intent intent=new Intent(ProfilePurple.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
