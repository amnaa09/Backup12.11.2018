package com.example.sammrabatool.solutions5d.Activity;

import android.app.ProgressDialog;
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
import com.example.sammrabatool.solutions5d.PrefManager;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.example.sammrabatool.solutions5d.dialog.Agreement;
import com.example.sammrabatool.solutions5d.dialog.LockError;
import com.example.sammrabatool.solutions5d.profile.ProfilePurple;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.verification.VerificationCode;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.sammrabatool.solutions5d.Activity.Signup.mypreference;


public class LoginCardOverlap extends AppCompatActivity {
    private View parent_view;
    private Button signin;
    private TextView link;
    private CheckBox checkBoxRememberMe;
    TextInputEditText uname,password;
    String userID,instanceStr, message, userToken, userName, Passowrd;
    boolean user_valid=false;
    boolean agreement=true;
    public static final String UNAME = "username";
    public static final String TOKEN = "token";
    SharedPreferences Prefs,prefLockError;
    int count, super_user, lg, bg;
    JSONArray recent_activity=null;
    String[] arr;
    LinearLayout login_layout;
    int wrong_attempt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        parent_view = findViewById(android.R.id.content);
        Tools.setSystemBarColor(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        login_layout=(LinearLayout) findViewById(R.id.login_layout) ;



        prefLockError=getSharedPreferences("LockError",Context.MODE_PRIVATE);

        if(prefLockError.getInt("lockCounter",0)>=3) {

            Intent intent=new Intent(LoginCardOverlap.this,LockError.class);
            startActivity(intent);
            finish();
        }
        final SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Prefs = this.getSharedPreferences("com.example.sammrabatool.solutions5d.dialog", Context.MODE_PRIVATE);
       final String keyTutorial = Prefs.getString("keyTutorial", "NullTutorial");
        final boolean agreement = Prefs.getBoolean("agreement", false);
     //   Toast.makeText(this, "agrrement="+keyTutorial, Toast.LENGTH_SHORT).show();
      //  agreement= getIntent().getStringExtra("agreement");
        signin=(Button)findViewById(R.id.signin);
        uname=(TextInputEditText)findViewById(R.id.uname);
        password=(TextInputEditText)findViewById(R.id.password);
        password.requestFocus();


        final SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);
        final SharedPreferences sharedprefLogin = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        userID=sharedprefSignup.getString("userID", "user id");//getIntent().getStringExtra("userID");
        instanceStr=sharedprefSignup.getString("instance", "instance");//getIntent().getStringExtra("instance");
        userName=sharedprefSignup.getString("uname","user name");
        userToken=sharedprefSignup.getString("token","token");
        uname.setText(sharedprefSignup.getString("emailKey", "email"));
        super_user=sharedprefSignup.getInt("super_user", 0);
        Passowrd=sharedprefLogin.getString("Password", "");
//        String encodePassword="";
  //      try {
    //        encodePassword = URLEncoder.encode(Passowrd, "UTF-8");
      //  }catch(UnsupportedEncodingException e){
        //    Toast.makeText(this, "Error: Please try again", Toast.LENGTH_SHORT).show();
        //}
//        Password=sharedprefLogin.getString("Password", "");

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

        if (!sharedPreferencesLogin.getString("Email", "").isEmpty() &&  !(keyTutorial.contains("NullTutorial")) && agreement==true) {
            login_layout.setVisibility(LinearLayout.GONE);
            RequestQueue MyRequestQueue = Volley.newRequestQueue(LoginCardOverlap.this);


         //   Toast.makeText(this, "pass if1=" + Passowrd, Toast.LENGTH_SHORT).show();
            String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/verifyuser/" + userID + "/" + Passowrd + "/" + userName;
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
                        wrong_attempt=data.getInt("wrong_attempt");
                        if (user_valid == true && wrong_attempt < 3) {
                            message = data.getString("message");
                            userID = data.getString("user_id");
                            userToken = data.getString("user_token");
                            wrong_attempt=data.getInt("wrong_attempt");
                            super_user = data.getInt("superuser");
                            lg = data.getInt("lg");
                            bg = data.getInt("bg");
                            if (super_user == 1) {
                                recent_activity = data.getJSONArray("recentactivity");
                                arr = new String[recent_activity.length()];
                                for (int i = 0; i < recent_activity.length(); i++)
                                    arr[i] = recent_activity.getString(i);
                            }


                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(TOKEN, userToken);
                            editor.putString(UNAME, userName);
                            editor.putString("token", userToken);
                            editor.putString("uname", userName);
                            editor.putInt("super_user", super_user);
                            editor.commit();

                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            Intent intent = new Intent(getBaseContext(), DashboardGridFab.class);//Listviewactivity if there is data in user
                            intent.putExtra("userID", userID);
                            intent.putExtra("token", userToken);
                            intent.putExtra("instance", instanceStr);
                            intent.putExtra("name", userName);
                            intent.putExtra("super_user", super_user);
                            intent.putExtra("lg", lg);
                            intent.putExtra("bg", bg);
                            if (super_user == 1) {
                                if (recent_activity != null)
                                    intent.putExtra("length", recent_activity.length());
                                intent.putExtra("recent_activity", arr);
                            }
                            //     Toast.makeText(LoginCardOverlap.this, "from and statement sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        } else {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            message = data.getString("message");
                            Toast.makeText(LoginCardOverlap.this, message, Toast.LENGTH_SHORT).show();
                            if (wrong_attempt >= 3) {
                                SharedPreferences.Editor editor = prefLockError.edit();
                                editor.putInt("lockCounter", wrong_attempt);
                                editor.commit();
                                Intent intent = new Intent(LoginCardOverlap.this, LockError.class);
                                startActivity(intent);
                                finish();

                            }
                        }
                    } catch (JSONException e) {
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                        e.printStackTrace();
                        //
                        //                            //  instance.setText("error= " + e.getMessage());
                        Toast.makeText(LoginCardOverlap.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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


        }
        else
        {
            if( (keyTutorial.contains("PROMPTED")) && agreement==false) {

                SharedPreferences preferences =getSharedPreferences("SignupPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                SharedPreferences.Editor editor2 = Prefs.edit();
                editor2.putString("keyTutorial","NullTutorial");
                editor2.commit();
                Intent intent=new Intent(getBaseContext(),Signup.class);
                startActivity(intent);
                finish();

            }

            //    Intent intent = new Intent(getBaseContext(), .class);//it is empity so have to login
             //   startActivity(intent);
              //  finish();
        }

        /*if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
            // startLoginActivity();

        }*/
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                userName = uname.getText().toString();
                Passowrd = password.getText().toString();
                try {
                    String encodePassword = URLEncoder.encode(Passowrd, "UTF-8");
                   // Toast.makeText(LoginCardOverlap.this, "pass signin=" + encodePassword, Toast.LENGTH_SHORT).show();
                    RequestQueue MyRequestQueue = Volley.newRequestQueue(LoginCardOverlap.this);
                    String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/verifyuser/" + userID + "/" + encodePassword + "/" + userName;
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
                                wrong_attempt=data.getInt("wrong_attempt");
                                if (user_valid == true && wrong_attempt<3) {
                                    message = data.getString("message");
                                    userID = data.getString("user_id");
                                    userToken = data.getString("user_token");
                                    super_user = data.getInt("superuser");
                                    lg = data.getInt("lg");
                                    bg = data.getInt("bg");
                                    if (super_user == 1) {
                                        recent_activity = data.getJSONArray("recentactivity");
                                        arr = new String[recent_activity.length()];
                                        for (int i = 0; i < recent_activity.length(); i++)
                                            arr[i] = recent_activity.getString(i);
                                    }


                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(TOKEN, userToken);
                                    editor.putString(UNAME, userName);
                                    editor.putString("token", userToken);
                                    editor.putString("uname", userName);
                                    editor.putInt("super_user", super_user);
                                    editor.commit();

                                } else {

                                    message = data.getString("message");
                                }
                                //  tx.setText("response== " + name+ age);
                                //    Toast.makeText(Signup.this, "result="+user_valid, Toast.LENGTH_SHORT).show();
                                //   company.setText(name);
                                //     userId.setText(age);
                                if (user_valid == true && wrong_attempt<3) {
                                    user_valid = false;
                                    //==============shared pref
                                    attemptLogin();

                                    if ((keyTutorial.contains("NullTutorial"))) {

                                        if (progressDialog.isShowing())
                                            progressDialog.hide();
                                        Intent intent = new Intent(LoginCardOverlap.this, Agreement.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("token", userToken);
                                        intent.putExtra("instance", instanceStr);
                                        intent.putExtra("name", userName);

                                        intent.putExtra("super_user", super_user);
                                        intent.putExtra("lg", lg);
                                        intent.putExtra("bg", bg);
                                        if (super_user == 1) {
                                            intent.putExtra("length", recent_activity.length());
                                            intent.putExtra("recent_activity", arr);
                                        }

                                        //                                 Toast.makeText(LoginCardOverlap.this, "from if sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        if (progressDialog.isShowing())
                                            progressDialog.hide();
                                        Intent intent = new Intent(LoginCardOverlap.this, DashboardGridFab.class);//Listviewactivity if there is data in user
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("token", userToken);
                                        intent.putExtra("instance", instanceStr);
                                        intent.putExtra("name", userName);

                                        intent.putExtra("super_user", super_user);
                                        intent.putExtra("lg", lg);
                                        intent.putExtra("bg", bg);
                                        if (super_user == 1) {
                                            intent.putExtra("length", recent_activity.length());
                                            intent.putExtra("recent_activity", arr);
                                        }

                                        //                               Toast.makeText(LoginCardOverlap.this, "from else sending to agreement name=" + userName + "token=" + userToken, Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    }
                                    //  finish();
                                } else {
                                    if (progressDialog.isShowing())
                                        progressDialog.hide();
                                    Toast.makeText(LoginCardOverlap.this, message, Toast.LENGTH_SHORT).show();
                                    if (wrong_attempt >= 3) {
                                        SharedPreferences.Editor editor = prefLockError.edit();
                                        editor.putInt("lockCounter", wrong_attempt);
                                        editor.commit();
                                        Intent intent = new Intent(LoginCardOverlap.this, LockError.class);
                                        startActivity(intent);
                                        finish();

                                    }

                                }
                            } catch (JSONException e) {
                                if (progressDialog.isShowing())
                                    progressDialog.hide();
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
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                        }
                    });

                    MyStringRequest.setShouldCache(false);
                    SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

                    if (userName.equals(sharedprefSignup.getString("emailKey", ""))) {
                        MyRequestQueue.add(MyStringRequest);
                    } else {
                        Toast.makeText(LoginCardOverlap.this, "This Username was not registered", Toast.LENGTH_LONG).show();
                    }


                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Loading...");
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();


                } catch (UnsupportedEncodingException e) {

                }
            }
        });

    }


    private void attemptLogin(){
        // Reset errors.
        uname.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = uname.getText().toString();
        String passwordd = password.getText().toString();
        String encodePassword="";
        try {
        encodePassword = URLEncoder.encode(passwordd, "UTF-8");
           // Toast.makeText(this, "attempt login pass="+encodePassword, Toast.LENGTH_SHORT).show();
        }catch(UnsupportedEncodingException e){
        Toast.makeText(this, "Error: Please try again", Toast.LENGTH_SHORT).show();
        }
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(encodePassword) && !isPasswordValid(encodePassword))
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
                saveLoginDetails(email, encodePassword);
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


