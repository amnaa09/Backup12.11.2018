package com.example.sammrabatool.solutions5d.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;

public class Agreement extends AppCompatActivity {
    private static final String TAG ="yes" ;
    private SharedPreferences Prefs;
    String userID,instanceStr, message, userName,token,name,activity, recent_activity[];
    int lg, bg, super_user, length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);


        Prefs = getSharedPreferences("com.example.sammrabatool.solutions5d.dialog", Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

        userID=sharedprefSignup.getString("userID", "save user id");//getIntent().getStringExtra("userID");
        instanceStr=sharedprefSignup.getString("instance", "save user id");//getIntent().getStringExtra("instance");

        name=sharedprefSignup.getString("emailKey", "save user id");
        token=sharedprefSignup.getString("token", "save user id");
    //    Toast.makeText(Agreement.this, "in agreement from pref  activity="+activity+" name="+name+"token="+token, Toast.LENGTH_SHORT).show();

        if(extras!=null) {
            userID = extras.getString("userID");
            instanceStr = extras.getString("instance");
            token = extras.getString("token");
            name = extras.getString("name");
            lg=extras.getInt("lg");
            bg=extras.getInt("bg");
            super_user=extras.getInt("super_user");
            if(super_user==1) {
                recent_activity = extras.getStringArray("recent_activity");
                length = extras.getInt("length");
            }

          // Toast.makeText(Agreement.this, "in agreement  activity="+activity+" name="+name+"token="+token, Toast.LENGTH_SHORT).show();
        }

        if (promptTutorial()) {

            showTermServicesDialog();

        }

    }

    private void showTermServicesDialog () {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_term_of_services);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((Button) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  boolean AgreementAccept = Prefs.getBoolean("agreement", true);
                SharedPreferences.Editor editor = Prefs.edit();
                // Log what are we saving in the shared Prefs

                editor.putBoolean("agreement", true);
                editor.commit();
                Intent intent = new Intent(Agreement.this, DashboardGridFab.class);

                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("name", name);

                intent.putExtra("super_user",super_user);
                intent.putExtra("lg",lg);
                intent.putExtra("bg",bg);
                if(super_user==1) {
                    intent.putExtra("length", length);
                    intent.putExtra("recent_activity", recent_activity);
                }
            //    Toast.makeText(getApplicationContext(),"Read: " + token,Toast.LENGTH_SHORT).show();
               startActivity(intent);
                finish();
                // Toast.makeText(getApplicationContext(), "Button Accept Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  boolean AgreementAccept = Prefs.getBoolean("agreement", false);
                SharedPreferences.Editor editor = Prefs.edit();
                // Log what are we saving in the shared Prefs

                editor.putBoolean("agreement", false);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Please accept the terms of service first.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Agreement.this, LoginCardOverlap.class);
                intent.putExtra("userID","");
                intent.putExtra("instance",instanceStr);
                intent.putExtra("agreement","decline");
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    // The function that decides if you need to prompt the dialog window
    private boolean promptTutorial() {
        // Check fo saved value in Shared preference for key: keyTutorial return "NullTutorial" if nothing found
        String keyTutorial = Prefs.getString("keyTutorial", "NullTutorial");

        // Log what we found in shared preference
        Log.d(TAG, "Shared Pref read: [keyTutorial: " + keyTutorial + "]");

        if (keyTutorial.contains("NullTutorial")) {
            // if nothing found save a new value "PROMPTED" for the key: keyTutorial
            // to save it in shared prefs just call our saveKey function
            saveKey("keyTutorial", "PROMPTED");
            return true;
        }
        // if some value was found for this key we already propted this window some time in the past
        // no need to prompt it again
        return false;
    }

    private void saveKey(String key, String value) {
        SharedPreferences.Editor editor = Prefs.edit();
        // Log what are we saving in the shared Prefs
        Log.d(TAG, "Shared Prefs Write [" + key + ":" + value + "]");
        editor.putString(key, value);
        editor.commit();
    }

}
