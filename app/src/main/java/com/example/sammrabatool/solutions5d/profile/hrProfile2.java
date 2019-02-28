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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.OTL.CircleTransform;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.squareup.picasso.Picasso;

public class hrProfile2 extends AppCompatActivity {
    TextView t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, name1, hire_date,job_title;
    ImageView pic1;
    String passNum, passIssue, passexpire, visaNum, visaIssue, visaExpire, laborNum,
            laborIssue, laborExpire, nationalId, nationalIdIssue, nationalIdExpire, name, hireDate, job, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrprofile_new2);


        name = getIntent().getStringExtra("name");
        hireDate = getIntent().getStringExtra("hireDate");
        job = getIntent().getStringExtra("job");
        pic=getIntent().getStringExtra("pic");
        passNum = getIntent().getStringExtra("passNum");
        passIssue = getIntent().getStringExtra("passIssue");
        passexpire = getIntent().getStringExtra("passexpire");
        visaNum = getIntent().getStringExtra("visaNum");
        visaIssue = getIntent().getStringExtra("visaIssue");
        visaExpire = getIntent().getStringExtra("visaExpire");
        nationalId = getIntent().getStringExtra("nationalId");
        nationalIdIssue = getIntent().getStringExtra("nationalIdIssue");
        nationalIdExpire = getIntent().getStringExtra("nationalIdExpire");
        laborNum = getIntent().getStringExtra("laborNum");
        laborIssue = getIntent().getStringExtra("laborIssue");
        laborExpire = getIntent().getStringExtra("laborExpire");


        name1=(TextView) findViewById(R.id.name1);
        pic1=(ImageView) findViewById(R.id.pic1);
        hire_date=(TextView) findViewById(R.id.hireDate);
        job_title=(TextView)findViewById(R.id.job);
        t16 = (TextView) findViewById(R.id.hrtext16);
        t17 = (TextView) findViewById(R.id.hrtext17);
        t18 = (TextView) findViewById(R.id.hrtext18);
        t19 = (TextView) findViewById(R.id.hrtext19);
        t20 = (TextView) findViewById(R.id.hrtext20);
        t21 = (TextView) findViewById(R.id.hrtext21);
        t22 = (TextView) findViewById(R.id.hrtext22);
        t23 = (TextView) findViewById(R.id.hrtext23);
        t24 = (TextView) findViewById(R.id.hrtext24);
        t25 = (TextView) findViewById(R.id.hrtext25);
        t26 = (TextView) findViewById(R.id.hrtext26);
        t27 = (TextView) findViewById(R.id.hrtext27);

        if(pic!=null || pic!="")
        {
            int SDK_INT = Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here

                Picasso.get().load(pic).transform(new CircleTransform()).into(pic1);

            }
        }
        name1.setText(name);
        hire_date.setText(hireDate);
        job_title.setText(job);
        t16.setText(passNum);
        t17.setText(passIssue);
        t18.setText(passexpire);
        t19.setText(visaNum);
        t20.setText(visaIssue);
        t21.setText(visaExpire);
        t22.setText(nationalId);
        t23.setText(nationalIdIssue);
        t24.setText(nationalIdExpire);
        t25.setText(laborNum);
        t26.setText(laborIssue);
        t27.setText(laborExpire);

        initToolbar();
    }
    private void initToolbar () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Document Information");
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
            Intent intent = new Intent(hrProfile2.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
