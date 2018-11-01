package com.example.sammrabatool.solutions5d.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.profile.ProfilePurple;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.verification.VerificationCode;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginCardOverlap extends AppCompatActivity {
    private View parent_view;
    private Button signin;
    private TextView link;
    TextInputEditText uname,password;
    String userID,instanceStr, message, userToken, userName, Passowrd;
    boolean user_valid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        parent_view = findViewById(android.R.id.content);
        Tools.setSystemBarColor(this);


        signin=(Button)findViewById(R.id.signin);
        uname=(TextInputEditText)findViewById(R.id.uname);
        password=(TextInputEditText)findViewById(R.id.password);
        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            userName=uname.getText().toString();
            Passowrd=password.getText().toString();
                RequestQueue MyRequestQueue = Volley.newRequestQueue(LoginCardOverlap.this);
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/verifyuser/"+userID+"/"+userName+"/"+Passowrd;
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
                            if(user_valid==true) {
                                message = data.getString("message");
                                userID = data.getString("user_id");
                                userToken = data.getString("user_token");
                            }

                            else
                            {
                                message = data.getString("message");
                            }
                            //  tx.setText("response== " + name+ age);
                            //    Toast.makeText(Signup.this, "result="+user_valid, Toast.LENGTH_SHORT).show();
                            //   company.setText(name);
                            //     userId.setText(age);
                            if(user_valid==true) {
                                user_valid=false;
                                //==============shared pref
                                Intent intent=new Intent(LoginCardOverlap.this, ProfilePurple.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("token",userToken);
                                intent.putExtra("instance", instanceStr);
                                startActivity(intent);
                            }

                            else {

                                Toast.makeText(LoginCardOverlap.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(LoginCardOverlap.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginCardOverlap.this, message, Toast.LENGTH_SHORT).show();
                    }
                })
                {
                };

                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);


            }
        });

    }
}
