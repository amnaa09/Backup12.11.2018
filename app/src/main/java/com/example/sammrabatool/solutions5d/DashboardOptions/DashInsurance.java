package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Funnel;
import com.example.sammrabatool.solutions5d.R;


import java.util.ArrayList;
import java.util.List;

public class DashInsurance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_insurance);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Funnel funnel = AnyChart.funnel();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Website Visits", 528756));
        data.add(new ValueDataEntry("Downloads", 164052));
        data.add(new ValueDataEntry("Valid Contacts", 112167));
        data.add(new ValueDataEntry("Interested to Buy", 79128));
        data.add(new ValueDataEntry("Purchased", 79128));

        funnel.data(data);

        funnel.margin(new String[] { "10", "20%", "10", "20%" });
        funnel.baseWidth("70%")
                .neckWidth("17%");

        funnel.labels()
                .position("outsideleft")
                .format("{%X} - {%Value}");

        funnel.animation(true);

        anyChartView.setChart(funnel);
    }
}
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.sammrabatool.solutions5d.R;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//
//import java.util.ArrayList;
//
//public class DashInsurance extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dash_options_insurance);
//        LineChart lineChart = (LineChart) findViewById(R.id.linechart);
//
//        ArrayList<String> xAXES = new ArrayList<>();
//        ArrayList<Entry> yAXESsin = new ArrayList<>();
//        ArrayList<Entry> yAXEScos = new ArrayList<>();
//        double x = 0;
//        int numDataPoints = 1000;
//        for (int i = 0; i < numDataPoints; i++) {
//            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
//            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
//            x = x + 0.1;
//            yAXESsin.add(new Entry(sinFunction, i));
//            yAXEScos.add(new Entry(cosFunction, i));
//            xAXES.add(i, String.valueOf(x));
//        }
//        String[] xaxes = new String[xAXES.size()];
//        for (int i = 0; i < xAXES.size(); i++) {
//            xaxes[i] = xAXES.get(i).toString();
//        }
//
//        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
//
//        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
//        lineDataSet1.setDrawCircles(false);
//        lineDataSet1.setColor(Color.BLUE);
//
//        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin, "sin");
//        lineDataSet2.setDrawCircles(false);
//        lineDataSet2.setColor(Color.RED);
//
//        lineDataSets.add(lineDataSet1);
//        lineDataSets.add(lineDataSet2);
//
//        lineChart.setData(new LineData(xaxes, lineDataSets));
//
//        lineChart.setVisibleXRangeMaximum(65f);
//    }
//}