package com.example.sammrabatool.solutions5d.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.example.sammrabatool.solutions5d.profile.ProfilePurple;
import com.example.sammrabatool.solutions5d.utils.Tools;


public class DashboardGridFab extends AppCompatActivity {

    FloatingActionButton profile, team, fyi, fyr, request;
    String   instanceStr, message, userID, token, details, image, name="Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_grid_fab);
        profile = (FloatingActionButton ) findViewById(R.id.profile_button);
        team = (FloatingActionButton ) findViewById(R.id.teammates_button);
        fyi = (FloatingActionButton ) findViewById(R.id.fyi_button);
        fyr = (FloatingActionButton ) findViewById(R.id.fyr_button);
        request = (FloatingActionButton ) findViewById(R.id.request_button);

        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);

        userID=sharedprefSignup.getString("userID", "save user id");//getIntent().getStringExtra("userID");
        instanceStr=sharedprefSignup.getString("instance", "save user id");//getIntent().getStringExtra("instance");

         name=sharedprefSignup.getString("emailKey", "save user id");
        token=sharedprefSignup.getString("token", "save user id");



        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");
        name=getIntent().getStringExtra("name");
        initToolbar();

        profile.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DashboardGridFab.this, ProfilePurple.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                startActivity(intent);



            }
        });

        team.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {




            }
        });

        fyi.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardGridFab.this, ListMultiSelection.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("type", "1");
                startActivity(intent);


            }
        });

        fyr.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DashboardGridFab.this, ListMultiSelection.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("type", "2");
                startActivity(intent);


            }
        });

        request.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {




            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

        getSupportActionBar().setTitle("Hi,"+name);
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
            Toast.makeText(this,"logout is clicked",Toast.LENGTH_LONG).show();
            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent=new Intent(DashboardGridFab.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
