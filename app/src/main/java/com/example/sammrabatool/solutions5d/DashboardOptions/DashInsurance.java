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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.sammrabatool.solutions5d.OTL.CheckIn;
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
import java.util.HashMap;
import java.util.List;

public class DashInsurance extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{
    private Toolbar toolbar;
    String   instanceStr,  userID, token, count1,count2,count3,count4,count5,count6;
    JSONArray countarray_7 = null, countarray_71 = null, countarray_72 = null, countarray_73 = null,countarray_74=null, countarray_8 = null,
            countarray_81 = null, countarray_82 = null, countarray_83 = null,countarray_84=null, countarray_9=null,countarray_91=null,
            countarray_92=null,countarray_93=null,countarray_94=null,countarray_10=null,countarray_101=null,countarray_102=null,countarray_103=null,countarray_104=null,
    countarray_11=null,countarray_111=null,countarray_112=null,countarray_12=null,countarray_121=null;
    JSONObject countarray111[],countarray121[];
    JSONArray supplierArray=null, subtypeArray=null, makesArray=null;
    JSONObject supplierDetail[]=null, subtypeDetail[]=null, makesDetail[]=null;
    double  array7[], array71[],array72[],array74[], array8[], array81[], array82[],array84[], array9[],array91[],array92[],array94[],array10[],array101[],array102[],
            array11[],array111a[],array12[],array121a[], array104[];
    String array73[], array83[],array93[],array103[],array112b[],array122b[];
    String supplierID, suppliertName, subtypeID, subtypeName, makesID, makesName;
    int lg, bg;
    TextView  countI1,countI2,countI3,countI4,countI5,countI6;
    String selectedSupplier1="0",selectedSupplier2="", selectedSupplier3="", selectedSupplier4="", selectedSubtype1="", selectedSubtype2="", selectedMakes1="", selectedMakes2="";

    Button search1, search2, search3, search4;

    Spinner s1, s2, s3, s4, s5, s6, s7, s8;
    List<String> supplier = new ArrayList<String>();
    List<String> subtype = new ArrayList<String>();
    List<String> makes = new ArrayList<String>();

    HashMap<Integer,String> hashSpinnerSupplier = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerSubtype = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerMakes = new HashMap<Integer, String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_insurance);
        final ProgressDialog progressDialog = new ProgressDialog(this);
     //   count0 = (TextView) findViewById(R.id.count0);
    //    count00 = (TextView) findViewById(R.id.count00);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);

//        Toast.makeText(this, "data=" + userID + " " + instanceStr + " " + token + " " + lg + " " + bg, Toast.LENGTH_LONG).show();


        initToolbar();
        final CustomMarkerView mv = new CustomMarkerView(this, R.layout.tv_content);
        final LineChart linechartPayable1 = (LineChart) findViewById(R.id.linechartisurrance1);
        final LineChart linechartPayable2 = (LineChart) findViewById(R.id.linechartisurrance2);
        final BarChart barchartinsurr1 = (BarChart) findViewById(R.id.barchart);
        final BarChart barchartinsurr2 = (BarChart) findViewById(R.id.barchart1);
        final LineChart linechartPayable3 = (LineChart) findViewById(R.id.linechartisurrance3);
        final LineChart linechartPayable4 = (LineChart) findViewById(R.id.linechartisurrance4);


        countI1=(TextView) findViewById(R.id.countI1);
        countI2=(TextView) findViewById(R.id.countI2);
        countI3=(TextView) findViewById(R.id.countI3);
        countI4=(TextView) findViewById(R.id.countI4);
        countI5=(TextView) findViewById(R.id.countI5);
        countI6=(TextView) findViewById(R.id.countI6);

        search1=(Button) findViewById(R.id.searchInsurance1);
        search2=(Button) findViewById(R.id.searchInsurance2);
        search3=(Button) findViewById(R.id.searchInsurance3);
        search4=(Button) findViewById(R.id.searchInsurance4);


        s1=(Spinner) findViewById(R.id.spinnerInsurance1);
        s2=(Spinner) findViewById(R.id.spinnerInsurance2);
        s3=(Spinner) findViewById(R.id.spinnerInsurance3);
        s4=(Spinner) findViewById(R.id.spinnerInsurance4);
        s5=(Spinner) findViewById(R.id.spinnerInsurance5);
        s6=(Spinner) findViewById(R.id.spinnerInsurance6);
        s7=(Spinner) findViewById(R.id.spinnerInsurance7);
        s8=(Spinner) findViewById(R.id.spinnerInsurance8);

        supplier.add("Select Supplier");// for showing
        hashSpinnerSupplier.put(0, "0");//hashspinner for set the key and value
        subtype.add("Select Subtype");
        hashSpinnerSubtype.put(0,"0");
        makes.add("Select Makes");
        hashSpinnerMakes.put(0,"0");


        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        s3.setOnItemSelectedListener(this);
        s4.setOnItemSelectedListener(this);
        s5.setOnItemSelectedListener(this);
        s6.setOnItemSelectedListener(this);
        s7.setOnItemSelectedListener(this);
        s8.setOnItemSelectedListener(this);
//...................setting the value of spinner..............//
        ArrayAdapter sup1Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,supplier);
        sup1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s1.setAdapter(sup1Adapter);

        ArrayAdapter sup2Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,supplier);
        sup2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s2.setAdapter(sup2Adapter);

        ArrayAdapter sub1Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,subtype);
        sub1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s3.setAdapter(sub1Adapter);

        ArrayAdapter make1Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,makes);
        make1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s4.setAdapter(make1Adapter);

        ArrayAdapter sup3Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,supplier);
        sup3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s5.setAdapter(sup3Adapter);

        ArrayAdapter sub2Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,subtype);
        sub2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s6.setAdapter(sub2Adapter);

        ArrayAdapter make2Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,makes);
        make2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s7.setAdapter(make2Adapter);

        ArrayAdapter sup4Adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,supplier);
        sup4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        s8.setAdapter(sup4Adapter);


        //------------------------------buttons-------------------------------------------
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
                String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getLastSixMonthInsuranceTrendCompanyWise1/" + lg + "/" + "1" + "/" + selectedSupplier1;
                StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            if (progressDialog.isShowing())
                                progressDialog.hide();

//..............................count9.........................................................//
                            if (data.getJSONArray("count_09").length()>0){

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
                                countarray_94 = countarray_9.getJSONArray(3);
                                array94 = new double[countarray_94.length()];
                                for (int i = 0; i < countarray_94.length(); i++) {
                                    array94[i] = countarray_94.getDouble(i);
                                    //                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                                }


                                ArrayList<BarEntry> graphrcv1 = new ArrayList<>();
                                for (int i = 0; i < array91.length; i++) {
                                    graphrcv1.add(new BarEntry(i, (float) array91[i]));
                                    //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                                }
                                ArrayList<BarEntry> graphrcv2 = new ArrayList<>();
                                for (int i = 0; i < array92.length; i++)
                                    graphrcv2.add(new BarEntry(i, (float) array92[i]));

                                ArrayList<BarEntry> graphrcv3 = new ArrayList<>();
                                for (int i = 0; i < array94.length; i++)
                                    graphrcv3.add(new BarEntry(i, (float) array94[i]));
//                    ArrayList<BarEntry> graphrcv3 = new ArrayList<>();
//
//                    for (int i = 0; i < array94.length; i++)
//                        graphrcv3.add(new BarEntry(i, (float) array94[i]));

                                ArrayList<String> labels_graphrcv1 = new ArrayList<String>();
                                for (int i = 0; i < array93.length; i++)
                                    labels_graphrcv1.add(array93[i]);


                                BarDataSet bardatasetrcv = new BarDataSet(graphrcv1, "Motor");
                                BarDataSet bardatasetrcv2 = new BarDataSet(graphrcv2, "Medical");
                                BarDataSet bardatasetrcv3 = new BarDataSet(graphrcv3, "General");
//                    BarDataSet bardatasetrcv3 = new BarDataSet(graphrcv3, "Cells");
                                //   BarData dataBar = new BarData(labels_graph1,bardataset);

                                bardatasetrcv.setColors(Color.BLUE);
                                bardatasetrcv2.setColors(Color.GREEN);
                                bardatasetrcv3.setColors(Color.RED);
                                bardatasetrcv.setDrawValues(false);//bydefault values hide,and on touch screen the values will show
                                bardatasetrcv2.setDrawValues(false);
                                bardatasetrcv3.setDrawValues(false);


//                    bardatasetrcv3.setColors(new int[]{R.color.amber_700});

                                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                                dataSets.add(bardatasetrcv);
                                dataSets.add(bardatasetrcv2);
                                dataSets.add(bardatasetrcv3);


                                BarData datarcv1 = new BarData(dataSets);
                                datarcv1.setBarWidth(0.1667f);
                                //  float defaultBarWidth = (1 - 0.4f)/graphrcv3.size()  - 0.05f;
                                //  float dw=(0.05f+0.25f))* graphrcv3.size()+0.45f;
                                //  Toast.makeText(DashInsurance.this, "defaultBarWidth="+defaultBarWidth, Toast.LENGTH_SHORT).show();
                                // datarcv1.setBarWidth(defaultBarWidth);
                                barchartinsurr1.setData(datarcv1);
                                barchartinsurr1.groupBars(0f, 0.5f, 0f); // perform the "explicit" grouping
                                // barChart.invalidate(); // refresh
                                //  barchartinsurr1.getDescription().setText("Set Bar Chart Description");  // set the description
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
                                // xAxis.setAxisMaximum(countarray_93.length());

                                xAxis.setAxisMaximum(barchartinsurr1.getBarData().getGroupWidth(0.4f, 0.05f) * graphrcv3.size());
                                xAxis.setLabelCount(array93.length);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graphrcv1));

                                barchartinsurr1.setTouchEnabled(true);//allow to touch the chart and see the values
                                barchartinsurr1.setMarker(mv);
                            }
                            else
                                barchartinsurr1.clear();


                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(DashInsurance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  count0.setText("Error:" + e.getMessage());
                            barchartinsurr1.clear();
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
        });

        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
                String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getLastSixMonthInsuranceTrendCompanyWise1/" + lg + "/" + "2" + "/" + selectedSupplier2;
                StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            if (progressDialog.isShowing())
                                progressDialog.hide();
//..............................count10.....................................................//
                            if (data.getJSONArray("count_10").length()>0){

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

                                countarray_104 = countarray_10.getJSONArray(3);
                                array104 = new double[countarray_104.length()];
                                for (int i = 0; i < countarray_104.length(); i++) {
                                    array104[i] = countarray_104.getDouble(i);
                                    // Toast.makeText(DashFinance.this, "value="+arr92[i], Toast.LENGTH_SHORT).show();
                                }

                                ArrayList<BarEntry> graphinsu2a = new ArrayList<>();
                                for (int i = 0; i < array101.length; i++) {
                                    graphinsu2a.add(new BarEntry(i, (float) array101[i]));
                                    //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                                }
                                ArrayList<BarEntry> graphinsu2b = new ArrayList<>();
                                for (int i = 0; i < array102.length; i++)
                                    graphinsu2b.add(new BarEntry(i, (float) array102[i]));

                                ArrayList<BarEntry> graphinsu2c = new ArrayList<>();
                                for (int i = 0; i < array104.length; i++)
                                    graphinsu2c.add(new BarEntry(i, (float) array104[i]));

                                ArrayList<String> labels_graphinsurr1 = new ArrayList<String>();
                                for (int i = 0; i < array103.length; i++)
                                    labels_graphinsurr1.add(array103[i]);


                                BarDataSet bardatasetinsur = new BarDataSet(graphinsu2a, "Motor");
                                BarDataSet bardatasetinsur2 = new BarDataSet(graphinsu2b, "Medical");
                                BarDataSet bardatasetinsur3 = new BarDataSet(graphinsu2c, "General");
                                //   BarData dataBar = new BarData(labels_graph1,bardataset);

                                bardatasetinsur.setColors(Color.BLUE);
                                bardatasetinsur2.setColors(Color.GREEN);
                                bardatasetinsur3.setColors(Color.RED);
                                bardatasetinsur.setDrawValues(false);
                                bardatasetinsur2.setDrawValues(false);
                                bardatasetinsur3.setDrawValues(false);

                                ArrayList<IBarDataSet> dataSetsinsu = new ArrayList<IBarDataSet>();
                                dataSetsinsu.add(bardatasetinsur);
                                dataSetsinsu.add(bardatasetinsur2);
                                dataSetsinsu.add(bardatasetinsur3);

                                BarData datainsu1 = new BarData(dataSetsinsu);
                                datainsu1.setBarWidth(0.1667f);
                                barchartinsurr2.setData(datainsu1);
                                barchartinsurr2.groupBars(0f, 0.5f, 0f); // perform the "explicit" grouping
                                // barChart.invalidate(); // refresh
                                // barchartinsurr2.getDescription().setText("Set Bar Chart Description");  // set the description
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
                                xAxis1.setAxisMaximum(barchartinsurr2.getBarData().getGroupWidth(0.4f, 0.05f) * graphinsu2c.size());
                                xAxis1.setLabelCount(array103.length);


                                xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis1.setValueFormatter(new IndexAxisValueFormatter(labels_graphinsurr1));

                                barchartinsurr2.setTouchEnabled(true);
                                barchartinsurr2.setMarker(mv);
                            }

                            else
                                barchartinsurr2.clear();





                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(DashInsurance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  count0.setText("Error:" + e.getMessage());
                            barchartinsurr2.clear();
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
        });

        search3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
                String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getLastSixMonthInsuranceTrendCompanyWise2/" + lg + "/" + "3" + "/" + selectedSubtype1+"/"+selectedMakes1+"/"+selectedSupplier3;
                StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            if (progressDialog.isShowing())
                                progressDialog.hide();

//........................................count11....................................................//
                            if (data.getJSONArray("count_11").length()>0) {
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
                              //  setComp5.setDrawValues(false);
                                setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
                                 setComp5.setDrawValues(true);

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
                                xAxis_line3.setLabelCount(array111a.length);
                                YAxis yAxisRight3 = linechartPayable3.getAxisRight();
                                yAxisRight3.setEnabled(false);



                            }

                            else
                                linechartPayable3.clear();


                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(DashInsurance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  count0.setText("Error:" + e.getMessage());
                            linechartPayable3.clear();

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
        });

        search4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
                String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getLastSixMonthInsuranceTrendCompanyWise2/" + lg + "/" + "4" + "/" + selectedSubtype2+"/"+selectedMakes2+"/"+selectedSupplier4;
                StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            if (progressDialog.isShowing())
                                progressDialog.hide();

//...................................count12..............................................//
                            if(!TextUtils.isEmpty(data.getString("count_12"))) {
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
                                setComp6.setDrawValues(true);

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
                                xAxis_line4.setLabelCount(array121a.length);

                                YAxis yAxisRight4 = linechartPayable4.getAxisRight();
                                yAxisRight4.setEnabled(false);



                            }

                            else
                                linechartPayable4.clear();




                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(DashInsurance.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  count0.setText("Error:" + e.getMessage());
                            linechartPayable4.clear();

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
        });
        //--------------------------------------------------------------------------------




        RequestQueue MyRequestQueue = Volley.newRequestQueue(DashInsurance.this);
        String urlrecv = "http://" + instanceStr + ".5dsurf.com/app/webservice/getInsuranceStatictics/" + bg + "/" + lg + "/" + userID + "/" + token;
        StringRequest insurrStringREquest = new StringRequest(Request.Method.GET, urlrecv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response);
                    if (progressDialog.isShowing())
                        progressDialog.hide();
//------------------------------getting sppiners data-----------------------------------------------------

                    if(data.getJSONArray("supplier").length()>0) {
                        supplierArray = data.getJSONArray("supplier");
                        //  if(otlPorjectArray!=null) {
                        supplierDetail = new JSONObject[supplierArray.length()];
                        for (int i = 0; i < supplierArray.length(); i++) {
                            supplierDetail[i] = supplierArray.getJSONObject(i);
                            supplierID = supplierDetail[i].getString("id");
                            suppliertName = supplierDetail[i].getString("name");
                            supplier.add(suppliertName);
                            hashSpinnerSupplier.put(i + 1, supplierID);
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }

                        //-------setting data to spinner----------------
                        ArrayAdapter sup1Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, supplier);
                        sup1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s1.setAdapter(sup1Adapter);

                        ArrayAdapter sup2Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, supplier);
                        sup2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s2.setAdapter(sup2Adapter);

                        ArrayAdapter sup3Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, supplier);
                        sup3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s5.setAdapter(sup3Adapter);

                        ArrayAdapter sup4Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, supplier);
                        sup4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s8.setAdapter(sup4Adapter);
                    }

                    if(data.getJSONArray("subtype").length()>0) {
                        subtypeArray = data.getJSONArray("subtype");
                        //  if(otlPorjectArray!=null) {
                        subtypeDetail = new JSONObject[subtypeArray.length()];
                        for (int i = 0; i < subtypeArray.length(); i++) {
                            subtypeDetail[i] = subtypeArray.getJSONObject(i);
                            subtypeID = subtypeDetail[i].getString("id");
                            subtypeName = subtypeDetail[i].getString("name");
                            subtype.add(subtypeName);
                            hashSpinnerSubtype.put(i + 1, subtypeID);
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }

                        ArrayAdapter sub1Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, subtype);
                        sub1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s3.setAdapter(sub1Adapter);

                        ArrayAdapter sub2Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, subtype);
                        sub2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s6.setAdapter(sub2Adapter);
                    }

                    if(data.getJSONArray("makes").length()>0) {
                        makesArray = data.getJSONArray("makes");
                        //  if(otlPorjectArray!=null) {
                        makesDetail = new JSONObject[makesArray.length()];
                        for (int i = 0; i < makesArray.length(); i++) {
                            makesDetail[i] = makesArray.getJSONObject(i);
                            makesID = makesDetail[i].getString("id");
                            makesName = makesDetail[i].getString("name");
                            makes.add(makesName);
                            hashSpinnerMakes.put(i + 1, makesID);
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }

                        ArrayAdapter mak1Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, makes);
                        mak1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s4.setAdapter(mak1Adapter);

                        ArrayAdapter mak2Adapter = new ArrayAdapter(DashInsurance.this, android.R.layout.simple_spinner_item, makes);
                        mak2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        s7.setAdapter(mak2Adapter);
                    }
//--------------------------------------------------------------------------------------------------------
                    if(!TextUtils.isEmpty(data.getString("count_01"))) {
                        count1 = data.getString("count_01");
                        countI1.setText(count1);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_02"))) {
                        count2 = data.getString("count_02");
                        countI2.setText(count2);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_03"))) {
                        count3 = data.getString("count_03");
                        countI3.setText(count3);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_04"))) {
                        count4 = data.getString("count_04");
                        countI4.setText(count4);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_05"))) {
                        count5 = data.getString("count_05");
                        countI5.setText(count5);
                    }
                    if(!TextUtils.isEmpty(data.getString("count_06"))) {
                        count6 = data.getString("count_06");
                        countI6.setText(count6);
                    }

                    if(!TextUtils.isEmpty(data.getString("count_07"))) {
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

                        LineDataSet setComp1 = new LineDataSet(valsComp1, "Motor");
                        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setComp1.setColor(Color.BLUE);
                        setComp1.setFillColor(Color.BLUE);
                        setComp1.setCircleColor(Color.BLUE);
                        setComp1.setDrawFilled(true);
                        setComp1.setDrawValues(false);

                        LineDataSet setComp2 = new LineDataSet(valsComp2, "Medical");
                        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setComp2.setColor(Color.GREEN);
                        setComp2.setFillColor(Color.GREEN);
                        setComp2.setCircleColor(Color.GREEN);
                        setComp2.setDrawFilled(true);
                        setComp2.setDrawValues(false);

                        LineDataSet setComp3 = new LineDataSet(valsComp3, "General");
                        setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setComp3.setColor(Color.RED);
                        setComp3.setFillColor(Color.RED);
                        setComp3.setCircleColor(Color.RED);
                        setComp3.setDrawFilled(true);
                        setComp3.setDrawValues(false);

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
                        linechartPayable1.setTouchEnabled(true);
                        linechartPayable1.setMarker(mv);
                    }

//...................................count8....................................................//
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

                        countarray_84 = countarray_8.getJSONArray(3);
                        array84 = new double[countarray_84.length()];
                        for (int i = 0; i < countarray_84.length(); i++) {
                            array84[i] = countarray_84.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        List<Entry> valsCompa = new ArrayList<Entry>();
                        List<Entry> valsCompb = new ArrayList<Entry>();
                        List<Entry> valsCompc = new ArrayList<Entry>();

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

                        for (int i = 0; i < countarray_84.length(); i++) {
                            Entry e = new Entry(i, (float) array84[i]);
                            valsCompc.add(e);
                            //arr82[i]=count_82.getDouble(i);
                            //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                        }

                        LineDataSet setCompa = new LineDataSet(valsCompa, "Motor");
                        setCompa.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setCompa.setColor(Color.BLUE);
                        setCompa.setFillColor(Color.BLUE);
                        setCompa.setCircleColor(Color.BLUE);
                        setCompa.setDrawFilled(true);
                        setCompa.setDrawValues(false);

                        LineDataSet setCompb = new LineDataSet(valsCompb, "Medical");
                        setCompb.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setCompb.setColor(Color.GREEN);
                        setCompb.setFillColor(Color.GREEN);
                        setCompb.setCircleColor(Color.GREEN);
                        setCompb.setDrawFilled(true);
                        setCompb.setDrawValues(false);

                        LineDataSet setCompc = new LineDataSet(valsCompc, "General");
                        setCompc.setAxisDependency(YAxis.AxisDependency.LEFT);

                        setCompc.setColor(Color.RED);
                        setCompc.setFillColor(Color.RED);
                        setCompc.setCircleColor(Color.RED);
                        setCompc.setDrawFilled(true);
                        setCompc.setDrawValues(false);


                        List<ILineDataSet> line_dataSetsa = new ArrayList<ILineDataSet>();
                        line_dataSetsa.add(setCompa);
                        line_dataSetsa.add(setCompb);
                        line_dataSetsa.add(setCompc);


                        LineData line_data2 = new LineData(line_dataSetsa);
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
                        xAxis_line2.setValueFormatter(formatter1);
                        linechartPayable2.setTouchEnabled(true);
                        linechartPayable2.setMarker(mv);
                    }
//..............................count9.........................................................//
                    if (data.getJSONArray("count_09").length()>0){
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
                        countarray_94 = countarray_9.getJSONArray(3);
                        array94 = new double[countarray_94.length()];
                        for (int i = 0; i < countarray_94.length(); i++) {
                            array94[i] = countarray_94.getDouble(i);
    //                        //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                        }


                        ArrayList<BarEntry> graphrcv1 = new ArrayList<>();
                        for (int i = 0; i < array91.length; i++) {
                            graphrcv1.add(new BarEntry(i, (float) array91[i]));
                            //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<BarEntry> graphrcv2 = new ArrayList<>();
                        for (int i = 0; i < array92.length; i++)
                            graphrcv2.add(new BarEntry(i, (float) array92[i]));

                        ArrayList<BarEntry> graphrcv3 = new ArrayList<>();
                        for (int i = 0; i < array94.length; i++)
                            graphrcv3.add(new BarEntry(i, (float) array94[i]));
//                    ArrayList<BarEntry> graphrcv3 = new ArrayList<>();
//
//                    for (int i = 0; i < array94.length; i++)
//                        graphrcv3.add(new BarEntry(i, (float) array94[i]));

                        ArrayList<String> labels_graphrcv1 = new ArrayList<String>();
                        for (int i = 0; i < array93.length; i++)
                            labels_graphrcv1.add(array93[i]);


                        BarDataSet bardatasetrcv = new BarDataSet(graphrcv1, "Motor");
                        BarDataSet bardatasetrcv2 = new BarDataSet(graphrcv2, "Medical");
                        BarDataSet bardatasetrcv3 = new BarDataSet(graphrcv3, "General");
//                    BarDataSet bardatasetrcv3 = new BarDataSet(graphrcv3, "Cells");
                        //   BarData dataBar = new BarData(labels_graph1,bardataset);

                        bardatasetrcv.setColors(Color.BLUE);
                        bardatasetrcv2.setColors(Color.GREEN);
                        bardatasetrcv3.setColors(Color.RED);


                        bardatasetrcv.setDrawValues(false);
                        bardatasetrcv2.setDrawValues(false);
                        bardatasetrcv3.setDrawValues(false);


//                    bardatasetrcv3.setColors(new int[]{R.color.amber_700});

                        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                        dataSets.add(bardatasetrcv);
                        dataSets.add(bardatasetrcv2);
                        dataSets.add(bardatasetrcv3);


                        BarData datarcv1 = new BarData(dataSets);
                        datarcv1.setBarWidth(0.1667f);
                      //  float defaultBarWidth = (1 - 0.4f)/graphrcv3.size()  - 0.05f;
                      //  float dw=(0.05f+0.25f))* graphrcv3.size()+0.45f;
                      //  Toast.makeText(DashInsurance.this, "defaultBarWidth="+defaultBarWidth, Toast.LENGTH_SHORT).show();
                       // datarcv1.setBarWidth(defaultBarWidth);
                        barchartinsurr1.setData(datarcv1);
                        barchartinsurr1.groupBars(0f, 0.5f, 0f); // perform the "explicit" grouping
                        // barChart.invalidate(); // refresh
                      //  barchartinsurr1.getDescription().setText("Set Bar Chart Description");  // set the description
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
                       // xAxis.setAxisMaximum(countarray_93.length());

                        xAxis.setAxisMaximum(barchartinsurr1.getBarData().getGroupWidth(0.4f, 0.05f) * graphrcv3.size());
                        xAxis.setLabelCount(array93.length);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graphrcv1));
                        barchartinsurr1.setTouchEnabled(true);
                        barchartinsurr1.setMarker(mv);
                    }
//..............................count10.....................................................//
                    if (data.getJSONArray("count_10").length()>0) {
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

                        countarray_104 = countarray_10.getJSONArray(3);
                        array104 = new double[countarray_104.length()];
                        for (int i = 0; i < countarray_104.length(); i++) {
                            array104[i] = countarray_104.getDouble(i);
                            // Toast.makeText(DashFinance.this, "value="+arr92[i], Toast.LENGTH_SHORT).show();
                        }

                        ArrayList<BarEntry> graphinsu2a = new ArrayList<>();
                        for (int i = 0; i < array101.length; i++) {
                            graphinsu2a.add(new BarEntry(i, (float) array101[i]));
                            //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<BarEntry> graphinsu2b = new ArrayList<>();
                        for (int i = 0; i < array102.length; i++)
                            graphinsu2b.add(new BarEntry(i, (float) array102[i]));

                        ArrayList<BarEntry> graphinsu2c = new ArrayList<>();
                        for (int i = 0; i < array104.length; i++)
                            graphinsu2c.add(new BarEntry(i, (float) array104[i]));

                        ArrayList<String> labels_graphinsurr1 = new ArrayList<String>();
                        for (int i = 0; i < array103.length; i++)
                            labels_graphinsurr1.add(array103[i]);


                        BarDataSet bardatasetinsur = new BarDataSet(graphinsu2a, "Motor");
                        BarDataSet bardatasetinsur2 = new BarDataSet(graphinsu2b, "Medical");
                        BarDataSet bardatasetinsur3 = new BarDataSet(graphinsu2c, "General");
                        //   BarData dataBar = new BarData(labels_graph1,bardataset);

                        bardatasetinsur.setColors(Color.BLUE);
                        bardatasetinsur2.setColors(Color.GREEN);
                        bardatasetinsur3.setColors(Color.RED);

                        bardatasetinsur.setDrawValues(false);
                        bardatasetinsur2.setDrawValues(false);
                        bardatasetinsur3.setDrawValues(false);

                        ArrayList<IBarDataSet> dataSetsinsu = new ArrayList<IBarDataSet>();
                        dataSetsinsu.add(bardatasetinsur);
                        dataSetsinsu.add(bardatasetinsur2);
                        dataSetsinsu.add(bardatasetinsur3);

                        BarData datainsu1 = new BarData(dataSetsinsu);
                        datainsu1.setBarWidth(0.1667f);
                        barchartinsurr2.setData(datainsu1);
                        barchartinsurr2.groupBars(0f, 0.5f, 0f); // perform the "explicit" grouping
                        // barChart.invalidate(); // refresh
                       // barchartinsurr2.getDescription().setText("Set Bar Chart Description");  // set the description
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
                        xAxis1.setAxisMaximum(barchartinsurr2.getBarData().getGroupWidth(0.4f, 0.05f) * graphinsu2c.size());
                        xAxis1.setLabelCount(array103.length);

                        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis1.setValueFormatter(new IndexAxisValueFormatter(labels_graphinsurr1));
                        barchartinsurr2.setTouchEnabled(true);
                        barchartinsurr2.setMarker(mv);

                    }
//........................................count11....................................................//
                    if (data.getJSONArray("count_11").length()>0) {
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
                        xAxis_line3.setLabelCount(array111a.length);
                        YAxis yAxisRight3 = linechartPayable3.getAxisRight();
                        yAxisRight3.setEnabled(false);
                    }
//...................................count12..............................................//
                    if (data.getJSONArray("count_12").length()>0){
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
                    xAxis_line4.setLabelCount(array121a.length);

                    YAxis yAxisRight4 = linechartPayable4.getAxisRight();
                    yAxisRight4.setEnabled(false);


                }

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

    //----------------------spinner methods----------------
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();

        if (parent.getId() == R.id.spinnerInsurance1 && position!=0) {
          //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSupplier1=hashSpinnerSupplier.get(s1.getSelectedItemPosition());
        }

        if (parent.getId() == R.id.spinnerInsurance2 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSupplier2=hashSpinnerSupplier.get(s2.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance5 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSupplier3=hashSpinnerSupplier.get(s5.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance8 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSupplier4=hashSpinnerSupplier.get(s8.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance3 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSubtype1=hashSpinnerSubtype.get(s3.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance6 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedSubtype2=hashSpinnerSubtype.get(s6.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance4 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedMakes1=hashSpinnerMakes.get(s4.getSelectedItemPosition());
        }
        if (parent.getId() == R.id.spinnerInsurance7 && position!=0) {
            //  selectedTypeID=hashSpinnerType.get(type.getSelectedItemPosition());
            selectedMakes2=hashSpinnerMakes.get(s7.getSelectedItemPosition());
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
