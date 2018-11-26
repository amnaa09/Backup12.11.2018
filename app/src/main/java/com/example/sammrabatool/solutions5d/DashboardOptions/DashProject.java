package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sammrabatool.solutions5d.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashProject extends AppCompatActivity {
//private static final String TAG="MainActivity";
//private LineChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_project);
        PieChartView pieChartView = (PieChartView) findViewById(R.id.chart);

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE));
        pieData.add(new SliceValue(25, Color.GREEN));
        pieData.add(new SliceValue(10, Color.RED));
        pieData.add(new SliceValue(60, Color.YELLOW));

        PieChartData pieChartData = new PieChartData(pieData);

        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);
//        mChart=(LineChart)findViewById(R.id.linechart2);
////
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(false);
//        ArrayList<Entry> yValues=new ArrayList<>();
//        yValues.add(new Entry(0, (int) 60f));
//        yValues.add(new Entry(1, (int) 60f));
//        yValues.add(new Entry(2, (int) 60f));
//        yValues.add(new Entry(3, (int) 60f));
//        yValues.add(new Entry(4, (int) 60f));
//        yValues.add(new Entry(5, (int) 60f));
//        yValues.add(new Entry(6, (int) 60f));
//        yValues.add(new Entry(7, (int) 60f));
//        yValues.add(new Entry(8, (int) 60f));
//        LineDataSet set1=new LineDataSet(yValues,"Data set 1");
//        set1.setFillAlpha(110);
//        set1.setColor(Color.RED);
//        set1.setLineWidth(3f);
//        set1.setValueTextColor(Color.GREEN);
//        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
//        dataSets.add(set1);
//       LineData data=new LineData();
//
//        mChart.setData(data);
    }
}
