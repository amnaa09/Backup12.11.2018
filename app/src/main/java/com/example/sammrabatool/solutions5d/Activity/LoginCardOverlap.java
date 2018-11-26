package com.example.sammrabatool.solutions5d.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.sammrabatool.solutions5d.PrefManager;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.example.sammrabatool.solutions5d.dialog.Agreement;
import com.example.sammrabatool.solutions5d.profile.ProfilePurple;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.verification.VerificationCode;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.sammrabatool.solutions5d.Activity.Signup.mypreference;


public class LoginCardOverlap extends AppCompatActivity {
    private View parent_view;
    private Button signin;
    private TextView link;
    private CheckBox checkBoxRememberMe;
    TextInputEditText uname,password;
    String userID,instanceStr, message, userToken, userName, Passowrd;
    boolean user_valid=false;
    public static final String UNAME = "username";
    public static final String TOKEN = "token";
    SharedPreferences Prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        parent_view = findViewById(android.R.id.content);
        Tools.setSystemBarColor(this);
        final SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Prefs = this.getSharedPreferences("com.example.sammrabatool.solutions5d.dialog", Context.MODE_PRIVATE);
       final String keyTutorial = Prefs.getString("keyTutorial", "NullTutorial");
     //   Toast.makeText(this, "agrrement="+keyTutorial, Toast.LENGTH_SHORT).show();
        signin=(Button)findViewById(R.id.signin);
        uname=(TextInputEditText)findViewById(R.id.uname);
        password=(TextInputEditText)findViewById(R.id.password);
        password.requestFocus();


        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

        userID=sharedprefSignup.getString("userID", "save user id");//getIntent().getStringExtra("userID");
        instanceStr=sharedprefSignup.getString("instance", "save user id");//getIntent().getStringExtra("instance");
        userName=sharedprefSignup.getString("uname","save user name");
        userToken=sharedprefSignup.getString("token","save token");
        uname.setText(sharedprefSignup.getString("emailKey", "save user id"));

        password.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        final SharedPreferences sharedPreferencesLogin = getBaseContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        if (!sharedPreferencesLogin.getString("Email", "").isEmpty() &&  !(keyTutorial.contains("NullTutorial")))
        {
            Intent intent = new Intent(getBaseContext(), DashboardGridFab.class);//Listviewactivity if there is data in user
            intent.putExtra("userID",userID);
            intent.putExtra("token",userToken);
            intent.putExtra("instance", instanceStr);
            intent.putExtra("name", userName);
       //     Toast.makeText(LoginCardOverlap.this, "from and statement sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
        else
        {
                /*Intent intent = new Intent(getBaseContext(), LoginCardOverlap.class);//it is empity so have to login
                startActivity(intent);
                finish();*/
        }

        /*if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
            // startLoginActivity();

        }*/
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userName=uname.getText().toString();
                Passowrd=password.getText().toString();

                RequestQueue MyRequestQueue = Volley.newRequestQueue(LoginCardOverlap.this);
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/verifyuser/"+userID+"/"+userName+"/"+Passowrd;
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                        // tx.setText("response: " + response.toString());
                        //  Toast.makeText(Signup.this, "reponse=" + response, Toast.LENGTH_SHORT).show();
                        try
                        {
                            JSONObject data = new JSONObject(response.toString());
                            user_valid = data.getBoolean("valid_user");
                            if(user_valid==true)
                            {
                                message = data.getString("message");
                                userID = data.getString("user_id");
                                userToken = data.getString("user_token");

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(TOKEN, userToken);
                                editor.putString(UNAME,userName);
                                editor.putString("token", userToken);
                                editor.putString("uname", userName);
                                editor.commit();

                            }
                            else
                            {
                                message = data.getString("message");
                            }
                            //  tx.setText("response== " + name+ age);
                            //    Toast.makeText(Signup.this, "result="+user_valid, Toast.LENGTH_SHORT).show();
                            //   company.setText(name);
                            //     userId.setText(age);
                            if(user_valid==true)
                            {
                                user_valid=false;
                                //==============shared pref
                                attemptLogin();

                               if( (keyTutorial.contains("NullTutorial"))) {
                                   Intent intent = new Intent(LoginCardOverlap.this, Agreement.class);
                                   intent.putExtra("userID", userID);
                                   intent.putExtra("token", userToken);
                                   intent.putExtra("instance", instanceStr);
                                   intent.putExtra("name", userName);
                                   intent.putExtra("activity", "login");
  //                                 Toast.makeText(LoginCardOverlap.this, "from if sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_LONG).show();
                                   startActivity(intent);
                               }
                               else
                               {
                                   Intent intent = new Intent(LoginCardOverlap.this, DashboardGridFab.class);//Listviewactivity if there is data in user
                                   intent.putExtra("userID",userID);
                                   intent.putExtra("token",userToken);
                                   intent.putExtra("instance", instanceStr);
                                   intent.putExtra("name", userName);
    //                               Toast.makeText(LoginCardOverlap.this, "from else sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_SHORT).show();
                                   startActivity(intent);
                               }
                              //  finish();
                            }
                            else
                            {
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
                }, new Response.ErrorListener()
                { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                });

                MyStringRequest.setShouldCache(false);
                SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

                if (userName.equals(sharedprefSignup.getString("emailKey","")))
                {
                    MyRequestQueue.add(MyStringRequest);
                }
                else
                {
                    Toast.makeText(LoginCardOverlap.this, "This Username was not registered", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    private void attemptLogin() {
        // Reset errors.
        uname.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = uname.getText().toString();
        String passwordd = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordd) && !isPasswordValid(passwordd))
        {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            uname.setError(getString(R.string.error_field_required));
            focusView = uname;
            cancel = true;
        }
        /*else if (!isEmailValid(email)) {
            uname.setError(getString(R.string.error_invalid_email));
            focusView = uname;
            cancel = true;
        }*/

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            // save data in local shared preferences
            if (checkBoxRememberMe.isChecked())
            {
                saveLoginDetails(email, passwordd);
            }
            //startHomeActivity();
        }
    }

    private void saveLoginDetails(String email, String password)
    {
        new PrefManager(LoginCardOverlap.this).saveLoginDetails(email, password);
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, LoginCardOverlap.class);//linking profile not yet pasted
        intent.putExtra("userID",userID);
        intent.putExtra("token",userToken);
        intent.putExtra("instance", instanceStr);
        intent.putExtra("name", userName);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}


