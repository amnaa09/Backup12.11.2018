package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.anychart.charts.Sunburst;
import com.anychart.enums.SunburstCalculationMode;
import com.anychart.enums.TreeFillingMethod;
import com.anychart.graphics.vector.text.HAlign;
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.unicodelabs.kdgaugeview.KdGaugeView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashSale extends AppCompatActivity {
    private Toolbar toolbar;
    String instanceStr, userID, token, count1, count2, count3, count4, count5, count6;
    JSONArray countarray_7 = null, countarray_71 = null, countarray_72 = null, countarray_73 = null,countarray_8 = null, countarray_81 = null, countarray_82 = null, countarray_83 = null,
    countarray_9=null,countarray_91=null,countarray_92=null,countarray_93=null,countarray_10=null,countarray_101=null,countarray_102=null,countarray_16=null,countarray_161=null,countarray_162=null,
    countarray_11=null,countarray_12=null,countarray_122=null,countarray_13=null,countarray_132=null;
    JSONObject countarray101[], countarray102[],countarray122[],countarray132[];

    double array71[],array72[],array7[],array81[],array82[],array8[],array9[],array91[],array92[],array10a1[],array10b1[],array16[],array162[],array12a[],array13a[];
    String array73[],array83[],array93[],array10a2[], array10b2[],array161[],array11[],array12[],array121[],array122[],array12b[],array13b[];
    int lg, bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_sale);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        initToolbar();

        final BarChart barchartSale1 = (BarChart) findViewById(R.id.barchartsale1);
        final BarChart barchartSale2 = (BarChart) findViewById(R.id.barchartsale2);
        final LineChart lineChartsale1 = (LineChart) findViewById(R.id.linechartsale2);
        final LineChart lineChartsale2 = (LineChart) findViewById(R.id.linechartsale3);
        final LineChart linechartsale3= (LineChart) findViewById(R.id.linechartsale1);
        final CustomMarkerView mv = new CustomMarkerView(this, R.layout.tv_content);
        final KdGaugeView gauge1= findViewById(R.id.gauge1);
        final PieChartView pieChartView = findViewById(R.id.chart);
        final PieChartView pieChartView1=findViewById(R.id.chart13);


        RequestQueue MyRequestQueue = Volley.newRequestQueue(DashSale.this);
        String sale = "http://" + instanceStr + ".5dsurf.com/app/webservice/getSaleStatictics/" + bg + "/" + lg + "/" + userID + "/" + token;
        StringRequest salerequest=new StringRequest(Request.Method.GET, sale, new Response.Listener<String>() {
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
                    countarray_71 = countarray_7.getJSONArray(0);
                    array71 = new double[countarray_71.length()];
                    for (int i = 0; i < countarray_71.length(); i++) {
                        array71[i] = countarray_71.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_72 = countarray_7.getJSONArray(1);
                    array72 = new double[countarray_72.length()];
                    for (int i = 0; i < countarray_72.length(); i++) {
                        array72[i] = countarray_72.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_73 = countarray_7.getJSONArray(2);
                    array73 = new String[countarray_73.length()];
                    for (int i = 0; i < countarray_73.length(); i++) {
                        array73[i] = countarray_73.getString(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<BarEntry> graphrcv1 = new ArrayList<>();
                    for (int i = 0; i < array71.length; i++) {
                        graphrcv1.add(new BarEntry(i, (float) array71[i]));
                        //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<BarEntry> graphrcv2 = new ArrayList<>();
                    for (int i = 0; i < array72.length; i++)
                        graphrcv2.add(new BarEntry(i, (float) array72[i]));


                    ArrayList<String> labels_graphrcv1 = new ArrayList<String>();
                    for (int i = 0; i < array73.length; i++)
                        labels_graphrcv1.add(array73[i]);


                    BarDataSet bardatasetrcv = new BarDataSet(graphrcv1, "Cells");
                    BarDataSet bardatasetrcv2 = new BarDataSet(graphrcv2, "Cells");
                    //   BarData dataBar = new BarData(labels_graph1,bardataset);

                    bardatasetrcv.setColors(new int[]{R.color.blue_500});
                    bardatasetrcv2.setColors(new int[]{R.color.red_500});

                    ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                    dataSets.add(bardatasetrcv);
                    dataSets.add(bardatasetrcv2);

                    BarData datarcv1 = new BarData(dataSets);
                    datarcv1.setBarWidth(0.45f);
                    barchartSale1.setData(datarcv1);
                    barchartSale1.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                    // barChart.invalidate(); // refresh
                    barchartSale1.getDescription().setText("Set Bar Chart Description");  // set the description
                    barchartSale1.animateY(5000);

                    YAxis yAxis = barchartSale1.getAxisLeft();
                    yAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                    yAxis.setGranularity(1f);
                    yAxis.setGranularityEnabled(true);

                    barchartSale1.getAxisRight().setEnabled(false);

                    XAxis xAxis = barchartSale1.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setDrawGridLines(true);
                    xAxis.setAxisMaximum(5);

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graphrcv1));
                    //...................................................................................
                    countarray_16 = data.getJSONArray("count_16");
                    array16 = new double[countarray_16.length()];
                    countarray_162 = countarray_16.getJSONArray(2);
                    array162 = new double[countarray_162.length()];
                    for (int i = 0; i < countarray_162.length(); i++) {
                        array162[i] = countarray_162.getDouble(i);
                        // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_161 = countarray_16.getJSONArray(0);
                    array161 = new String[countarray_161.length()];
                    for (int i = 0; i < countarray_161.length(); i++) {
                        array161[i] = countarray_161.getString(i);
                        Toast.makeText(DashSale.this, "value=" + array161[i], Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<BarEntry> graphsale1 = new ArrayList<>();
                    for (int i = 0; i < array162.length; i++) {
                        graphsale1.add(new BarEntry(i, (float) array162[i]));
                        //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                    }
//                    ArrayList<BarEntry> graphsale2 = new ArrayList<>();
//                    for (int i = 0; i < array162.length; i++)
//                        graphsale2.add(new BarEntry(i, (float) array72[i]));


                    ArrayList<String> labels_graphsale1 = new ArrayList<String>();
                    for (int i = 0; i < array161.length; i++)
                        labels_graphsale1.add(array161[i]);


                    BarDataSet bardatasetsale = new BarDataSet(graphsale1, "Cells");
//                    BarDataSet bardatasetsale2 = new BarDataSet(graphsale2, "Cells");
                    //   BarData dataBar = new BarData(labels_graph1,bardataset);

                    bardatasetsale.setColors(new int[]{R.color.blue_500});
                    //   bardatasetrcv2.setColors(new int[]{R.color.red_500});

                    ArrayList<IBarDataSet> dataSets2 = new ArrayList<IBarDataSet>();
                    dataSets2.add(bardatasetrcv);
                    //  dataSets2.add(bardatasetrcv2);

                    BarData datasale1 = new BarData(dataSets2);
                    datasale1.setBarWidth(0.45f);
                    barchartSale2.setData(datarcv1);
                    barchartSale2.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                    // barChart.invalidate(); // refresh
                    barchartSale2.getDescription().setText("Set Bar Chart Description");  // set the description
                    barchartSale2.animateY(5000);

                    YAxis yAxis2 = barchartSale2.getAxisLeft();
                    yAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });
                    yAxis2.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                    yAxis.setGranularity(1f);
                    yAxis.setGranularityEnabled(true);

                    barchartSale2.getAxisRight().setEnabled(false);

                    XAxis xAxis2 = barchartSale2.getXAxis();
                    xAxis2.setGranularity(1f);
                    xAxis2.setGranularityEnabled(true);
                    xAxis2.setCenterAxisLabels(true);
                    xAxis2.setDrawGridLines(true);
                    xAxis2.setAxisMaximum(5);

                    xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis2.setValueFormatter(new IndexAxisValueFormatter(labels_graphsale1));

                    //------------------------------------------------------------------------------
                    countarray_8 = data.getJSONArray("count_08");
                    array8 = new double[countarray_8.length()];
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
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    List<Entry> valsComp1 = new ArrayList<Entry>();
                    List<Entry> valsComp2 = new ArrayList<Entry>();
                    for (int i = 0; i < countarray_81.length(); i++) {
                        Entry e = new Entry(i, (float) array81[i]);
                        valsComp1.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < countarray_82.length(); i++) {
                        Entry e = new Entry(i, (float) array82[i]);
                        valsComp2.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    LineDataSet setComp1 = new LineDataSet(valsComp1, "Receivable");
                    setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    setComp1.setColor(R.color.red_500);

                    LineDataSet setComp2 = new LineDataSet(valsComp2, "VAT");
                    setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

                    setComp2.setColor(R.color.green_500);

                    List<ILineDataSet> line_dataSets1 = new ArrayList<ILineDataSet>();
                    line_dataSets1.add(setComp1);
                    line_dataSets1.add(setComp2);


                    LineData line_data1 = new LineData(line_dataSets1);
                    lineChartsale1.setData(line_data1);
                    lineChartsale1.invalidate(); // refresh

                    IAxisValueFormatter formatter = new IAxisValueFormatter() {

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

                    XAxis xAxis_line1 = lineChartsale1.getXAxis();
                    xAxis_line1.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line1.setValueFormatter(formatter);
                    xAxis_line1.setPosition(XAxis.XAxisPosition.BOTTOM);

                    YAxis yAxisRight = lineChartsale1.getAxisRight();
                    yAxisRight.setEnabled(false);

                    lineChartsale1.setTouchEnabled(true);
                    lineChartsale1.setMarker(mv);


                    //.........................................................................................................

                    countarray_9 = data.getJSONArray("count_09");
                    array9 = new double[countarray_9.length()];
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
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    countarray_93 = countarray_9.getJSONArray(0);
                    array93 = new String[countarray_93.length()];
                    for (int i = 0; i < countarray_93.length(); i++) {
                        array93[i] = countarray_93.getString(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
                    List<Entry> valsComp3 = new ArrayList<Entry>();
                    List<Entry> valsComp4 = new ArrayList<Entry>();

                    for (int i = 0; i < countarray_91.length(); i++) {
                        Entry e = new Entry(i, (float) array91[i]);
                        valsComp3.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
//
                    for (int i = 0; i < countarray_92.length(); i++) {
                        Entry e = new Entry(i, (float) array92[i]);
                        valsComp4.add(e);
                        //arr82[i]=count_82.getDouble(i);
                        //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }
//
                    LineDataSet setComp3 = new LineDataSet(valsComp3, "Receiveable Invoice");
                    setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
                    setComp3.setDrawValues(false);

                    setComp3.setColor(Color.BLUE);
                    setComp3.setFillColor(Color.BLUE);
                    setComp3.setCircleColor(Color.BLUE);
                    setComp3.setDrawFilled(true);
//
                    LineDataSet setComp4 = new LineDataSet(valsComp4, "Receipt Voucher");
                    setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);
                    setComp4.setDrawValues(false);
//
                    setComp4.setColor(Color.GREEN);
                    setComp4.setFillColor(Color.GREEN);
                    setComp4.setDrawFilled(true);
                    setComp4.setCircleColor(Color.GREEN);

                    List<ILineDataSet> line_dataSets2 = new ArrayList<ILineDataSet>();
                    line_dataSets2.add(setComp3);
                    line_dataSets2.add(setComp4);

//
                    LineData line_data2 = new LineData(line_dataSets2);
                    lineChartsale2.setData(line_data2);
                    lineChartsale2.invalidate(); // refresh

                    lineChartsale2.setTouchEnabled(true);
                    lineChartsale2.setMarker(mv);
//
                    IAxisValueFormatter formatter2 = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array93[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line2 = lineChartsale2.getXAxis();
                    xAxis_line2.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line2.setValueFormatter(formatter);
                    xAxis_line2.setPosition(XAxis.XAxisPosition.BOTTOM);


                    YAxis yAxisRight2 = lineChartsale2.getAxisRight();
                    yAxisRight2.setEnabled(false);

                    //.................................................................................................

                    countarray_10 = data.getJSONArray("count_10");
                    countarray_101 = countarray_10.getJSONArray(0);
                    countarray101 = new JSONObject[countarray_101.length()];

                    array10a1 = new double[countarray_101.length()];
                    array10a2 = new String[countarray_101.length()];
                    for (int i = 0; i < countarray_101.length(); i++) {
                        countarray101[i] = countarray_101.getJSONObject(i);
                        array10a1[i] = countarray101[i].getInt("value");
                        array10a2[i] = countarray101[i].getString("Ages");
                        //  Toast.makeText(DashFinance.this, "age="+arr10a2[i]+"value="+ arr10a1[i], Toast.LENGTH_SHORT).show();

                    }

                    List<Entry> valsComp5 = new ArrayList<Entry>();

                    for (int i = 0; i < countarray_101.length(); i++) {
                        Entry e = new Entry(i, (float) array10a1[i]);
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
                    linechartsale3.setData(line_data3);
                    linechartsale3.invalidate(); // refresh

                    IAxisValueFormatter formatter3 = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return array10a2[(int) value];
                        }

                        // we don't draw numbers, so no decimal digits needed
                        //   @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };

                    XAxis xAxis_line3 = linechartsale3.getXAxis();
                    xAxis_line3.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis_line3.setValueFormatter(formatter3);
                    xAxis_line3.setPosition(XAxis.XAxisPosition.BOTTOM);


                    YAxis yAxisRight3 = linechartsale3.getAxisRight();
                    yAxisRight3.setEnabled(false);

                    //    linechartPayable3.setTouchEnabled(true);
                    //   linechartPayable3.setMarker(mv);

                    countarray_102 = countarray_10.getJSONArray(1);
                    countarray102 = new JSONObject[countarray_102.length()];

                    array10b1 = new double[countarray_102.length()];
                    array10b2 = new String[countarray_102.length()];
                    for (int i = 0; i < countarray_102.length(); i++) {
                        countarray102[i] = countarray_102.getJSONObject(i);
                        array10b1[i] = countarray102[i].getInt("value");
                        array10b2[i] = countarray102[i].getString("name");

                    }

                    List<DataEntry> funnelData = new ArrayList<>();
                    for (int i = 0; i < countarray_102.length(); i++) {
                        funnelData.add(new ValueDataEntry(array10b2[i], array10b1[i]));
                       // Toast.makeText(DashSale.this, "name=" + array10b2[i] + "value=" + array10b1[i], Toast.LENGTH_SHORT).show();

                    }
                    List<DataEntry> dataf = new ArrayList<>();
                    dataf.add(new ValueDataEntry("Website Visits", 528756));
                    dataf.add(new ValueDataEntry("Downloads", 164052));
                    dataf.add(new ValueDataEntry("Valid Contacts", 112167));
                    dataf.add(new ValueDataEntry("Interested to Buy", 79128));
                    dataf.add(new ValueDataEntry("Purchased", 79128));
//........................................................................................................

                    countarray_11 = data.getJSONArray("count_11");
                    array11 = new String[countarray_11.length()];
                    for (int i = 0; i < countarray_11.length(); i++)
                        array11[i] = countarray_11.getString(i);
                    Float temp = Float.valueOf(array11[0].toString());

                    gauge1.setSpeed(temp);
//...................................................................................................


                    countarray_12 = data.getJSONArray("count_12");
                    countarray_122 = countarray_12.getJSONArray(1);
                    Toast.makeText(DashSale.this, "arrayindex=" +countarray_122.length(), Toast.LENGTH_SHORT).show();

                    countarray122 = new JSONObject[countarray_122.length()];
//                    array122=new String[countarray_122.length()];
                    array12a = new double[countarray_122.length()];
                    array12b = new String[countarray_122.length()];
             //       countarray122=new JSONObject[countarray_122.length()];
                    for (int i = 0; i < countarray_122.length(); i++) {

                        countarray122[i] = countarray_122.getJSONObject(i);
                        array12a[i] = countarray122[i].getDouble("value");
                        array12b[i] = countarray122[i].getString("label");
                        Toast.makeText(DashSale.this, "label=" + array12b[i] + "value=" + array12a[i], Toast.LENGTH_SHORT).show();

                    }




                    List<SliceValue> pieData = new ArrayList<>();
                    for (int i=0; i<countarray_122.length(); i++){
                        pieData.add(new SliceValue((float) array12a[i], R.color.deep_orange_200).setLabel(array12b[i]));

                    }
//                    pieData.add(new SliceValue(15, R.color.colorAccentLight));
//                    pieData.add(new SliceValue(25, Color.LTGRAY));
//                    pieData.add(new SliceValue(10, Color.GREEN));
//                    pieData.add(new SliceValue(60, Color.CYAN));
//
                   PieChartData pieChartData = new PieChartData(pieData);

//                    pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
//                    pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
//                    pieData.add(new SliceValue(10, Color.CYAN).setLabel("Q3: $18"));
//                    pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
                    pieChartData.setHasLabels(true);
                 pieChartView.setPieChartData(pieChartData);

//...................................................Count13................................................................//
                    countarray_13 = data.getJSONArray("count_13");
                    countarray_132 = countarray_13.getJSONArray(1);
                    Toast.makeText(DashSale.this, "arrayindex=" +countarray_132.length(), Toast.LENGTH_SHORT).show();
                    countarray132 = new JSONObject[countarray_132.length()];
//                    array122=new String[countarray_122.length()];
                    array13a = new double[countarray_132.length()];
                    array13b = new String[countarray_132.length()];

                    for (int i = 0; i < countarray_132.length(); i++) {

                        countarray132[i] = countarray_132.getJSONObject(i);
                        array13a[i] = countarray132[i].getDouble("value");
                        array13b[i] = countarray132[i].getString("name");
                        Toast.makeText(DashSale.this, "name=" + array13b[i] + "value=" + array13a[i], Toast.LENGTH_SHORT).show();

                    }
                    List<SliceValue> pieData1 = new ArrayList<>();
                    for (int i=0; i<countarray_132.length(); i++){
                        pieData1.add(new SliceValue((float) array13a[i], R.color.deep_orange_200).setLabel(array13b[i]));

                    }
//                    pieData.add(new SliceValue(15, R.color.colorAccentLight));
//                    pieData.add(new SliceValue(25, Color.LTGRAY));
//                    pieData.add(new SliceValue(10, Color.GREEN));
//                    pieData.add(new SliceValue(60, Color.CYAN));
//
                    PieChartData pieChartData1 = new PieChartData(pieData1);

//                    pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
//                    pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
//                    pieData.add(new SliceValue(10, Color.CYAN).setLabel("Q3: $18"));
//                    pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
                    pieChartData1.setHasLabels(true);
                    pieChartView1.setPieChartData(pieChartData1);



                } catch (JSONException e) {

                    if ( progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(DashSale.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //  count0.setText("Error:" + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//This code is executed if there is an error.
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
                Toast.makeText(DashSale.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        salerequest.setShouldCache(false);
        MyRequestQueue.add(salerequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();


    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DashSale");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
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
            Intent intent = new Intent(DashSale.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}