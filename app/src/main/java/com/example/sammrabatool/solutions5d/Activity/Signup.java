package com.example.sammrabatool.solutions5d.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.sammrabatool.solutions5d.R;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.example.sammrabatool.solutions5d.PrefManager;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dialog.Agreement;
import com.example.sammrabatool.solutions5d.dialog.LockError;
import com.example.sammrabatool.solutions5d.verification.VerificationCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity
{
    PrefManager p;
    Button signup;
    TextInputEditText userId, company, instance;
    String userStr, companyStr, instanceStr, message, userID;
    boolean user_valid=false;
    public static final String mypreference = "SignupPref";
    public static final String CID = "companyid";
    public static final String UID = "emailKey";
    public static final String INST = "name";
    public static final String VERIFYCODE = "false";
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final SharedPreferences sharedpreferences, prefLockError;
        final ProgressDialog progressDialog = new ProgressDialog(this);

        count=0;
        signup=(Button)findViewById(R.id.signup);
        userId=(TextInputEditText)findViewById(R.id.SignUp_userid);
        company=(TextInputEditText)findViewById(R.id.SignUp_company);
        instance=(TextInputEditText)findViewById(R.id.SignUp_instance);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        prefLockError=getSharedPreferences("LockError",Context.MODE_PRIVATE);


     //   Toast.makeText(this, "counter="+prefLockError.getInt("lockCounter",0), Toast.LENGTH_SHORT).show();


        if(prefLockError.getInt("lockCounter",0)==3) {

            Intent intent=new Intent(Signup.this,LockError.class);
            startActivity(intent);
        }

        if (sharedpreferences.contains(CID))
        {
            company.setText(sharedpreferences.getString(CID, "save company"));
        }
        if (sharedpreferences.contains(UID))
        {
            userId.setText(sharedpreferences.getString(UID, "save user id"));

        }
        if (sharedpreferences.contains(INST))
        {
            instance.setText(sharedpreferences.getString(INST, "save instance"));
            if(sharedpreferences.getString(VERIFYCODE,"false").contains("true")) {
                Intent intent = new Intent(Signup.this, LoginCardOverlap.class);
                intent.putExtra("userID", sharedpreferences.getString(UID, "save user id"));
                intent.putExtra("instance", sharedpreferences.getString(INST, "save instance"));
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup.this, VerificationCode.class);
                intent.putExtra("emailKey", sharedpreferences.getString(UID, "save user id"));
                intent.putExtra("instance", sharedpreferences.getString(INST, "save instance"));
                intent.putExtra("companyID", sharedpreferences.getString(CID, "save company"));
                startActivity(intent);
                finish();
            }

        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userStr=userId.getText().toString();
                companyStr=company.getText().toString();
                instanceStr=instance.getText().toString();
                //  Toast.makeText(Signup.this, "data="+userStr+companyStr+instanceStr, Toast.LENGTH_SHORT).show();

                RequestQueue MyRequestQueue = Volley.newRequestQueue(Signup.this);
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/verifiyuserfirsttime/"+userStr+"/"+companyStr;
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                        try
                        {
                            JSONObject data = new JSONObject(response.toString());
                            user_valid = data.getBoolean("valid_user");
                            message = data.getString("message");
                            userID=data.getString("user_id");

                            if(user_valid==true)
                            {
                                user_valid=false;

                                //-----------------sharer ppref


                                /*SharedPreferences sharedPreferencesLogin = getBaseContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

                                if (!sharedPreferencesLogin.getString("Email", "").isEmpty())
                                {
                                    Intent intent = new Intent(getBaseContext(), ProfilePurple.class);//Listviewactivity if there is data in user
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Intent intent = new Intent(getBaseContext(), LoginCardOverlap.class);//it is empity so have to login
                                    startActivity(intent);
                                    finish();
                                }*/
                                if ( progressDialog.isShowing())
                                    progressDialog.hide();
                                Intent intent=new Intent(Signup.this, VerificationCode.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("instance",instanceStr);
                                intent.putExtra("companyID",companyStr);
                                intent.putExtra("name",userStr);
                                String c = company.getText().toString();
                                String u = userId.getText().toString();
                                String i = instance.getText().toString();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(CID, c);
                                editor.putString(UID, u);
                                editor.putString(INST, i);
                                editor.putString("userID", userID);
                                editor.putString("instance", instanceStr);
                                editor.putString("companyID", companyStr);
                                editor.putString("name",userStr);
                                editor.commit();
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                if ( progressDialog.isShowing())
                                    progressDialog.hide();
                                count++;
                                Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                                if(count==3)
                                {
                                    SharedPreferences.Editor editor = prefLockError.edit();
                                    editor.putInt("lockCounter", count);
                                    editor.commit();
                                    Intent intent=new Intent(Signup.this,LockError.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }
                        catch (JSONException e)
                        {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            Toast.makeText(Signup.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Signup.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.hide();

                    }
                })



                {
                   /*   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("username", userStr); //Add the data you'd like to send to the server.
                        MyData.put("companycode", companyStr);

                   //     Toast.makeText(Signup.this, "in function", Toast.LENGTH_SHORT).show();
                        return MyData;
                    }



                   @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                       params.put("Content-Type", "application/json; charset=utf-8");
                       params.put("User-agent", "My useragent");

                        return params;
                    }
                    */
                };

                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                progressDialog.show();

            }
        });
    }
}
