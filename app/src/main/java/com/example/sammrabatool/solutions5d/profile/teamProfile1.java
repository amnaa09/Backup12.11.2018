package com.example.sammrabatool.solutions5d.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class teamProfile1 extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15,empname, bst1, yost1;
    String name, employee_number, hireDate, gender, dob, maritalStatus, nationality,
            email, officeNum, org, job,grade, location, status,manager,yos,basicsal,pic;
    ImageView empPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrprofile_team1);


        gender = getIntent().getStringExtra("gender");
        maritalStatus = getIntent().getStringExtra("maritalStatus");
        email = getIntent().getStringExtra("email");
        hireDate = getIntent().getStringExtra("hireDate");
        dob = getIntent().getStringExtra("dob");
        nationality = getIntent().getStringExtra("nationality");
        officeNum = getIntent().getStringExtra("officeNum");
        employee_number = getIntent().getStringExtra("employee_number");
        manager = getIntent().getStringExtra("manager");
        job = getIntent().getStringExtra("job");
        grade = getIntent().getStringExtra("grade");
        org = getIntent().getStringExtra("org");
        location = getIntent().getStringExtra("location");
        status = getIntent().getStringExtra("status");
        name = getIntent().getStringExtra("name");
        yos = getIntent().getStringExtra("yearofservice");
        basicsal = getIntent().getStringExtra("basicsalary");
        pic = getIntent().getStringExtra("pic");


        empname=(TextView) findViewById(R.id.namet1);
        empPic=(ImageView) findViewById(R.id.pic1);
        bst1=(TextView) findViewById(R.id.bst1);
        yost1=(TextView) findViewById(R.id.yost1);


        t1 = (TextView) findViewById(R.id.hrtext1);
        t2 = (TextView) findViewById(R.id.hrtext2);
        t3 = (TextView) findViewById(R.id.hrtext3);
        t4 = (TextView) findViewById(R.id.hrtext4);
        t5 = (TextView) findViewById(R.id.hrtext5);
        t6 = (TextView) findViewById(R.id.hrtext6);
        t7 = (TextView) findViewById(R.id.hrtext7);
        t8 = (TextView) findViewById(R.id.hrtext8);
        t9 = (TextView) findViewById(R.id.hrtext9);
        t10 = (TextView) findViewById(R.id.hrtext10);
        t11 = (TextView) findViewById(R.id.hrtext11);
        t12 = (TextView) findViewById(R.id.hrtext12);
        t13 = (TextView) findViewById(R.id.hrtext13);
        t14 = (TextView) findViewById(R.id.hrtext14);
        t15 = (TextView) findViewById(R.id.hrtext15);

        bst1.setText(basicsal);
        yost1.setText(yos);
        empname.setText(name);

        if(pic!=null || pic!="")
        {
            int SDK_INT = Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here

                Picasso.get().load(pic).transform(new com.example.sammrabatool.solutions5d.OTL.CircleTransform()).into(empPic);

            }
        }
        t1.setText(name);
        t2.setText(gender);
        t3.setText(maritalStatus);
        t4.setText(email);
        t5.setText(hireDate);
        t6.setText(dob);
        t7.setText(nationality);
        t8.setText(officeNum);
        t9.setText(employee_number);
        t10.setText(manager);
        t11.setText(job);
        t12.setText(grade);
        t13.setText(org);
        t14.setText(location);
        t15.setText(status);



        initToolbar();
    }
    private void initToolbar () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Information");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_LONG).show();
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(teamProfile1.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
