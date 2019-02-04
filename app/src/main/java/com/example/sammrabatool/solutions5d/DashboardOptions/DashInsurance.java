package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Funnel;
import com.anychart.charts.Pie;
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashInsurance extends AppCompatActivity {
    private Toolbar toolbar;
    String   instanceStr,  userID, token, count1,count2,count3,count4,count5,count6;
    JSONArray countarray_7 = null, countarray_71 = null, countarray_72 = null, countarray_73 = null,countarray_74=null, countarray_8 = null,
            countarray_81 = null, countarray_82 = null, countarray_83 = null,countarray_9=null,countarray_91=null,
            countarray_92=null,countarray_93=null,countarray_94=null,countarray_10=null,countarray_101=null,countarray_102=null,countarray_103=null,
    countarray_11=null,countarray_111=null,countarray_112=null,countarray_12=null,countarray_121=null;
    JSONObject countarray111[],countarray121[];
    double  array7[], array71[],array72[],array74[], array8[], array81[], array82[],array9[],array91[],array92[],array94[],array10[],array101[],array102[],
            array11[],array111a[],array12[],array121a[];
    String array73[], array83[],array93[],array103[],array112b[],array122b[];
    int lg, bg;
    TextView count0, count00;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_insurance);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        count0 = (TextView) findViewById(R.id.count0);
    //    count00 = (TextView) findViewById(R.id.count00);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);

        Toast.makeText(this, "data=" + userID + " " + instanceStr + " " + token + " " + lg + " " + bg, Toast.LENGTH_LONG).show();


        initToolbar();
        final CustomMarkerView mv = new CustomMarkerView(this, R.layout.tv_content);
        final LineChart linechartPayable1 = (LineChart) findViewById(R.id.linechartisurrance1);
        final LineChart linechartPayable2 = (LineChart) findViewById(R.id.linechartisurrance2);
        final BarChart barchartinsurr1 = (BarChart) findViewById(R.id.barchart);
        final BarChart barchartinsurr2 = (BarChart) findViewById(R.id.barchart1);
        final LineChart linechartPayable3 = (LineChart) findViewById(R.id.linechartisurrance3);
        final LineChart linechartPayable4 = (LineChart) findViewById(R.id.linechartisurrance4);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
        String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getinsuranceStatictics/" + bg + "/" + lg + "/" + userID + "/" + token;
        StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response);
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    count1 = data.getString("count_01");
                    count2 = data.getString("count_02");
                    count3 = data.getString("count_03");
                    count4 = data.getString("count_04");
                    count5 = data.getString("count_05");
                    count6 = data.getString("count_06");
                    countarray_7 = data.getJSONArray("count_07");
                    array7 = new double[countarray_7.length()];
                    countarray_71 = countarray_7.getJSONArray(1);
                    array71 = new double[countarray_71.length()];
                    for (int i = 0; i < countarray_71.length(); i++) {
                        array71[i] = countarray_71.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_72 = countarray_7.getJSONArray(2);
                    array72 = new double[countarray_72.length()];
                    for (int i = 0; i < countarray_72.length(); i++) {
                        array72[i] = countarray_72.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_73 = countarray_7.getJSONArray(0);
                    array73 = new String[countarray_73.length()];
                    for (int i = 0; i < countarray_73.length(); i++) {
                        array73[i] = countarray_73.getString(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_74 = countarray_7.getJSONArray(3);
                    array74 = new double[countarray_74.length()];
                    for (int i = 0; i < countarray_74.length(); i++) {
                        array74[i] = countarray_74.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    List<Entry> valsComp1 = new ArrayList<Entry>();
                    List<Entry> valsComp2 = new ArrayList<Entry>();
                    List<Entry> valsComp3 = new ArrayList<Entry>();
                    for (int i = 0; i < countarray_71.length(); i++) {
                        Entry e = new Entry(i, (float) array71[i]);
                        valsComp1.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < countarray_72.length(); i++) {
                        Entry e = new Entry(i, (float) array72[i]);
                        valsComp2.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < countarray_74.length(); i++) {
                        Entry e = new Entry(i, (float) array74[i]);
                        valsComp3.add(e);
//                        arr82[i]=count_82.getDouble(i);
//                          Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    LineDataSet setComp1 = new LineDataSet(valsComp1, "Insurrance");
                    setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setComp1.setColor(R.color.red_500);

                    LineDataSet setComp2 = new LineDataSet(valsComp2, "VAT");
                    setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setComp2.setColor(R.color.green_500);

                    LineDataSet setComp3 = new LineDataSet(valsComp2, "VATT");
                    setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setComp3.setColor(R.color.colorAccent);

                    List<ILineDataSet> line_dataSets1 = new ArrayList<ILineDataSet>();
                    line_dataSets1.add(setComp1);
                    line_dataSets1.add(setComp2);
                    line_dataSets1.add(setComp3);

                    LineData line_data1 = new LineData(line_dataSets1);
                    linechartPayable1.setData(line_data1);
                    linechartPayable1.invalidate(); // refresh

                    IAxisValueFormatter formatter = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array73[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line1 = linechartPayable1.getXAxis();
                    xAxis_line1.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line1.setValueFormatter(formatter);


//...................................count8....................................................//
                    countarray_8 = data.getJSONArray("count_08");
                    array8 = new double[countarray_8.length()];
                    //for(int i=0;i<count_7.length();i++)
                    //  {
                    countarray_81 = countarray_8.getJSONArray(1);
                    array81 = new double[countarray_81.length()];
                    for (int i = 0; i < countarray_81.length(); i++) {
                        array81[i] = countarray_81.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_82 = countarray_8.getJSONArray(2);
                    array82 = new double[countarray_82.length()];
                    for (int i = 0; i < countarray_82.length(); i++) {
                        array82[i] = countarray_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_83 = countarray_8.getJSONArray(0);
                    array83 = new String[countarray_83.length()];
                    for (int i = 0; i < countarray_83.length(); i++) {
                        array83[i] = countarray_83.getString(i);
                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                    }

                    List<Entry> valsCompa = new ArrayList<Entry>();
                    List<Entry> valsCompb = new ArrayList<Entry>();

                    for (int i = 0; i < countarray_81.length(); i++) {
                        Entry e = new Entry(i, (float) array81[i]);
                        valsCompa.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < countarray_82.length(); i++) {
                        Entry e = new Entry(i, (float) array82[i]);
                        valsCompb.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    LineDataSet setCompa = new LineDataSet(valsComp1, "insurrance");
                    setCompa.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setCompa.setColor(R.color.red_500);

                    LineDataSet setCompb = new LineDataSet(valsComp2, "VAT");
                    setCompb.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setCompb.setColor(R.color.green_500);

                    List<ILineDataSet> line_dataSetsa = new ArrayList<ILineDataSet>();
                    line_dataSetsa.add(setCompa);
                    line_dataSetsa.add(setCompb);


                    LineData line_data2 = new LineData(line_dataSets1);
                    linechartPayable2.setData(line_data2);
                    linechartPayable2.invalidate(); // refresh

                    IAxisValueFormatter formatter1 = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array83[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line2 = linechartPayable2.getXAxis();
                    xAxis_line2.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line2.setValueFormatter(formatter);
//..............................count9.........................................................//
                    countarray_9 = data.getJSONArray("count_09");

                    //for(int i=0;i<count_7.length();i++)
                    //  {
                    countarray_91 = countarray_9.getJSONArray(1);
                    array91 = new double[countarray_91.length()];
                    for (int i = 0; i < countarray_91.length(); i++) {
                        array91[i] = countarray_91.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_92 = countarray_9.getJSONArray(2);
                    array92 = new double[countarray_92.length()];
                    for (int i = 0; i < countarray_92.length(); i++) {
                        array92[i] = countarray_92.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr92[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_93 = countarray_9.getJSONArray(0);
                    array93 = new String[countarray_93.length()];
                    for (int i = 0; i < countarray_93.length(); i++) {
                        array93[i] = countarray_93.getString(i);
                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                    }
//                    countarray_94 = countarray_9.getJSONArray(3);
//                    array94 = new double[countarray_94.length()];
//                    for (int i = 0; i < countarray_94.length(); i++) {
//                        array94[i] = countarray_94.getDouble(i);
//                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
//                    }


                    ArrayList<BarEntry> graphrcv1 = new ArrayList<>();
                    for (int i = 0; i < array91.length; i++) {
                        graphrcv1.add(new BarEntry(i, (float) array91[i]));
                        //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<BarEntry> graphrcv2 = new ArrayList<>();
                    for (int i = 0; i < array92.length; i++)
                        graphrcv2.add(new BarEntry(i, (float) array92[i]));
//                    ArrayList<BarEntry> graphrcv3 = new ArrayList<>();
//
//                    for (int i = 0; i < array94.length; i++)
//                        graphrcv3.add(new BarEntry(i, (float) array94[i]));

                    ArrayList<String> labels_graphrcv1 = new ArrayList<String>();
                    for (int i = 0; i < array93.length; i++)
                        labels_graphrcv1.add(array93[i]);


                    BarDataSet bardatasetrcv = new BarDataSet(graphrcv1, "Cells");
                    BarDataSet bardatasetrcv2 = new BarDataSet(graphrcv2, "Cells");
//                    BarDataSet bardatasetrcv3 = new BarDataSet(graphrcv3, "Cells");
                    //   BarData dataBar = new BarData(labels_graph1,bardataset);

                    bardatasetrcv.setColors(new int[]{R.color.blue_500});
                    bardatasetrcv2.setColors(new int[]{R.color.red_500});
//                    bardatasetrcv3.setColors(new int[]{R.color.amber_700});

                    ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                    dataSets.add(bardatasetrcv);
                    dataSets.add(bardatasetrcv2);
//                    dataSets.add(bardatasetrcv3);

                    BarData datarcv1 = new BarData(dataSets);
                    datarcv1.setBarWidth(0.45f);
                    barchartinsurr1.setData(datarcv1);
                    barchartinsurr1.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                    // barChart.invalidate(); // refresh
                    barchartinsurr1.getDescription().setText("Set Bar Chart Description");  // set the description
                    barchartinsurr1.animateY(5000);

                    YAxis yAxis = barchartinsurr1.getAxisLeft();
                    yAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                    yAxis.setGranularity(1f);
                    yAxis.setGranularityEnabled(true);

                    barchartinsurr1.getAxisRight().setEnabled(false);

                    XAxis xAxis = barchartinsurr1.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setDrawGridLines(true);
                    xAxis.setAxisMaximum(5);

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graphrcv1));

//..............................count10.....................................................//
                    countarray_10 = data.getJSONArray("count_10");

                    //for(int i=0;i<count_7.length();i++)
                    //  {
                    countarray_101 = countarray_10.getJSONArray(1);
                    array101 = new double[countarray_101.length()];
                    for (int i = 0; i < countarray_101.length(); i++) {
                        array101[i] = countarray_101.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_102 = countarray_10.getJSONArray(2);
                    array102 = new double[countarray_102.length()];
                    for (int i = 0; i < countarray_102.length(); i++) {
                        array102[i] = countarray_102.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr92[i], Toast.LENGTH_SHORT).show();
                    }

                    countarray_103 = countarray_10.getJSONArray(0);
                    array103 = new String[countarray_103.length()];
                    for (int i = 0; i < countarray_103.length(); i++) {
                        array103[i] = countarray_103.getString(i);
                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                    }

                    ArrayList<BarEntry> graphinsu2a = new ArrayList<>();
                    for (int i = 0; i < array101.length; i++) {
                        graphinsu2a.add(new BarEntry(i, (float) array101[i]));
                        //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<BarEntry> graphinsu2b = new ArrayList<>();
                    for (int i = 0; i < array102.length; i++)
                        graphinsu2b.add(new BarEntry(i, (float) array102[i]));


                    ArrayList<String> labels_graphinsurr1 = new ArrayList<String>();
                    for (int i = 0; i < array103.length; i++)
                        labels_graphinsurr1.add(array103[i]);


                    BarDataSet bardatasetinsur = new BarDataSet(graphinsu2a, "Cells");
                    BarDataSet bardatasetinsur2 = new BarDataSet(graphinsu2b, "Cells");
                    //   BarData dataBar = new BarData(labels_graph1,bardataset);

                    bardatasetinsur.setColors(new int[]{R.color.blue_500});
                    bardatasetinsur2.setColors(new int[]{R.color.red_500});

                    ArrayList<IBarDataSet> dataSetsinsu = new ArrayList<IBarDataSet>();
                    dataSetsinsu.add(bardatasetinsur);
                    dataSetsinsu.add(bardatasetinsur2);

                    BarData datainsu1 = new BarData(dataSetsinsu);
                    datainsu1.setBarWidth(0.45f);
                    barchartinsurr2.setData(datainsu1);
                    barchartinsurr2.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                    // barChart.invalidate(); // refresh
                    barchartinsurr2.getDescription().setText("Set Bar Chart Description");  // set the description
                    barchartinsurr2.animateY(5000);

                    YAxis yAxis1 = barchartinsurr2.getAxisLeft();
                    yAxis1.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                    yAxis1.setGranularity(1f);
                    yAxis1.setGranularityEnabled(true);

                    barchartinsurr2.getAxisRight().setEnabled(false);

                    XAxis xAxis1 = barchartinsurr2.getXAxis();
                    xAxis1.setGranularity(1f);
                    xAxis1.setGranularityEnabled(true);
                    xAxis1.setCenterAxisLabels(true);
                    xAxis1.setDrawGridLines(true);
                    xAxis1.setAxisMaximum(5);

                    xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis1.setValueFormatter(new IndexAxisValueFormatter(labels_graphinsurr1));

//........................................count11....................................................//
                    countarray_11 = data.getJSONArray("count_11");
                    countarray_111 = countarray_11.getJSONArray(1);
                    countarray111 = new JSONObject[countarray_111.length()];

                    array111a = new double[countarray_111.length()];
                    array112b = new String[countarray_111.length()];
                    for (int i = 0; i < countarray_111.length(); i++) {
                        countarray111[i] = countarray_111.getJSONObject(i);
                        array111a[i] = countarray111[i].getInt("count");
                        array112b[i] = countarray111[i].getString("months");
                        //  Toast.makeText(DashFinance.this, "age="+arr10a2[i]+"value="+ arr10a1[i], Toast.LENGTH_SHORT).show();

                    }
                    List<Entry> valsComp5 = new ArrayList<Entry>();

                    for (int i = 0; i < countarray_111.length(); i++) {
                        Entry e = new Entry(i, (float) array111a[i]);
                        valsComp5.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr10a1[i], Toast.LENGTH_SHORT).show();
                    }

//
                    LineDataSet setComp5 = new LineDataSet(valsComp5, "");
                    setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
                    // setComp5.setDrawValues(false);

                    setComp5.setColor(Color.BLUE);
                    //   setComp5.setFillColor(Color.BLUE);
                    setComp5.setCircleColor(Color.BLUE);
                    //   setComp5.setDrawFilled(true);

                    List<ILineDataSet> line_dataSets3 = new ArrayList<ILineDataSet>();
                    line_dataSets3.add(setComp5);

                    LineData line_data3 = new LineData(line_dataSets3);
                    linechartPayable3.setData(line_data3);
                    linechartPayable3.invalidate(); // refresh

                    IAxisValueFormatter formatter3 = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array112b[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line3 = linechartPayable3.getXAxis();
                    xAxis_line3.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line3.setValueFormatter(formatter3);
                    xAxis_line3.setPosition(XAxis.XAxisPosition.BOTTOM);


                    YAxis yAxisRight3 = linechartPayable3.getAxisRight();
                    yAxisRight3.setEnabled(false);
//...................................count12..............................................//
                    countarray_12 = data.getJSONArray("count_12");
                    countarray_121 = countarray_12.getJSONArray(1);
                    countarray121 = new JSONObject[countarray_121.length()];

                    array121a = new double[countarray_121.length()];
                    array122b = new String[countarray_121.length()];
                    for (int i = 0; i < countarray_121.length(); i++) {
                        countarray121[i] = countarray_121.getJSONObject(i);
                        array121a[i] = countarray121[i].getInt("premium");
                        array122b[i] = countarray121[i].getString("months");
                        //  Toast.makeText(DashFinance.this, "age="+arr10a2[i]+"value="+ arr10a1[i], Toast.LENGTH_SHORT).show();

                    }
                    List<Entry> valsComp6 = new ArrayList<Entry>();

                    for (int i = 0; i < countarray_121.length(); i++) {
                        Entry e = new Entry(i, (float) array121a[i]);
                        valsComp6.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr10a1[i], Toast.LENGTH_SHORT).show();
                    }

//
                    LineDataSet setComp6 = new LineDataSet(valsComp6, "");
                    setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
                    // setComp5.setDrawValues(false);

                    setComp6.setColor(Color.BLUE);
                    //   setComp5.setFillColor(Color.BLUE);
                    setComp6.setCircleColor(Color.BLUE);
                    //   setComp5.setDrawFilled(true);

                    List<ILineDataSet> line_dataSets4 = new ArrayList<ILineDataSet>();
                    line_dataSets4.add(setComp6);

                    LineData line_data4 = new LineData(line_dataSets4);
                    linechartPayable4.setData(line_data4);
                    linechartPayable4.invalidate(); // refresh

                    IAxisValueFormatter formatter4 = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array122b[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line4 = linechartPayable4.getXAxis();
                    xAxis_line4.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line4.setValueFormatter(formatter4);
                    xAxis_line4.setPosition(XAxis.XAxisPosition.BOTTOM);


                    YAxis yAxisRight4 = linechartPayable4.getAxisRight();
                    yAxisRight4.setEnabled(false);







                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(DashInsurance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //  count0.setText("Error:" + e.getMessage());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(DashInsurance.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.hide();
            }
        });

       insurrStringREquest.setShouldCache(false);
        MyRequestQueue.add(insurrStringREquest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
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
            Intent intent = new Intent(DashInsurance.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
