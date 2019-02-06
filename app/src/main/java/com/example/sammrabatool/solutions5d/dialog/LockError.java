package com.example.sammrabatool.solutions5d.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.Signup;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;

public class LockError extends AppCompatActivity {
    private static final String TAG ="yes" ;
    private SharedPreferences Prefs;
    String userID,instanceStr, message, userName,token,name,activity;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_error);


        Prefs = getSharedPreferences("LockError", Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

    //    userID=sharedprefSignup.getString("userID", "save user id");//getIntent().getStringExtra("userID");
     //   instanceStr=sharedprefSignup.getString("instance", "save user id");//getIntent().getStringExtra("instance");

    //    name=sharedprefSignup.getString("emailKey", "save user id");
    //    token=sharedprefSignup.getString("token", "save user id");
    //    Toast.makeText(Agreement.this, "in agreement from pref  activity="+activity+" name="+name+"token="+token, Toast.LENGTH_SHORT).show();
        count=Prefs.getInt("lockCounter",0);
       // Toast.makeText(this, "lockcounter="+count, Toast.LENGTH_SHORT).show();



        if(extras!=null) {
            userID = extras.getString("userID");
            instanceStr = extras.getString("instance");
            token = extras.getString("token");
            name = extras.getString("name");
            activity = extras.getString("activity");
          // Toast.makeText(Agreement.this, "in agreement  activity="+activity+" name="+name+"token="+token, Toast.LENGTH_SHORT).show();
        }

        if (promptTutorial()) {

            showTermServicesDialog();

        }

    }

    private void showTermServicesDialog () {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_lock_error);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    /*    ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LockError.this, Signup.class);

               // intent.putExtra("userID",userID);
              //  intent.putExtra("token",token);
              //  intent.putExtra("instance", instanceStr);
             //   intent.putExtra("name", name);
            //    Toast.makeText(getApplicationContext(),"Read: " + token,Toast.LENGTH_SHORT).show();
               startActivity(intent);
             //   finish();
                // Toast.makeText(getApplicationContext(), "Button Accept Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Decline Clicked", Toast.LENGTH_SHORT).show();
            }
        });    */

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    // The function that decides if you need to prompt the dialog window
    private boolean promptTutorial() {
        // Check fo saved value in Shared preference for key: keyTutorial return "NullTutorial" if nothing found
        int keyTutorial = Prefs.getInt("lockCounter", count);

        // Log what we found in shared preference
        Log.d(TAG, "Shared Pref read: [keyTutorial: " + keyTutorial + "]");

        if (keyTutorial==3) {
            // if nothing found save a new value "PROMPTED" for the key: keyTutorial
            // to save it in shared prefs just call our saveKey function
            saveKey("lockCounter",count);
          //  Toast.makeText(this, "in if counter="+count, Toast.LENGTH_SHORT).show();
            return true;
        }
        // if some value was found for this key we already propted this window some time in the past
        // no need to prompt it again
        return false;
    }

    private void saveKey(String key, int value) {
        SharedPreferences.Editor editor = Prefs.edit();
        // Log what are we saving in the shared Prefs
        Log.d(TAG, "Shared Prefs Write [" + key + ":" + value + "]");
        editor.putInt(key, value);
        editor.commit();
    }

}
