package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;

public class DashboardOptions extends AppCompatActivity {
   // CardView finnace,isure,pro,projec;
    LinearLayout finance,insurance,procurement,project,hrpay,profile,property,sale;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_options);
        profile=(LinearLayout)findViewById(R.id.profile_card);
        property=(LinearLayout)findViewById(R.id.PROPERTY);
hrpay=(LinearLayout)findViewById(R.id.HR_card);
sale=(LinearLayout)findViewById(R.id.SALE_card);
        finance=(LinearLayout) findViewById(R.id.dash_finance);
        insurance=(LinearLayout) findViewById(R.id.dash_insurance);
        project=(LinearLayout)findViewById(R.id.dash_project);
        procurement=(LinearLayout)findViewById(R.id.dash_procurement);

        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(DashboardOptions.this, "click fin", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashboardOptions.this, DashFinance.class);
                startActivity(intent);
            }
        });
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(DashboardOptions.this, "click fin", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashboardOptions.this, DashInsurance.class);
                startActivity(intent);
            }
        });


        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(DashboardOptions.this, "click", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashboardOptions.this, DashProject.class);
                startActivity(intent);
            }
        });
       // pro=(CardView)findViewById(R.id.procure);


        procurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Toast.makeText(DashboardOptions.this, "click", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashboardOptions.this, DashProcurement.class);
                startActivity(intent);
            }
        });
        hrpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardOptions.this, HRpayroll.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardOptions.this, DashProfile.class);
                startActivity(intent);
            }
        });
        property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardOptions.this, DashProperty.class);
                startActivity(intent);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardOptions.this, DashSale.class);
                startActivity(intent);
            }
        });
      //  isure=(CardView)findViewById(R.id.insurrance);

        initToolbar();






    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.indigo_500), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Tools.setSystemBarColor(this, android.R.color.white);
//        Tools.setSystemBarLight(this);
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
        } else if (id == R.id.logout) {
            Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_LONG).show();
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(DashboardOptions.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
