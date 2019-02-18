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

public class teamProfile2 extends AppCompatActivity {
    TextView t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27,empname,yost,basicsalt;
    String passNum, passIssue, passexpire, visaNum, visaIssue, visaExpire, laborNum,
            laborIssue, laborExpire, nationalId, nationalIdIssue, nationalIdExpire,yos,basicsal,pic, name;
    ImageView empPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrprofile_team2);

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

        name = getIntent().getStringExtra("name");
        yos = getIntent().getStringExtra("yearofservice");
        basicsal = getIntent().getStringExtra("basicsalary");
        pic = getIntent().getStringExtra("pic");

        yost=(TextView) findViewById(R.id.yos2);
        basicsalt=(TextView) findViewById(R.id.basicsal2);
        empname=(TextView) findViewById(R.id.empname2) ;
        empPic=(ImageView) findViewById(R.id.image2);



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

        String temp=yost.getText().toString();
        yost.setText(temp+yos);
        String temp1=basicsalt.getText().toString();
        basicsalt.setText(temp1+basicsal);
        empname.setText(name);

        Picasso.get().load(pic).transform(new CircleTransform()).into(empPic);

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

    }
}
