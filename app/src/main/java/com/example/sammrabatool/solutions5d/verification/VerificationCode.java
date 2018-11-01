package com.example.sammrabatool.solutions5d.verification;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sammrabatool.solutions5d.Activity.Signup;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VerificationCode extends AppCompatActivity {

    TextInputEditText otp_code1,otp_code2, otp_code3,otp_code4,otp_code5;
    AppCompatButton verify;
    String userID,instanceStr,otpCode="", message;
    boolean user_valid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        initToolbar();

        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
       // Toast.makeText(this, "data="+userID, Toast.LENGTH_SHORT).show();
        verify=(AppCompatButton)findViewById(R.id.verify);
        otp_code1=(TextInputEditText)findViewById(R.id.otp1);
        otp_code2=(TextInputEditText)findViewById(R.id.otp2);
        otp_code3=(TextInputEditText)findViewById(R.id.otp3);
        otp_code4=(TextInputEditText)findViewById(R.id.otp4);
        //   otp_code5=(TextInputEditText)findViewById(R.id.otp5);

        otp_code1.addTextChangedListener( new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp_code1.getText().toString().length() == 1)     //size as per your requirement
                {

                    otp_code2.requestFocus();
                 //   Toast.makeText(VerificationCode.this, "otp1", Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
              //  Toast.makeText(VerificationCode.this, " no otp1", Toast.LENGTH_SHORT).show();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        otp_code2.addTextChangedListener( new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp_code2.getText().toString().length() == 1)     //size as per your requirement
                {

                    otp_code3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        otp_code3.addTextChangedListener( new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp_code3.getText().toString().length() == 1)     //size as per your requirement
                {

                    otp_code4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        otp_code4.addTextChangedListener( new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp_code4.getText().toString().length() == 1)     //size as per your requirement
                {

                    verify.requestFocus();
                    // opt_code5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    /*    otp_code5.addTextChangedListener( new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp_code5.getText().toString().length() == 1)     //size as per your requirement
                {
                    up.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });*/


    verify.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //verify otp

            otpCode+=otp_code1.getText().toString()+otp_code2.getText().toString()+otp_code3.getText().toString()+otp_code4.getText().toString();

          //  Toast.makeText(VerificationCode.this, "otp="+otpCode, Toast.LENGTH_SHORT).show();
            RequestQueue MyRequestQueue = Volley.newRequestQueue(VerificationCode.this);
           String url = "http://"+instanceStr+".5dsurf.com/app/webservice/verifiedotp/"+userID+"/"+otpCode;
            otpCode="";
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
                        message = data.getString("message");
                        userID=data.getString("user_id");
                        //  tx.setText("response== " + name+ age);
                        //    Toast.makeText(Signup.this, "result="+user_valid, Toast.LENGTH_SHORT).show();
                        //   company.setText(name);
                        //     userId.setText(age);
                        if(user_valid==true) {
                            user_valid=false;
                            Intent intent=new Intent(VerificationCode.this, LoginCardOverlap.class);
                            intent.putExtra("userID",userID);
                            intent.putExtra("instance",instanceStr);
                            startActivity(intent);
                        }

                        else {

                            Toast.makeText(VerificationCode.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //
                        //                            //  instance.setText("error= " + e.getMessage());
                        Toast.makeText(VerificationCode.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(VerificationCode.this, message, Toast.LENGTH_SHORT).show();
                }
            })
            {
            };

            MyStringRequest.setShouldCache(false);
            MyRequestQueue.add(MyStringRequest);

        }
    });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
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


}
