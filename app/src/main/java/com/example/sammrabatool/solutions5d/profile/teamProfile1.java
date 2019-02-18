package com.example.sammrabatool.solutions5d.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class teamProfile1 extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15,empname,yost,basicsalt;
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

        yost=(TextView) findViewById(R.id.yos);
        basicsalt=(TextView) findViewById(R.id.basicsal);
        empname=(TextView) findViewById(R.id.empname) ;
        empPic=(ImageView) findViewById(R.id.image);


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

        String temp=yost.getText().toString();
        yost.setText(temp+yos);
        String temp1=basicsalt.getText().toString();
        basicsalt.setText(temp1+basicsal);
        empname.setText(name);

        Picasso.get().load(pic).transform(new CircleTransform()).into(empPic);

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



    }
}
