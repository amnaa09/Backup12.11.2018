package com.example.sammrabatool.solutions5d.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
import com.example.sammrabatool.solutions5d.DashboardOptions.DashboardOptions;
import com.example.sammrabatool.solutions5d.OTL.CheckIn;
import com.example.sammrabatool.solutions5d.OTL.MainActivityOut;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Reminder.CustomAdapter;
import com.example.sammrabatool.solutions5d.Reminder.Model;
import com.example.sammrabatool.solutions5d.Reminder.Recyclerview;
import com.example.sammrabatool.solutions5d.leave.ExpansionPanelInvoice;
import com.example.sammrabatool.solutions5d.list.AdapterListInbox;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.example.sammrabatool.solutions5d.profile.ProfileFabMenu;
import com.example.sammrabatool.solutions5d.profile.ProfilePurple;
import com.example.sammrabatool.solutions5d.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageButton;


public class DashboardGridFab extends AppCompatActivity {
    private CustomAdapter mcustomAdapter;
    private RecyclerView recyclerView;
    GifImageButton btnbell;
    FloatingActionButton profile, otl, fyi, fyr, request, dash,leave;
    String   instanceStr, message, userID, token, details, image, name="Unknown";
    LinearLayout dashboard, recent;
    int super_user,lg,bg;
    String recent_activity[];
    String reminder,notification_id,messg,c,status,date,f,fy,h,k,l,m,n,text;
    int lenght;
    SharedPreferences checkin_preferences;
    TextView lpo,grn,supplier,payment,receipt,customer,requisition;
    String emp_name, emp_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_grid_fab);
        profile = (FloatingActionButton ) findViewById(R.id.profile_button);
        otl = (FloatingActionButton ) findViewById(R.id.otl_button);
        fyi = (FloatingActionButton ) findViewById(R.id.fyi_button);
        fyr = (FloatingActionButton ) findViewById(R.id.fyr_button);
        request = (FloatingActionButton ) findViewById(R.id.request_button);
        dash=(FloatingActionButton) findViewById(R.id.dashboard);
        leave=(FloatingActionButton)findViewById(R.id.leavedetail);
       // dashboard=(LinearLayout) findViewById(R.id.linear_dash);
        recent=(LinearLayout) findViewById(R.id.recent);
        lpo=(TextView)findViewById(R.id.lpo);
        grn=(TextView)findViewById(R.id.grn);
        supplier=(TextView)findViewById(R.id.supplier);
        payment=(TextView)findViewById(R.id.payment);
        receipt=(TextView)findViewById(R.id.receipt);
        customer=(TextView)findViewById(R.id.customer);
        requisition=(TextView)findViewById(R.id.requisition);
        btnbell=(GifImageButton)findViewById(R.id.APPLY);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        SharedPreferences sharedprefSignup = getSharedPreferences("SignupPref", Context.MODE_PRIVATE);
        checkin_preferences = getSharedPreferences("checkin_preferences", Context.MODE_PRIVATE);

        userID=sharedprefSignup.getString("userID", "save user id");//getIntent().getStringExtra("userID");
        instanceStr=sharedprefSignup.getString("instance", "save user id");//getIntent().getStringExtra("instance");

         name=sharedprefSignup.getString("emailKey", "save user id");
        token=sharedprefSignup.getString("token", "save user id");

  //      Toast.makeText(this, "before:"+ userID, Toast.LENGTH_SHORT).show();
        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        token=getIntent().getStringExtra("token");
        name=getIntent().getStringExtra("name");

        //recent_activity[]

        super_user=getIntent().getIntExtra("super_user",0);
        lg=getIntent().getIntExtra("lg",0);
        bg=getIntent().getIntExtra("bg",0);

        SharedPreferences.Editor editor = sharedprefSignup.edit();
        editor.putInt("LG", lg);
        editor.putInt("BG",bg);
        editor.commit();



     //   super_user=0;
      //  Toast.makeText(this, "length="+lenght, Toast.LENGTH_SHORT).show();
        if(super_user==1) {
            lenght = getIntent().getIntExtra("length", 0);
            recent_activity = new String[lenght];
            recent_activity = getIntent().getStringArrayExtra("recent_activity");
        //    Toast.makeText(this, "activity=" + recent_activity[0] + " " + recent_activity[1], Toast.LENGTH_SHORT).show();
            lpo.setText(recent_activity[0]);
            grn.setText(recent_activity[1]);
            supplier.setText(recent_activity[2]);
            payment.setText(recent_activity[3]);
            receipt.setText(recent_activity[4]);
            customer.setText(recent_activity[5]);
            requisition.setText(recent_activity[6]);
        }
        else {
            recent.setVisibility(LinearLayout.GONE);
            dashboard.setVisibility(LinearLayout.GONE);

        }


    //    Toast.makeText(this, "after:"+ name+"token="+token, Toast.LENGTH_SHORT).show();
        initToolbar();

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardGridFab.this, ExpansionPanelInvoice.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("lg",lg);
                intent.putExtra("bg",bg);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DashboardGridFab.this, ProfileFabMenu.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("lg",lg);
                intent.putExtra("name",name);
                intent.putExtra("bg",bg);
                startActivity(intent);

            }
        });

        otl.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                emp_name=checkin_preferences.getString("EmpName", "");
                emp_pic=checkin_preferences.getString("EmpPicture", "");
                if(checkin_preferences.getInt("Checkin", 0)==1)
                {
                    Intent intent=new Intent(DashboardGridFab.this, MainActivityOut.class);
                    intent.putExtra("userID",userID);
                    intent.putExtra("token",token);
                    intent.putExtra("instance", instanceStr);
                    intent.putExtra("lg",lg);
                    intent.putExtra("bg",bg);
                    intent.putExtra("empName", emp_name);
                    intent.putExtra("empPic", emp_pic);
                    startActivity(intent);
                //    finish();
                }
                else {
                    Intent intent = new Intent(DashboardGridFab.this, CheckIn.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);
                    intent.putExtra("instance", instanceStr);
                    intent.putExtra("lg", lg);
                    intent.putExtra("bg", bg);
                    startActivity(intent);
                 //   finish();
                }


            }
        });

        fyi.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardGridFab.this, ListMultiSelection.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("type", "1");
                startActivity(intent);


            }
        });

        fyr.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DashboardGridFab.this, ListMultiSelection.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("type", "2");
                startActivity(intent);


            }
        });

        request.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {




            }
        });

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardGridFab.this, DashboardOptions.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("lg",lg);
                intent.putExtra("bg",bg);

                startActivity(intent);

            }
        });
        btnbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardGridFab.this, Recyclerview.class);
                intent.putExtra("userID", userID);
                intent.putExtra("token", token);
                intent.putExtra("instance", instanceStr);
                intent.putExtra("lg", lg);
                intent.putExtra("bg", bg);
                startActivity(intent);
//                final ArrayList<Model> list = new ArrayList<>();
//
//
//                RequestQueue MyRequestQueue = Volley.newRequestQueue(DashboardGridFab.this);
//
//                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/getReminders/"+bg+"/"+lg+"/"+userID+"/"+token;
//                final ProgressDialog progressDialog = new ProgressDialog(DashboardGridFab.this);
//
//                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JSONObject data = null;
//                        try {
//                            data = new JSONObject(response.toString());
//                            reminder = data.getString("reminder");
//                            JSONArray not_data = new JSONArray(reminder);
//                            for (int i = 0; i < not_data.length(); i++) {
//                                Model obj = new Model();
//                                JSONObject not_obj = (JSONObject) not_data.get(i);
//                                obj.setMessage(not_obj.getString("b"));
//                                obj.setNotifcation_id(not_obj.getString("a"));
//                                obj.setStatus(not_obj.getString("d"));
//                                obj.setDate(not_obj.getString("e"));
//                                obj.setFyr(not_obj.getString("g"));
//                                obj.setName(not_obj.getString("h"));
//                                list.add(obj);
//                            }
//                           mcustomAdapter = new CustomAdapter(DashboardGridFab.this, list);
//                            recyclerView.setAdapter(mcustomAdapter);
//                            mcustomAdapter.notifyDataSetChanged();
//
//
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                            Toast.makeText(DashboardGridFab.this, "Error:" + e1.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        String message = null;
//                        if (error instanceof NetworkError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof ServerError) {
//                            message = "The server could not be found. Please try again after some time!!";
//                        } else if (error instanceof AuthFailureError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof ParseError) {
//                            message = "Parsing error! Please try again after some time!!";
//                        } else if (error instanceof NoConnectionError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof TimeoutError) {
//                            message = "Connection TimeOut! Please check your internet connection.";
//                        }
//                        Toast.makeText(DashboardGridFab.this, message, Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                );
//                MyStringRequest.setShouldCache(true);
//                MyRequestQueue.add(MyStringRequest);
//
//
//                progressDialog.setCancelable(false);
//                progressDialog.setTitle("Loading...");
//                progressDialog.setMessage("Please wait");
//                progressDialog.show();
//            }
//        });


            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

        getSupportActionBar().setTitle("Hi,"+name);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(DashboardGridFab.this, "click", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashboardGridFab.this, ProfilePurple.class);
                intent.putExtra("userID",userID);
                intent.putExtra("token",token);
                intent.putExtra("instance", instanceStr);
                startActivity(intent);
            }
        });
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
        } else if (id == R.id.logout){
            Toast.makeText(this,"You have successfully logged out",Toast.LENGTH_LONG).show();
            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent=new Intent(DashboardGridFab.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
