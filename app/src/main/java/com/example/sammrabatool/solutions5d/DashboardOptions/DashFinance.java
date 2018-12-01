package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DashFinance extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_finance);
        initToolbar();

        BarChart barChart = (BarChart) findViewById(R.id.barchart1);
        BarChart barChart2 = (BarChart) findViewById(R.id.barchart2);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Set Bar Chart Description");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);


        barChart2.setData(data); // set the data and list of lables into chart

        barChart2.setDescription("Set Bar Chart Description");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart2.animateY(5000);

//        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.barchart);
//
//        // create BarEntry for Bar Group 1
//        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
//        bargroup1.add(new BarEntry(8f, 0));
//        bargroup1.add(new BarEntry(2f, 1));
//        bargroup1.add(new BarEntry(5f, 2));
//        bargroup1.add(new BarEntry(20f, 3));
//        bargroup1.add(new BarEntry(15f, 4));
//        bargroup1.add(new BarEntry(19f, 5));
//
//        // create BarEntry for Bar Group 1
//        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
//        bargroup2.add(new BarEntry(6f, 0));
//        bargroup2.add(new BarEntry(10f, 1));
//        bargroup2.add(new BarEntry(5f, 2));
//        bargroup2.add(new BarEntry(25f, 3));
//        bargroup2.add(new BarEntry(4f, 4));
//        bargroup2.add(new BarEntry(17f, 5));
//
//        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");  // creating dataset for group1
//
//        //barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Brand 2"); // creating dataset for group1
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("2016");
//        labels.add("2015");
//        labels.add("2014");
//        labels.add("2013");
//        labels.add("2012");
//        labels.add("2011");
//
//        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
//        BarData data = new BarData(labels, dataSets); // initialize the Bardata with argument labels and dataSet
//        barChart.setData(data);
//
//
//
//
//        barChart.setDescription("Set Bar Chart Description");  // set the description
//
//        barChart.animateY(5000);

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
            Intent intent = new Intent(DashFinance.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
