package com.example.sammrabatool.solutions5d.Reminder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.sammrabatool.solutions5d.DashboardOptions.DashSale;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.dashboard.DashboardGridFab;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.widget.LineItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recyclerview extends AppCompatActivity {

    private RecyclerView recyclerView;
    //  public static ArrayList<Model> modelArrayList;
    private CustomAdapter mcustomAdapter;
    private Button btnnext;
    String reminder, notification_id, messg, c, status, date, f, fy, h, k, l, m, n, text;
    //    private String[] reminder = new String[]{"FYR", "FYR", "FYR", "FYR", "FYI", "FYI", "FYR", "FYI", "FYI"};
    private ProgressDialog progressDialog;
    boolean user_valid = false;
    String message;
    String instanceStr, userID, token, pic;
    int lg, bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        btnnext = (Button) findViewById(R.id.next);
        initToolbar();
        initComponent();
        final ArrayList<Model> list = new ArrayList<>();


        RequestQueue MyRequestQueue = Volley.newRequestQueue(Recyclerview.this);

        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getReminders/" + bg + "/" + lg + "/" + userID + "/" + token;
        final ProgressDialog progressDialog = new ProgressDialog(Recyclerview.this);

        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject data = null;
                try {
                    data = new JSONObject(response.toString());
                    reminder = data.getString("reminder");
                    JSONArray not_data = new JSONArray(reminder);
                    for (int i = 0; i < not_data.length(); i++) {
                        Model obj = new Model();
                        JSONObject not_obj = (JSONObject) not_data.get(i);
                        obj.setMessage(not_obj.getString("b"));
                        obj.setNotifcation_id(not_obj.getString("a"));
                        obj.setStatus(not_obj.getString("d"));
                        obj.setDate(not_obj.getString("e"));
                        obj.setFyr(not_obj.getString("g"));
                        obj.setName(not_obj.getString("h"));
                        pic=not_obj.getString("pic");
                        obj.image=pic;
                        list.add(obj);
                    }
                    mcustomAdapter = new CustomAdapter(Recyclerview.this, list);
                    recyclerView.setAdapter(mcustomAdapter);
                    mcustomAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.hide();


                } catch (JSONException e1) {
                    e1.printStackTrace();
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    Toast.makeText(Recyclerview.this, "Error:" + e1.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Recyclerview.this, message, Toast.LENGTH_SHORT).show();

            }
        }
        );
        MyStringRequest.setShouldCache(true);
        MyRequestQueue.add(MyStringRequest);

//        modelArrayList = getModel();
        //    mcustomAdapter = new CustomAdapter(this, modelArrayList);
        //   recyclerView.setAdapter(mcustomAdapter);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Reminder");
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
            Intent intent = new Intent(Recyclerview.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

        private void initComponent () {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            // recyclerView.setDrawingCacheEnabled(true);
            LinearLayoutManager llm = new LinearLayoutManager(Recyclerview.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            //  getNotificationData(ListMultiSelection.this);


            // actionModeCallback = new ActionModeCallback();

        }
    }


////        modelArrayList = getModel();
//        mcustomAdapter = new CustomAdapter(this, modelArrayList);
//        recyclerView.setAdapter(mcustomAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//
//    private ArrayList<Model> getModel() {
//        ArrayList<Model> list = new ArrayList<>();
//        for(int i = 0; i < 9; i++){
//
//            Model model = new Model();
//            model.setNotifcation_id();
//            model.setName(reminder[i]);
//            model.setFyr(reminder[i]);
//            model.setDate(reminder[i]);
//            model.setStatus(reminder[i]);
//            model.setMessage(reminder[i]);
////            model.setImage();
//            list.add(model);
//        }
//        return list



