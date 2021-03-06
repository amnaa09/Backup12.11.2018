package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.anychart.standalones.markersfactory.Marker;
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;

import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.unicodelabs.kdgaugeview.KdGaugeView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static java.lang.Boolean.TRUE;


public class DashFinance extends AppCompatActivity {
    private Toolbar toolbar;
    ImageView info1, info2;
    String   instanceStr,  userID, token, count1,count2,count3,count4,count5,count6;
    JSONArray  count_7=null, count_71=null, count_72=null, count_73=null,count_74=null, count_8=null, count_81=null, count_82=null, count_83=null,
            count_9=null, count_91=null, count_92=null, count_93=null, count_10=null, count_101=null, count_102=null,count_13=null,count_14=null,count_132=null,count_142=null;
    JSONObject count101[], count102[],count132[], count142[];
    String  countr_1, countr_2, countr_3, countr_4, countr_5, countr_6;
    int count11;
    int count12;
    JSONArray countarray11;
    int countarray12;
    JSONArray countarray_7 = null, countarray_71 = null, countarray_72 = null, countarray_73 = null,countarray_74=null, countarray_8 = null,
            countarray_81 = null, countarray_82 = null, countarray_83 = null,countarray_9=null,countarray_91=null,
            countarray_92=null,countarray_93=null,countarray_10=null,countarray_101=null,countarray_102=null,countarray_13=null,countarray_132=null,countarray_14=null,countarray_142=null ;
JSONObject countarray101[],countarray102[],countarray132[], countarray142[];
    double arr7[], arr71[], arr72[], arr8[], arr81[], arr82[], arr9[], arr91[], arr92[],arr10a1[],arr10b1[],arr13a[], array7[], array71[],
            array72[], array8[], array81[], array82[],array9[],array91[],array92[],array10a1[],array10b1[],array13a[],array14a[],arr14a[];
    String arr73[],arr74[], arr83[], arr93[],arr10a2[], arr10b2[],arr13b[], array73[],array74[], array83[],array93[],array10a2[],array10b2[],array11[],
            array13b[], array14b[],arr14b[];
    int lg, bg;
    TextView countP1,countP2,countP3,countP4,countP5,countP6, countR1,countR2,countR3,countR4,countR5,countR6,amounttext ;
    final int[] MY_COLORS = {Color.rgb(255,204,204), Color.rgb(153,255,255), Color.rgb(178,255,102),
            Color.rgb(255,255,102), Color.rgb(204,153,255), Color.rgb(204,255,204), Color.rgb(244,244,244)
            , Color.rgb(51,255,255), Color.rgb(255,178,102), Color.rgb(255,102,255)
            , Color.rgb(229,204,255), Color.rgb(153,76,0), Color.rgb(102,0,51)
            , Color.rgb(0,0,0)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_finance);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        countP1=(TextView) findViewById(R.id.countP1);
        countP2=(TextView) findViewById(R.id.countP2);
        countP3=(TextView) findViewById(R.id.countP3);
        countP4=(TextView) findViewById(R.id.countP4);
        countP5=(TextView) findViewById(R.id.countP5);
        countP6=(TextView) findViewById(R.id.countP6);

        countR1=(TextView) findViewById(R.id.countR1);
        countR2=(TextView) findViewById(R.id.countR2);
        countR3=(TextView) findViewById(R.id.countR3);
        countR4=(TextView) findViewById(R.id.countR4);
        countR5=(TextView) findViewById(R.id.countR5);
        countR6=(TextView) findViewById(R.id.countR6);

        amounttext=(TextView) findViewById(R.id.guageTextreceivable) ;

        info1=(ImageView) findViewById(R.id.info1);
        info2=(ImageView) findViewById(R.id.info2);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);

    //    Toast.makeText(this, "data=" + userID + " " + instanceStr + " " + token + " " + lg + " " + bg, Toast.LENGTH_LONG).show();

        initToolbar();

        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(DashFinance.this, "in click", Toast.LENGTH_SHORT).show();
                showInfoDialog(arr73, arr74, "Code", "Supplier");
            }
        });

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(DashFinance.this, "in click", Toast.LENGTH_SHORT).show();
                showInfoDialog(array73, array74, "Code", "Customer");
            }
        });
        final BarChart barchartPayable1 = (BarChart) findViewById(R.id.barchartPayable1);
        final BarChart barchartReceivable1 = (BarChart) findViewById(R.id.barchartReceivable1);
        final LineChart linechartPayable1 = (LineChart) findViewById(R.id.linechartPayable1);
        final LineChart linechartPayable2 = (LineChart) findViewById(R.id.linechartPayable2);
        final LineChart linechartPayable3 = (LineChart) findViewById(R.id.linechartPayable3);
        final LineChart linechartReceivable1 = (LineChart) findViewById(R.id.linechartReceivable1);
        final LineChart lineChartReceivable2=(LineChart)findViewById(R.id.linechartReceivable2);
        final LineChart lineChartReceivable3=(LineChart)findViewById(R.id.linechartReceivable3);
        final PieChartView pieChartpayable= (PieChartView) findViewById(R.id.piechartpay);
        final PieChartView pieChartrcv= (PieChartView) findViewById(R.id.piechartrecv);
        final PieChartView pieChartpayble1=(PieChartView)findViewById(R.id.chart13);
        final PieChartView pieChartrecv=(PieChartView)findViewById(R.id.chartrecv13);
        final PieChartView pieChartpayble2=(PieChartView)findViewById(R.id.chart14);
        final PieChartView pieChartrecv2=(PieChartView)findViewById(R.id.chartrecv14);
        final KdGaugeView gauge1= findViewById(R.id.gauge1);
        final KdGaugeView gauge2= findViewById(R.id.gauge2);
        final KdGaugeView gauge3= findViewById(R.id.gauge3);
        final KdGaugeView gaugerecv= findViewById(R.id.gaugerecv);
        final CustomMarkerView mv = new CustomMarkerView(this, R.layout.tv_content);


        RequestQueue MyRequestQueue = Volley.newRequestQueue(DashFinance.this);
        String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getReceivableStatictics/" + bg + "/" + lg + "/" + userID + "/" + token;
        StringRequest recvStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response);
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    if(!TextUtils.isEmpty(data.getString("count_01"))) {
                        countr_1 = data.getString("count_01");
                        countR1.setText(countr_1);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_02"))) {
                        countr_2 = data.getString("count_02");
                        countR2.setText(countr_2);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_03"))) {
                        countr_3 = data.getString("count_03");
                        countR3.setText(countr_3);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_04"))) {
                        countr_4 = data.getString("count_04");
                        countR4.setText(countr_4);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_05"))) {
                        countr_5 = data.getString("count_05");
                        countR5.setText(countr_5);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_06"))) {
                        countr_6 = data.getString("count_06");
                        countR6.setText(countr_6);
                    }


                    if(!TextUtils.isEmpty(data.getString("count_07"))) {
                        info2.setVisibility(View.VISIBLE);
                        countarray_7 = data.getJSONArray("count_07");

                        array7 = new double[countarray_7.length()];
                        //for(int i=0;i<count_7.length();i++)
                        //  {
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

                        countarray_74 = countarray_7.getJSONArray(3);
                        array74 = new String[countarray_74.length()];
                        for (int i = 0; i < countarray_74.length(); i++) {
                            array74[i] = countarray_74.getString(i);
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
                        for (int i = 0; i < array74.length; i++)
                            labels_graphrcv1.add(array74[i]);


                        BarDataSet bardatasetrcv = new BarDataSet(graphrcv1, "Outstanding Amount");
                        BarDataSet bardatasetrcv2 = new BarDataSet(graphrcv2, "Receivable Amount");
                        //   BarData dataBar = new BarData(labels_graph1,bardataset);

                        bardatasetrcv.setColors(new int[]{R.color.blue_500});
                        bardatasetrcv2.setColors(new int[]{R.color.red_500});
                        bardatasetrcv.setDrawValues(false);
                        bardatasetrcv2.setDrawValues(false);

                        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                        dataSets.add(bardatasetrcv);
                        dataSets.add(bardatasetrcv2);

                        BarData datarcv1 = new BarData(dataSets);
                        datarcv1.setBarWidth(0.45f);
                        barchartReceivable1.setData(datarcv1);
                        barchartReceivable1.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                        // barChart.invalidate(); // refresh
                      //  barchartReceivable1.getDescription().setText("Set Bar Chart Description");  // set the description
                        barchartReceivable1.animateY(5000);

                        YAxis yAxis = barchartReceivable1.getAxisLeft();
                        yAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return String.valueOf((int) value);
                            }
                        });
                        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                        yAxis.setGranularity(1f);
                        yAxis.setGranularityEnabled(true);

                        barchartReceivable1.getAxisRight().setEnabled(false);

                        XAxis xAxis = barchartReceivable1.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setDrawGridLines(true);
                        xAxis.setAxisMaximum(5);

                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graphrcv1));
                        barchartReceivable1.setTouchEnabled(true);
                        barchartReceivable1.setMarker(mv);
                    }
                    //------------------------------------------------------------------------------
                    if(!TextUtils.isEmpty(data.getString("count_08"))) {
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

                        LineDataSet setComp1 = new LineDataSet(valsComp1, "Recivable");
                        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setComp1.setColor(R.color.red_500);
                        setComp1.setFillColor(R.color.red_500);
                        setComp1.setDrawFilled(true);
                        setComp1.setCircleColor(R.color.red_500);
                        setComp1.setDrawValues(false);


                        LineDataSet setComp2 = new LineDataSet(valsComp2, "VAT");
                        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setComp2.setColor(R.color.green_500);
                        setComp2.setFillColor(R.color.green_500);
                        setComp2.setDrawFilled(true);
                        setComp2.setCircleColor(R.color.green_500);
                        setComp2.setDrawValues(false);

                        List<ILineDataSet> line_dataSets1 = new ArrayList<ILineDataSet>();
                        line_dataSets1.add(setComp1);
                        line_dataSets1.add(setComp2);


                        LineData line_data1 = new LineData(line_dataSets1);
                        linechartReceivable1.setData(line_data1);
                        linechartReceivable1.invalidate(); // refresh

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

                        XAxis xAxis_line1 = linechartReceivable1.getXAxis();
                        xAxis_line1.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis_line1.setValueFormatter(formatter);
                        linechartReceivable1.setTouchEnabled(true);
                        linechartReceivable1.setMarker(mv);
                    }
                    //.......................................................................................................................

                    if(!TextUtils.isEmpty(data.getString("count_09"))) {
                        countarray_9 = data.getJSONArray("count_09");

                        //for(int i=0;i<count_7.length();i++)
                        //  {
                        countarray_91 = countarray_9.getJSONArray(1);
                        array91 = new double[countarray_91.length()];
                        for (int i = 0; i < countarray_91.length(); i++) {
                            array91[i] = countarray_91.getDouble(i);
                            // Toast.makeText(DashFinance.this, "value="+array91[i], Toast.LENGTH_SHORT).show();
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
//
//
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
                        LineDataSet setComp4 = new LineDataSet(valsComp4, "Payment Voucher");
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
                        lineChartReceivable2.setData(line_data2);
                        lineChartReceivable2.invalidate(); // refresh

                        lineChartReceivable2.setTouchEnabled(true);
                        lineChartReceivable2.setMarker(mv);
//
                        IAxisValueFormatter formatter2 = new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return arr93[(int) value];
                            }

                            // we don't draw numbers, so no decimal digits needed
                            //   @Override
                            public int getDecimalDigits() {
                                return 0;
                            }
                        };

                        XAxis xAxis_line2 = lineChartReceivable2.getXAxis();
                        xAxis_line2.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis_line2.setValueFormatter(formatter2);
                        xAxis_line2.setPosition(XAxis.XAxisPosition.BOTTOM);


                        YAxis yAxisRight2 = lineChartReceivable2.getAxisRight();
                        yAxisRight2.setEnabled(false);
                    }
                    //.................................................................................................
                    if(!TextUtils.isEmpty(data.getString("count_10"))) {
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
                        lineChartReceivable3.setData(line_data3);
                        lineChartReceivable3.invalidate(); // refresh

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

                        XAxis xAxis_line3 = lineChartReceivable3.getXAxis();
                        xAxis_line3.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis_line3.setValueFormatter(formatter3);
                        xAxis_line3.setPosition(XAxis.XAxisPosition.BOTTOM);


                        YAxis yAxisRight3 = lineChartReceivable3.getAxisRight();
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


                        // Toast.makeText(DashFinance.this, "name=" + array10b2[i] + "value=" + array10b1[i], Toast.LENGTH_SHORT).show();


                        List<SliceValue> pieData = new ArrayList<>();
                        for (int i = 0; i < countarray_102.length(); i++) {
                            pieData.add(new SliceValue((float) array10b1[i], MY_COLORS[i]).setLabel(array10b2[i]+"="+array10b1[i]));

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
                        pieChartData.setHasLabelsOutside(true);
                        pieChartrcv.setPieChartData(pieChartData);
                    }

                    if(!TextUtils.isEmpty(data.getString("count_11"))) {
                        countarray11 = data.getJSONArray("count_11");
                        array11 = new String[countarray11.length()];
                        for (int i = 0; i < countarray11.length(); i++)
                            array11[i] = countarray11.getString(i);
                        Float temp = Float.valueOf(array11[0].toString());
                        gaugerecv.setSpeed(temp);
                        //guagerecv.setSpeed(temp);
                        amounttext.setText("Amount: "+array11[1].toString());
                    }
                    if(!TextUtils.isEmpty(data.getString("count_12"))) {

                        countarray12 = data.getInt("count_12");
                        //   gauge1.setMinValue(0);
                        //   gauge1.setMaxValue(100);
                        gauge2.setSpeed(countarray12);
                    }
//..................Count13..........................................................................//
                    if(!TextUtils.isEmpty(data.getString("count_13"))) {
                        countarray_13 = data.getJSONArray("count_13");
                        countarray_132 = countarray_13.getJSONArray(1);
                        //  Toast.makeText(DashFinance.this, "arrayindex=" +countarray_132.length(), Toast.LENGTH_SHORT).show();
                        countarray132 = new JSONObject[countarray_132.length()];
//                    array122=new String[countarray_122.length()];
                        array13a = new double[countarray_132.length()];
                        array13b = new String[countarray_132.length()];
                        for (int i = 0; i < countarray_132.length(); i++) {

                            countarray132[i] = countarray_132.getJSONObject(i);
                            array13a[i] = countarray132[i].getDouble("value");
                            array13b[i] = countarray132[i].getString("label");
                            //      Toast.makeText(DashFinance.this, "label=" + array13b[i] + "value=" + array13a[i], Toast.LENGTH_SHORT).show();

                        }
                        List<SliceValue> pieData2 = new ArrayList<>();
                        for (int i = 0; i < countarray_132.length(); i++) {
                            pieData2.add(new SliceValue((float) array13a[i], MY_COLORS[i]).setLabel(array13b[i]+"="+array13a[i]));

                        }

                        PieChartData pieChartData2 = new PieChartData(pieData2);

                        pieChartData2.setHasLabels(true);
                        pieChartData2.setHasLabelsOutside(true);
                        pieChartrecv.setPieChartData(pieChartData2);

                    }

//..................Count14..........................................................................//
                    if(!TextUtils.isEmpty(data.getString("count_14"))) {
                        countarray_14 = data.getJSONArray("count_14");
                        countarray_142 = countarray_14.getJSONArray(1);
                        //  Toast.makeText(DashFinance.this, "arrayindex=" +countarray_132.length(), Toast.LENGTH_SHORT).show();
                        countarray142 = new JSONObject[countarray_142.length()];
//                    array122=new String[countarray_122.length()];
                        array14a = new double[countarray_142.length()];
                        array14b = new String[countarray_142.length()];
                        for (int i = 0; i < countarray_142.length(); i++) {

                            countarray142[i] = countarray_142.getJSONObject(i);
                            array14a[i] = countarray142[i].getDouble("value");
                            array14b[i] = countarray142[i].getString("name");
                            //      Toast.makeText(DashFinance.this, "label=" + array13b[i] + "value=" + array13a[i], Toast.LENGTH_SHORT).show();

                        }
                        List<SliceValue> pieData2 = new ArrayList<>();
                        for (int i = 0; i < countarray_142.length(); i++) {
                            pieData2.add(new SliceValue((float) array14a[i], MY_COLORS[i]).setLabel(array14b[i]+"="+array14a[i]));

                        }

                        PieChartData pieChartData2 = new PieChartData(pieData2);

                        pieChartData2.setHasLabels(true);
                        pieChartData2.setHasLabelsOutside(true);
                        pieChartrecv2.setPieChartData(pieChartData2);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(DashFinance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DashFinance.this, message, Toast.LENGTH_SHORT).show();
                if ( progressDialog.isShowing())
                    progressDialog.hide();
            }
        });
        recvStringREquest.setShouldCache(false);
        MyRequestQueue.add(recvStringREquest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();



        String url = "http://"+instanceStr+".5dsurf.com/app/webservice/getPayableStatictics/"+bg+"/"+lg+"/"+userID+"/"+token;

        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                // tx.setText("response: " + response.toString());
                //  Toast.makeText(Signup.this, "reponse=" + response, Toast.LENGTH_SHORT).show();
          //    Toast.makeText(DashFinance.this, "reponse=" + response.toString(), Toast.LENGTH_LONG).show();
           //   count0.setText(response.toString());

                try {
                    //JSONObject dataa=new JSONObject(response.toString());
                    JSONObject data = new JSONObject(response);
                    if ( progressDialog.isShowing())
                        progressDialog.hide();
                    if(!TextUtils.isEmpty(data.getString("count_01"))) {
                        count1 = data.getString("count_01");
                        countP1.setText(count1);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_02"))) {
                        count2 = data.getString("count_02");
                        countP2.setText(count2);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_03"))) {
                        count3 = data.getString("count_03");
                        countP3.setText(count1);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_04"))) {
                        count4 = data.getString("count_04");
                        countP4.setText(count4);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_05"))) {
                        count5 = data.getString("count_05");
                        countP5.setText(count5);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_06"))) {
                        count6 = data.getString("count_06");
                        countP6.setText(count1);
                    }

               //     Toast.makeText(DashFinance.this, "count1=" + count1, Toast.LENGTH_LONG).show();


                    //  count7_length=data.getJSONArray("count_07").length();
                  //  count_7=new int[count7_length];

                    if(!TextUtils.isEmpty(data.getString("count_07"))) {
                        info1.setVisibility(View.VISIBLE);
                        count_7 = data.getJSONArray("count_07");
                        arr7 = new double[count_7.length()];
                        //for(int i=0;i<count_7.length();i++)
                        //  {
                        count_71 = count_7.getJSONArray(0);
                        arr71 = new double[count_71.length()];
                        for (int i = 0; i < count_71.length(); i++) {
                            arr71[i] = count_71.getDouble(i);
                            // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                        }

                        count_72 = count_7.getJSONArray(1);
                        arr72 = new double[count_72.length()];
                        for (int i = 0; i < count_72.length(); i++) {
                            arr72[i] = count_72.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        count_73 = count_7.getJSONArray(2);
                        arr73 = new String[count_73.length()];
                        for (int i = 0; i < count_73.length(); i++) {
                            arr73[i] = count_73.getString(i);
                            //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                        }

                        count_74 = count_7.getJSONArray(3);
                        arr74 = new String[count_74.length()];
                        for (int i = 0; i < count_74.length(); i++) {
                            arr74[i] = count_74.getString(i);
                            //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                        }

                        ArrayList<BarEntry> graph1 = new ArrayList<>();
                        for (int i = 0; i < arr71.length; i++) {
                            graph1.add(new BarEntry(i, (float) arr71[i]));
                            //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                        }

                        ArrayList<BarEntry> graph2 = new ArrayList<>();
                        for (int i = 0; i < arr72.length; i++)
                            graph2.add(new BarEntry(i, (float) arr72[i]));


                        ArrayList<String> labels_graph1 = new ArrayList<String>();
                        for (int i = 0; i < arr74.length; i++)
                            labels_graph1.add(arr74[i]);


                        BarDataSet bardataset = new BarDataSet(graph1, "Outstanding Amount");
                        BarDataSet bardataset2 = new BarDataSet(graph2, "Payable amount");
                        //   BarData dataBar = new BarData(labels_graph1,bardataset);

                        bardataset.setColors(new int[]{R.color.blue_500});
                        bardataset2.setColors(new int[]{R.color.red_500});
                        bardataset.setDrawValues(false);
                        bardataset2.setDrawValues(false);

                        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                        dataSets.add(bardataset);
                        dataSets.add(bardataset2);

                        BarData data1 = new BarData(dataSets);
                        data1.setBarWidth(0.45f);
                        barchartPayable1.setData(data1);
                        barchartPayable1.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                        // barChart.invalidate(); // refresh

                        //   barChart.setData(dataBar); // set the data and list of lables into chart

                 //       barchartPayable1.getDescription().setText("Set Bar Chart Description");  // set the description
                        barchartPayable1.animateY(5000);

                        YAxis yAxis = barchartPayable1.getAxisLeft();
                        yAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return String.valueOf((int) value);
                            }
                        });

                        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                        yAxis.setGranularity(1f);
                        yAxis.setGranularityEnabled(true);

                        barchartPayable1.getAxisRight().setEnabled(false);

                        XAxis xAxis = barchartPayable1.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setDrawGridLines(true);
                        xAxis.setAxisMaximum(7);

                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graph1));

                        barchartPayable1.setTouchEnabled(true);
                        barchartPayable1.setMarker(mv);

                    }
                 //------------------------------------------------------------------------------
                    if(!TextUtils.isEmpty(data.getString("count_08"))) {

                        count_8 = data.getJSONArray("count_08");
                        arr8 = new double[count_8.length()];
                        //for(int i=0;i<count_7.length();i++)
                        //  {
                        count_81 = count_8.getJSONArray(1);
                        arr81 = new double[count_81.length()];
                        for (int i = 0; i < count_81.length(); i++) {
                            arr81[i] = count_81.getDouble(i);
                            // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                        }

                        count_82 = count_8.getJSONArray(2);
                        arr82 = new double[count_82.length()];
                        for (int i = 0; i < count_82.length(); i++) {
                            arr82[i] = count_82.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        count_83 = count_8.getJSONArray(0);
                        arr83 = new String[count_83.length()];
                        for (int i = 0; i < count_83.length(); i++) {
                            arr83[i] = count_83.getString(i);
                            //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                        }


                        List<Entry> valsComp1 = new ArrayList<Entry>();
                        List<Entry> valsComp2 = new ArrayList<Entry>();

                        for (int i = 0; i < count_81.length(); i++) {
                            Entry e = new Entry(i, (float) arr81[i]);
                            valsComp1.add(e);
                            //arr82[i]=count_82.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        for (int i = 0; i < count_82.length(); i++) {
                            Entry e = new Entry(i, (float) arr82[i]);
                            valsComp2.add(e);
                            //arr82[i]=count_82.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        LineDataSet setComp1 = new LineDataSet(valsComp1, "Payable");
                        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
                        setComp1.setDrawValues(false);


                        setComp1.setColor(Color.BLUE);
                        setComp1.setFillColor(Color.BLUE);
                        setComp1.setCircleColor(Color.BLUE);
                        setComp1.setDrawFilled(true);


                        LineDataSet setComp2 = new LineDataSet(valsComp2, "VAT");
                        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
                        setComp2.setDrawValues(false);

                        setComp2.setColor(Color.GREEN);
                        setComp2.setFillColor(Color.GREEN);
                        setComp2.setCircleColor(Color.GREEN);
                        setComp2.setDrawFilled(true);


                        List<ILineDataSet> line_dataSets1 = new ArrayList<ILineDataSet>();
                        line_dataSets1.add(setComp1);
                        line_dataSets1.add(setComp2);


                        LineData line_data1 = new LineData(line_dataSets1);
                        linechartPayable1.setData(line_data1);
                        linechartPayable1.invalidate(); // refresh

                        IAxisValueFormatter formatter = new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return arr83[(int) value];
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
                        xAxis_line1.setPosition(XAxis.XAxisPosition.BOTTOM);

                        YAxis yAxisRight = linechartPayable1.getAxisRight();
                        yAxisRight.setEnabled(false);

                        linechartPayable1.setTouchEnabled(true);
                        linechartPayable1.setMarker(mv);
                    }
                    //------------------------------------------------------------------------------
                   if(!TextUtils.isEmpty(data.getString("count_09"))) {
                       count_9 = data.getJSONArray("count_09");

                       //for(int i=0;i<count_7.length();i++)
                       //  {
                       count_91 = count_9.getJSONArray(1);
                       arr91 = new double[count_91.length()];
                       for (int i = 0; i < count_91.length(); i++) {
                           arr91[i] = count_91.getDouble(i);
                           // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                       }

                       count_92 = count_9.getJSONArray(2);
                       arr92 = new double[count_92.length()];
                       for (int i = 0; i < count_92.length(); i++) {
                           arr92[i] = count_92.getDouble(i);
                           // Toast.makeText(DashFinance.this, "value="+arr92[i], Toast.LENGTH_SHORT).show();
                       }

                       count_93 = count_9.getJSONArray(0);
                       arr93 = new String[count_93.length()];
                       for (int i = 0; i < count_93.length(); i++) {
                           arr93[i] = count_93.getString(i);
                           //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                       }


                       List<Entry> valsComp3 = new ArrayList<Entry>();
                       List<Entry> valsComp4 = new ArrayList<Entry>();

                       for (int i = 0; i < count_91.length(); i++) {
                           Entry e = new Entry(i, (float) arr91[i]);
                           valsComp3.add(e);
                           //arr82[i]=count_82.getDouble(i);
                           //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                       }

                       for (int i = 0; i < count_92.length(); i++) {
                           Entry e = new Entry(i, (float) arr92[i]);
                           valsComp4.add(e);
                           //arr82[i]=count_82.getDouble(i);
                           //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                       }

                       LineDataSet setComp3 = new LineDataSet(valsComp3, "Payable Invoice");
                       setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
                       setComp3.setDrawValues(false);

                       setComp3.setColor(Color.BLUE);
                       setComp3.setFillColor(Color.BLUE);
                       setComp3.setCircleColor(Color.BLUE);
                       setComp3.setDrawFilled(true);

                       LineDataSet setComp4 = new LineDataSet(valsComp4, "Payment Voucher");
                       setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);
                       setComp4.setDrawValues(false);

                       setComp4.setColor(Color.GREEN);
                       setComp4.setFillColor(Color.GREEN);
                       setComp4.setDrawFilled(true);
                       setComp4.setCircleColor(Color.GREEN);

                       List<ILineDataSet> line_dataSets2 = new ArrayList<ILineDataSet>();
                       line_dataSets2.add(setComp3);
                       line_dataSets2.add(setComp4);


                       LineData line_data2 = new LineData(line_dataSets2);
                       linechartPayable2.setData(line_data2);
                       linechartPayable2.invalidate(); // refresh

                       linechartPayable2.setTouchEnabled(true);
                       linechartPayable2.setMarker(mv);

                       IAxisValueFormatter formatter2 = new IAxisValueFormatter() {

                           @Override
                           public String getFormattedValue(float value, AxisBase axis) {
                               return arr93[(int) value];
                           }

                           // we don't draw numbers, so no decimal digits needed
                           //   @Override
                           public int getDecimalDigits() {
                               return 0;
                           }
                       };

                       XAxis xAxis_line2 = linechartPayable2.getXAxis();
                       xAxis_line2.setGranularity(1f); // minimum axis-step (interval) is 1
                       xAxis_line2.setValueFormatter(formatter2);
                       xAxis_line2.setPosition(XAxis.XAxisPosition.BOTTOM);


                       YAxis yAxisRight2 = linechartPayable2.getAxisRight();
                       yAxisRight2.setEnabled(false);

                   }
                    //------------------------------------------------------------------------------
                   if(!TextUtils.isEmpty(data.getString("count_10"))) {
                       count_10 = data.getJSONArray("count_10");

                       //for(int i=0;i<count_7.length();i++)
                       //  {
                       count_101 = count_10.getJSONArray(0);
                       count101 = new JSONObject[count_101.length()];
                       arr10a1 = new double[count_101.length()];
                       arr10a2 = new String[count_101.length()];
                       for (int i = 0; i < count_101.length(); i++) {
                           count101[i] = count_101.getJSONObject(i);
                           arr10a1[i] = count101[i].getInt("value");
                           arr10a2[i] = count101[i].getString("Ages");
                           //  Toast.makeText(DashFinance.this, "age="+arr10a2[i]+"value="+ arr10a1[i], Toast.LENGTH_SHORT).show();

                       }

                       List<Entry> valsComp5 = new ArrayList<Entry>();

                       for (int i = 0; i < count_101.length(); i++) {
                           Entry e = new Entry(i, (float) arr10a1[i]);
                           valsComp5.add(e);
                           //arr82[i]=count_82.getDouble(i);
                           //  Toast.makeText(DashFinance.this, "value="+arr10a1[i], Toast.LENGTH_SHORT).show();
                       }


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
                               return arr10a2[(int) value];
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

                       //    linechartPayable3.setTouchEnabled(true);
                       //   linechartPayable3.setMarker(mv);

                       count_102 = count_10.getJSONArray(1);
                       count102 = new JSONObject[count_102.length()];

                       arr10b1 = new double[count_102.length()];
                       arr10b2 = new String[count_102.length()];
                       for (int i = 0; i < count_102.length(); i++) {
                           count102[i] = count_102.getJSONObject(i);
                           arr10b1[i] = count102[i].getInt("value");
                           arr10b2[i] = count102[i].getString("name");

                       }


                       List<SliceValue> pieData = new ArrayList<>();
                       for (int i = 0; i < count_102.length(); i++) {
                      //     Toast.makeText(DashFinance.this, "in 10 loop", Toast.LENGTH_SHORT).show();
                           pieData.add(new SliceValue((float) arr10b1[i], MY_COLORS[i]).setLabel(arr10b2[i]+"="+arr10b1[i]));

                       }
//                    pieData.add(new SliceValue(15, R.color.colorAccentLight));
//                    pieData.add(new SliceValue(25, Color.LTGRAY));
//                    pieDataadd(new SliceValue(10, Color.GREEN));
//                    pieData.add(new SliceValue(60, Color.CYAN));
//
                       PieChartData pieChartData = new PieChartData(pieData);

//                    pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
//                    pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
//                    pieData.add(new SliceValue(10, Color.CYAN).setLabel("Q3: $18"));
//                    pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
                       pieChartData.setHasLabels(true);
                     //  pieChartData.hasLabelsOutside();
                       pieChartData.setHasLabelsOutside(true);
                       pieChartpayable.setPieChartData(pieChartData);


                   }
                    if(!TextUtils.isEmpty(data.getString("count_11")))
                    { count11 = data.getInt("count_11");
                        gauge1.setSpeed(count11);
                    }



                    if(!TextUtils.isEmpty(data.getString("count_12"))) {
                        count12 = data.getInt("count_12");
                        gauge3.setSpeed(count12);

                    }
                 //   gauge1.setMinValue(0);
                 //   gauge1.setMaxValue(100);


//..................Count13..........................................................................//
                   if(!TextUtils.isEmpty(data.getString("count_13"))) {
                       count_13 = data.getJSONArray("count_13");
                       count_132 = count_13.getJSONArray(1);
                       //    Toast.makeText(DashFinance.this, "arrayindex=" +count_132.length(), Toast.LENGTH_SHORT).show();
                       count132 = new JSONObject[count_132.length()];
//                    array122=new String[countarray_122.length()];
                       arr13a = new double[count_132.length()];
                       arr13b = new String[count_132.length()];
                       for (int i = 0; i < count_132.length(); i++) {

                           count132[i] = count_132.getJSONObject(i);
                           arr13a[i] = count132[i].getDouble("value");
                           arr13b[i] = count132[i].getString("label");

                       }
                       // Toast.makeText(DashFinance.this, "count 13=" + count_132.length(), Toast.LENGTH_SHORT).show();

                       List<SliceValue> pieData1 = new ArrayList<>();
                       for (int i = 0; i < count_132.length(); i++) {
                           //   Toast.makeText(DashFinance.this, "value="+arr13a[i], Toast.LENGTH_SHORT).show();
                           pieData1.add(new SliceValue((float) arr13a[i], MY_COLORS[i]).setLabel(arr13b[i] + "=" + arr13a[i]));

                       }
//                    pieData.add(new SliceValue(15, R.color.colorAccentLight));
//                    pieData.add(new SliceValue(25, Color.LTGRAY));
//                    pieDataadd(new SliceValue(10, Color.GREEN));
//                    pieData.add(new SliceValue(60, Color.CYAN));
//
                       PieChartData pieChartData1 = new PieChartData(pieData1);

//                    pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
//                    pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
//                    pieData.add(new SliceValue(10, Color.CYAN).setLabel("Q3: $18"));
//                    pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
                       pieChartData1.setHasLabels(true);
                       pieChartData1.setHasLabelsOutside(true);
                       pieChartpayble1.setPieChartData(pieChartData1);
                   }
                   //..................Count14..........................................................................//

                    if(!TextUtils.isEmpty(data.getString("count_14"))) {
                        count_14 = data.getJSONArray("count_14");
                        count_142 = count_14.getJSONArray(1);
                        //    Toast.makeText(DashFinance.this, "arrayindex=" +count_132.length(), Toast.LENGTH_SHORT).show();
                        count142 = new JSONObject[count_142.length()];
//                    array122=new String[countarray_122.length()];
                        arr14a = new double[count_142.length()];
                        arr14b = new String[count_142.length()];
                        for (int i = 0; i < count_142.length(); i++) {

                            count142[i] = count_142.getJSONObject(i);
                            arr14a[i] = count142[i].getDouble("value");
                            arr14b[i] = count142[i].getString("name");

                        }
                        // Toast.makeText(DashFinance.this, "count 13=" + count_132.length(), Toast.LENGTH_SHORT).show();

                        List<SliceValue> pieData1 = new ArrayList<>();
                        for (int i = 0; i < count_142.length(); i++) {
                            //   Toast.makeText(DashFinance.this, "value="+arr13a[i], Toast.LENGTH_SHORT).show();
                            pieData1.add(new SliceValue((float) arr14a[i], MY_COLORS[i]).setLabel(arr14b[i]+"="+arr14a[i]));

                        }
//                    pieData.add(new SliceValue(15, R.color.colorAccentLight));
//                    pieData.add(new SliceValue(25, Color.LTGRAY));
//                    pieDataadd(new SliceValue(10, Color.GREEN));
//                    pieData.add(new SliceValue(60, Color.CYAN));
//
                        PieChartData pieChartData1 = new PieChartData(pieData1);

//                    pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
//                    pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
//                    pieData.add(new SliceValue(10, Color.CYAN).setLabel("Q3: $18"));
//                    pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));
                        pieChartData1.setHasLabels(true);
                        pieChartData1.setHasLabelsOutside(true);
                        pieChartpayble2.setPieChartData(pieChartData1);
                    }

                } //end try

                catch (JSONException e)
                {
                    if ( progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(DashFinance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                  //  count0.setText("Error:" + e.getMessage());
                }
            }



        }, new Response.ErrorListener()
        { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error)
            {
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
                Toast.makeText(DashFinance.this, message, Toast.LENGTH_SHORT).show();
                if ( progressDialog.isShowing())
                    progressDialog.hide();
            }
        });

        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();




    /*    ArrayList<String> labels = new ArrayList<String>();
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

        barChart2.animateY(5000);   */

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


    private void showInfoDialog(String[] arr73, String[] arr74, String heading1, String heading2){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.info1_barchartpayable);
        dialog.setCancelable(true);

        TableLayout tab1=(TableLayout) dialog.findViewById(R.id.tabinfo1);
        tab1.removeAllViews();

        TableRow tableRowHeading = new TableRow(this);

        TextView th = new TextView(this);
        TextView th1 = new TextView(this);

        th.setPadding(10, 10, 10, 10);
        th.setAllCaps(TRUE);
        th1.setAllCaps(TRUE);
        th.setGravity(Gravity.CENTER);
        th.setText(heading1);
        th1.setPadding(10, 10, 10, 10);


        th1.setGravity(Gravity.CENTER);
        th1.setText(heading2);
        tableRowHeading.addView(th);
        tableRowHeading.addView(th1);

        tab1.addView(tableRowHeading);
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, 1));
        v.setBackgroundColor(Color.rgb(51, 51, 51));
        tab1.addView(v);

        for(int i=0;i<arr73.length;i++)
        {
         //   Toast.makeText(this, "in loop", Toast.LENGTH_SHORT).show();
            TableRow tableRow = new TableRow(this);

            TextView tv = new TextView(this);
            TextView tv1 = new TextView(this);

            tv.setPadding(10, 10, 10, 10);

            tv.setGravity(Gravity.LEFT);
            tv.setText(arr74[i]);
            tv1.setPadding(10, 10, 10, 10);

            tv1.setGravity(Gravity.LEFT);
            tv1.setText(arr73[i]);

            tableRow.addView(tv);
            tableRow.addView(tv1);

            tab1.addView(tableRow);
            View v1 = new View(this);
            v1.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1));
            v1.setBackgroundColor(Color.rgb(51, 51, 51));
            tab1.addView(v1);
        }
dialog.show();

    }
}
