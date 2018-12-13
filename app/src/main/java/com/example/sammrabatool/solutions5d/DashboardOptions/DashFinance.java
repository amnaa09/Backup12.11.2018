package com.example.sammrabatool.solutions5d.DashboardOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashFinance extends AppCompatActivity {
    private Toolbar toolbar;
    String   instanceStr,  userID, token, count1,count2,count3,count4,count5,count6;
    JSONArray count_7=null, count_71=null, count_72=null, count_73=null;

    double arr7[],arr71[],arr72[];
    String arr73[];
    int lg,bg;
    TextView count0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_options_finance);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        count0=(TextView) findViewById(R.id.count0);

        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");
        lg=getIntent().getIntExtra("lg",0);
        bg=getIntent().getIntExtra("bg",0);

        Toast.makeText(this, "data="+userID+" "+instanceStr+" "+token+" "+lg+" "+bg, Toast.LENGTH_LONG).show();

        initToolbar();

      final   BarChart barChart = (BarChart) findViewById(R.id.barchart1);
        BarChart barChart2 = (BarChart) findViewById(R.id.barchart2);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(DashFinance.this);
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

                    count1=data.getString("count_01");
                    count2=data.getString("count_02");
                    count3=data.getString("count_03");
                    count4=data.getString("count_04");
                    count5=data.getString("count_05");
                    count6=data.getString("count_06");
               //     Toast.makeText(DashFinance.this, "count1=" + count1, Toast.LENGTH_LONG).show();


                    //  count7_length=data.getJSONArray("count_07").length();
                  //  count_7=new int[count7_length];
                    count_7=data.getJSONArray("count_07");
                    arr7=new double[count_7.length()];
                    //for(int i=0;i<count_7.length();i++)
                  //  {
                        count_71=count_7.getJSONArray(0);
                        arr71=new double[count_71.length()];
                    for(int i=0;i<count_71.length();i++)
                    {
                        arr71[i]=count_71.getDouble(i);
                       // Toast.makeText(DashFinance.this, "value="+arr71[i], Toast.LENGTH_SHORT).show();
                    }

                    count_72=count_7.getJSONArray(1);
                    arr72=new double[count_72.length()];
                    for(int i=0;i<count_72.length();i++)
                    {
                        arr72[i]=count_72.getDouble(i);
                      //  Toast.makeText(DashFinance.this, "value="+arr72[i], Toast.LENGTH_SHORT).show();
                    }

                    count_73=count_7.getJSONArray(2);
                   arr73=new String[count_73.length()];
                    for(int i=0;i<count_73.length();i++)
                    {
                        arr73[i]=count_73.getString(i);
                     //   Toast.makeText(DashFinance.this, "value="+arr73[i], Toast.LENGTH_SHORT).show();
                    }

                    ArrayList<BarEntry> graph1=new ArrayList<>();
                    for(int i=0;i<arr71.length;i++) {
                        graph1.add(new BarEntry(i,(float) arr71[i]));
                     //   Toast.makeText(DashFinance.this, "data="+ arr71[i], Toast.LENGTH_SHORT).show();
                    }

                    ArrayList<BarEntry> graph2=new ArrayList<>();
                    for(int i=0;i<arr72.length;i++)
                        graph2.add(new BarEntry(i,(float)arr72[i]));



                    ArrayList<String> labels_graph1 = new ArrayList<String>();
                    for(int i=0;i<arr73.length;i++)
                        labels_graph1.add(arr73[i]);


                    BarDataSet bardataset = new BarDataSet(graph1, "Cells");
                    BarDataSet bardataset2 = new BarDataSet(graph2, "Cells");
                 //   BarData dataBar = new BarData(labels_graph1,bardataset);

                    bardataset.setColors(new int[]{R.color.blue_500});
                    bardataset2.setColors(new int[]{R.color.red_500});

                    ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                    dataSets.add(bardataset);
                    dataSets.add(bardataset2);

                    BarData data1 = new BarData(dataSets);
                    data1.setBarWidth(0.45f);
                    barChart.setData(data1);
                    barChart.groupBars(0.001f, 0.06f, 0.02f); // perform the "explicit" grouping
                   // barChart.invalidate(); // refresh

                 //   barChart.setData(dataBar); // set the data and list of lables into chart

                   barChart.getDescription().setText("Set Bar Chart Description");  // set the description
                    barChart.animateY(5000);

                    YAxis yAxis = barChart.getAxisLeft();
                    yAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return String.valueOf((int) value);
                        }
                    });

                    yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


                    yAxis.setGranularity(1f);
                    yAxis.setGranularityEnabled(true);

                    barChart.getAxisRight().setEnabled(false);

                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setDrawGridLines(true);

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels_graph1));


                 //   }


                  //  for(int i=0;i<arr.length();i++)
               //     int count0=data.getInt(0);
               /*
                        if (super_user == 1) {
                            recent_activity = data.getJSONArray("recentactivity");
                            arr = new String[recent_activity.length()];
                            for (int i = 0; i < recent_activity.length(); i++)
                                arr[i] = recent_activity.getString(i);
                        }

                        */




                } catch (JSONException e)
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
}
