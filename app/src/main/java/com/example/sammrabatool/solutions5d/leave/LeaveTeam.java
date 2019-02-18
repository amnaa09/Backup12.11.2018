package com.example.sammrabatool.solutions5d.leave;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.OTL.CircleTransform;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.ViewAnimation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class LeaveTeam extends AppCompatActivity  {
    String[] spaceProbeHeaders = {"Leave Type", "Duration", "Status"};
    String[][] spaceProbes;

    private ImageButton bt_toggle_items, bt_toggle_address, bt_toggle_description;
    private View lyt_expand_items, lyt_expand_address, lyt_expand_description;
    private NestedScrollView nested_scroll_view;
    private ProgressDialog progressDialog ;
    boolean user_valid=false;
    String message;
    JSONObject  otlProjectdetail[];
    JSONObject leavdetail;
    JSONArray otlPorjectArray;
    JSONArray absenseInform;
    JSONObject getjsonarray[];
    String projecrID, projectName,projectfullname,pic,employeeid="0",sickleav,annuleav,unpdleav ,anuuleavEntit,outannuleav,idd[],
            leavetype[],duration[],status[], personId, employee_name;

    TableLayout tableLayout;
    TableRow tableRow;
    private static final String TAG = "ExpansionPanelnvoice";
    ImageView profileImage;

    int lg, bg;
    TextView count0, count00,empNameText;
    String instanceStr, userID, token;
    TextView mDisplayDate;
     DatePickerDialog.OnDateSetListener mDateSetListener;
    Spinner employtype;
    List<String> listtype = new ArrayList<String>();
TextView t1,t2,t3,t4,t5;
    HashMap<Integer,String> hashSpinnerType = new HashMap<Integer, String>();
    HashMap<Integer,String> hashSpinnerimage = new HashMap<Integer, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaveinfo_team);
          progressDialog = new ProgressDialog(this);
    //    progressDialog=new ProgressDialog(this);
     //   count0 = (TextView) findViewById(R.id.count0);
       // count00 = (TextView) findViewById(R.id.count00);
        t1=(TextView)findViewById(R.id.anualleaveentitle);
        t2=(TextView)findViewById(R.id.anualleave);
        t3=(TextView)findViewById(R.id.outanuleave);
        t4=(TextView)findViewById(R.id.sickleave);
        t5=(TextView)findViewById(R.id.upadleave);
        empNameText=(TextView) findViewById(R.id.empName);
        userID = getIntent().getStringExtra("userID");
        instanceStr = getIntent().getStringExtra("instance");
        token = getIntent().getStringExtra("token");
        lg = getIntent().getIntExtra("lg", 0);
        bg = getIntent().getIntExtra("bg", 0);
        personId=getIntent().getStringExtra("personId");
        employee_name=getIntent().getStringExtra("employee_name");
        pic=getIntent().getStringExtra("pic");
        profileImage = (ImageView) findViewById(R.id.profileImage);

        if(pic!=null || pic!="")
        {
            int SDK_INT = Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here

                Picasso.get().load(pic).transform(new CircleTransform()).into(profileImage);


            }
        }

        tableLayout=(TableLayout)findViewById(R.id.tabinfo2);
        tableLayout.setVisibility(View.GONE);
        initComponent();
        initToolbar();


        RequestQueue MyRequestQueue = Volley.newRequestQueue(LeaveTeam.this);
        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getEmployeeLeaveDetail/" + bg + "/" + lg + "/" + userID + "/" + token + "/" + personId;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject data = new JSONObject(response.toString());
                    //  listtype.clear();
                    //  hashSpinnerType.clear();
                    //listtype.add("Select Employee");
                    // hashSpinnerType.put(0, "0");

                    if (data.getString("leaveInfo") != null) {

                        leavdetail = data.getJSONObject("leaveInfo");
                        if (leavdetail != null) {
//                                otlTaskdetail = new JSONObject[otlPorjectArray.length()];

                            sickleav = leavdetail.getString("sick_leave");
                            annuleav = leavdetail.getString("annual_leave");
                            unpdleav = leavdetail.getString("unpaid_leave");
                            anuuleavEntit = leavdetail.getString("annualLeavesEntitled");
                            outannuleav = leavdetail.getString("outstantingAnuualLeave");
                            String temp="Annual Leave Entitlement Yearly: "+"<b>" + anuuleavEntit + "</b>";
                            t1.setText(Html.fromHtml(temp));
                            temp="Annual Leave: "+"<b>" + annuleav + "</b>";
                            t2.setText(Html.fromHtml(temp));
                            temp="Outstanding: "+"<b>" + outannuleav + "</b>";
                            t3.setText(Html.fromHtml(temp));

                            temp="Sick Leave: "+"<b>" + sickleav + "</b>";
                            t4.setText(Html.fromHtml(temp));
                            temp="Unpaid Leave: "+"<b>" + unpdleav + "</b>";
                            t5.setText(Html.fromHtml(temp));


                            empNameText.setText(employee_name);

//                                    pic = leavdetail.getString("pic");

//                                    listtype.add(taskName);
//                                    hashSpinnerType.put(1, taskID);
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }
                        if(!data.get("absenseInfo").equals(null)) {
                            //   Toast.makeText(ExpansionPanelInvoice.this, "in if", Toast.LENGTH_SHORT).show();
                            absenseInform = data.getJSONArray("absenseInfo");
                            getjsonarray = new JSONObject[absenseInform.length()];
                            idd=new String[absenseInform.length()];
                            leavetype=new String[absenseInform.length()];
                            duration=new String[absenseInform.length()];
                            status=new String[absenseInform.length()];
                            for (int i = 0; i < absenseInform.length(); i++) {
                                getjsonarray[i] = absenseInform.getJSONObject(i);
                                idd[i] = getjsonarray[i].getString("a");
                                leavetype[i] = getjsonarray[i].getString("b");
                                //   Toast.makeText(ExpansionPanelInvoice.this, "lt="+leavetype[i], Toast.LENGTH_SHORT).show();
                                duration[i] = getjsonarray[i].getString("c");
                                status[i] = getjsonarray[i].getString("d");
                            }
                            // populateData(absenseInform.length());
                            tableLayout.removeAllViews();
                            tableLayout.setStretchAllColumns(TRUE);
                            //    tableLayout.setBackgroundResource(R.drawable.table_background);
                            //    tableLayout.setBackgroundColor(Color.BLACK);
                           /* TableLayout.LayoutParams tableRowParams =
                                    new TableLayout.LayoutParams
                                            (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
*/
                            TableRow tableRowHeading = new TableRow(LeaveTeam.this);
                            tableRowHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                            tableRowHeading.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));



                            TextView th = new TextView(LeaveTeam.this);
                            TextView th1 = new TextView(LeaveTeam.this);
                            TextView th2 = new TextView(LeaveTeam.this);
                            th.setPadding(10, 10, 10, 10);
                            th.setAllCaps(FALSE);
                            th1.setAllCaps(FALSE);
                            th.setGravity(Gravity.LEFT);
                            th.setText("Leave Type");
                            th1.setPadding(10, 10, 10, 10);

                            th1.setGravity(Gravity.LEFT);
                            th1.setText("Duration");

                            th2.setPadding(10, 10, 10, 10);
                            th2.setAllCaps(FALSE);
                            th2.setGravity(Gravity.LEFT);
                            th2.setAllCaps(FALSE);
                            th2.setText("Status");
                            th.setTextColor(Color.WHITE);
                            th1.setTextColor(Color.WHITE);
                            th2.setTextColor(Color.WHITE);

                            tableRowHeading.addView(th);
                            tableRowHeading.addView(th1);
                            tableRowHeading.addView(th2);

                            tableLayout.addView(tableRowHeading);
                            View v = new View(LeaveTeam.this);
                            v.setLayoutParams(new TableRow.LayoutParams(
                                    TableRow.LayoutParams.MATCH_PARENT, 1));
                            v.setBackgroundColor(Color.parseColor("#1976D2"));

                            tableLayout.addView(v);
                            for (int i = 0; i < absenseInform.length(); i++) {
                                getjsonarray[i] = absenseInform.getJSONObject(i);
                                // idd = getjsonarray.getString("a");
                                TableRow tableRow = new TableRow(LeaveTeam.this);
                                tableRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
                                tableRow.setBackgroundColor(Color.WHITE);
                                //      tableRowParams.setMargins(1,1,1,1);
                                //tableRow.LayoutParams=tableRowParams;
                                //    tableRow.setLayoutParams(tableRowParams);
                                TextView tv = new TextView(LeaveTeam.this);
                                TextView tv1 = new TextView(LeaveTeam.this);
                                TextView tv2 = new TextView(LeaveTeam.this);
                                tv.setPadding(10, 10, 10, 10);

                                tv.setGravity(Gravity.LEFT);
                                tv.setText(leavetype[i]);
                                tv1.setPadding(10, 10, 10, 10);

                                tv1.setGravity(Gravity.LEFT);
                                tv1.setText(duration[i]);

                                tv2.setPadding(10, 10, 10, 10);

                                tv2.setGravity(Gravity.LEFT);
                                tv2.setText(status[i]);

                                tv.setTextColor(Color.BLACK);
                                tv1.setTextColor(Color.BLACK);
                                tv2.setTextColor(Color.BLACK);



//                                        tv1.setGravity(Gravity.LEFT);
//                                        tv1.setText(duration[i]);

                                tableRow.addView(tv);
                                tableRow.addView(tv1);
                                tableRow.addView(tv2);
                                //      Toast.makeText(LeaveHr.this, "creating table", Toast.LENGTH_SHORT).show();
                                tableLayout.addView(tableRow);
                                View v1 = new View(LeaveTeam.this);
                                v1.setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.MATCH_PARENT, 1));
                                v1.setBackgroundColor(Color.rgb(51, 51, 51));
                                tableLayout.addView(v1);
                            }
                            tableLayout.setVisibility(View.VISIBLE);
                        }
                        else {
                            tableLayout.setVisibility(View.GONE);
                            //     Toast.makeText(ExpansionPanelInvoice.this, "in else", Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(LeaveTeam.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LeaveTeam.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.hide();
            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Reminder");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        com.example.sammrabatool.solutions5d.utils.Tools.setSystemBarColor(this);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  Toast.makeText(DashboardGridFab.this, "click", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(ExpansionPanelInvoice.this, LoginCardOverlap.class);
//                intent.putExtra("userID",userID);
//                intent.putExtra("token",token);
//                intent.putExtra("instance", instanceStr);
//                startActivity(intent);
//            }
//        });
    }

    //----------------------spinner methods----------------
        //Performing action onItemSelected and onNothing selected
   /*     @Override
        public void onItemSelected(AdapterView<?> parent, View arg1, int position, final long id) {

            final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);


            final String text=parent.getItemAtPosition(position).toString();
       //     Toast.makeText(ExpansionPanelInvoice.this, "text=" + text, Toast.LENGTH_SHORT).show();
            if (parent.getId() == R.id.spinnerType && position!=0) {
               // employeeid = hashSpinnerType.get(employtype.getSelectedItemPosition());
                employeeid = hashSpinnerType.get(employtype.getSelectedItemPosition());
            //    Toast.makeText(ExpansionPanelInvoice.this, "employeeid=" + employeeid, Toast.LENGTH_SHORT).show();
                RequestQueue MyRequestQueue = Volley.newRequestQueue(LeaveTeam.this);
                String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getEmployeeLeaveDetail/" + bg + "/" + lg + "/" + userID + "/" + token + "/" + employeeid;
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject data = new JSONObject(response.toString());
                          //  listtype.clear();
                          //  hashSpinnerType.clear();
                            //listtype.add("Select Employee");
                           // hashSpinnerType.put(0, "0");

                            if (data.getString("leaveInfo") != null) {

                                leavdetail = data.getJSONObject("leaveInfo");
                                if (leavdetail != null) {
//                                otlTaskdetail = new JSONObject[otlPorjectArray.length()];

                                    sickleav = leavdetail.getString("sick_leave");
                                    annuleav = leavdetail.getString("annual_leave");
                                    unpdleav = leavdetail.getString("unpaid_leave");
                                    anuuleavEntit = leavdetail.getString("annualLeavesEntitled");
                                    outannuleav = leavdetail.getString("outstantingAnuualLeave");
                                    String temp="Annual Leave Entitlement Yearly: "+"<b>" + anuuleavEntit + "</b>";
                                    t1.setText(Html.fromHtml(temp));
                                    temp="Annual Leave: "+"<b>" + annuleav + "</b>";
                                    t2.setText(Html.fromHtml(temp));
                                    temp="Outstanding: "+"<b>" + outannuleav + "</b>";
                                    t3.setText(Html.fromHtml(temp));

                                    temp="Sick Leave: "+"<b>" + sickleav + "</b>";
                                    t4.setText(Html.fromHtml(temp));
                                    temp="Unpaid Leave: "+"<b>" + unpdleav + "</b>";
                                    t5.setText(Html.fromHtml(temp));


                                    empNameText.setText(text);

//                                    pic = leavdetail.getString("pic");

//                                    listtype.add(taskName);
//                                    hashSpinnerType.put(1, taskID);
                                    //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                                }
                                if(!data.get("absenseInfo").equals(null)) {
                                 //   Toast.makeText(ExpansionPanelInvoice.this, "in if", Toast.LENGTH_SHORT).show();
                                    absenseInform = data.getJSONArray("absenseInfo");
                                    getjsonarray = new JSONObject[absenseInform.length()];
                                    idd=new String[absenseInform.length()];
                                    leavetype=new String[absenseInform.length()];
                                    duration=new String[absenseInform.length()];
                                    status=new String[absenseInform.length()];
                                    for (int i = 0; i < absenseInform.length(); i++) {
                                        getjsonarray[i] = absenseInform.getJSONObject(i);
                                        idd[i] = getjsonarray[i].getString("a");
                                        leavetype[i] = getjsonarray[i].getString("b");
                                     //   Toast.makeText(ExpansionPanelInvoice.this, "lt="+leavetype[i], Toast.LENGTH_SHORT).show();
                                        duration[i] = getjsonarray[i].getString("c");
                                        status[i] = getjsonarray[i].getString("d");
                                    }
                                    populateData(absenseInform.length());

                                 //   final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
                                    tb.setColumnCount(3);
                                    tb.setColumnWeight(0,2);
                                    tb.setColumnWeight(2,1);

                                  //  tb.setHeaderBackgroundColor(Color.parseColor("#FFFFFF"));

                                    //ADAPTERS
                                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(LeaveTeam.this, spaceProbeHeaders));
                                    tb.setDataAdapter(new SimpleTableDataAdapter(LeaveTeam.this, spaceProbes));
                                    tb.setVisibility(View.VISIBLE);
                                   // tb.addDataClickListener(new TableDataClickListener<String[]>() {
//                                        @Override
//                                        public void onDataClicked(int rowIndex, String[] clickedData) {
//                                            Toast.makeText(ExpansionPanelInvoice.this, ((String[]) clickedData)[1], Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                }
                                else {
                                    tb.setVisibility(View.GONE);
                               //     Toast.makeText(ExpansionPanelInvoice.this, "in else", Toast.LENGTH_SHORT).show();

                                }
                            }
                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(LeaveTeam.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LeaveTeam.this, message, Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.hide();
                    }
                });
                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                //    progressDialog.show();
            }
//................................................................................................................//

//        mDisplayDate = (TextView) findViewById(R.id.tvDate);
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        ExpansionPanelInvoice.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//            }
//        });
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                mDisplayDate.setText(date);
//                //showCustomDialog();
//            }
//        };

       // Button btn=(Button)findViewById(R.id.APPLY);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//           @SuppressLint("WrongViewCast")
//            private void showCustomDialog() {
//                final Calendar myCalendar = Calendar.getInstance();
//
//                final TextView edittext= (TextView) findViewById(R.id.tvDate);
//                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                          int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.MONTH, monthOfYear);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        updateLabel();
//                    }
//                    private void updateLabel() {
//
//                        String myFormat = "MM/dd/yy"; //In which you need put here
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                        edittext.setText(sdf.format(myCalendar.getTime()));
//                    }
//
//                };
//

//                    final Dialog dialog = new Dialog(ExpansionPanelInvoice.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//                dialog.setContentView(R.layout.leavepopup);
//                dialog.setCancelable(true);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                //  final EditText et_post = (EditText) dialog.findViewById(R.id.et_post);

//                ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });



//                dialog.show();
//                dialog.getWindow().setAttributes(lp);
//            }
    }
    private void populateData(int size) {


*//*        Spaceprobe spaceprobe = new Spaceprobe();
        ArrayList<Spaceprobe> spaceprobeList = new ArrayList<>();

//        spaceprobe.setId("1");
//        spaceprobe.setName("Pioneer");
//        spaceprobe.setPropellant("Solar");
//        spaceprobe.setDestination("Venus");
//        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("2");
        spaceprobe.setName("Casini");
        spaceprobe.setPropellant("Nuclear");
        spaceprobe.setDestination("Jupiter");
        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("3");
        spaceprobe.setName("Apollo");
        spaceprobe.setPropellant("Chemical");
        spaceprobe.setDestination("Moon");
        spaceprobeList.add(spaceprobe);

        spaceprobe = new Spaceprobe();
//        spaceprobe.setId("4");
        spaceprobe.setName("Enterpise");
        spaceprobe.setPropellant("Anti-Matter");
        spaceprobe.setDestination("Andromeda");
        spaceprobeList.add(spaceprobe);*//*


        spaceProbes = new String[size*3][3];
        // spaceProbes= new String[][]{{}};
     //   Toast.makeText(this, "size="+size, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < size*3; i++) {

         //   Spaceprobe s = spaceprobeList.get(i);

//            spaceProbes[i][0] = s.getId();
            spaceProbes[i][0] = leavetype[0];
            spaceProbes[i][1] = duration[0];
            spaceProbes[i][2] = status[0];

      //      Toast.makeText(ExpansionPanelInvoice.this, "leavetype" + spaceProbes[i][0], Toast.LENGTH_SHORT).show();

        }

    }*/




    private void initComponent() {

        // nested scrollview
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        // section items
//        bt_toggle_items = (ImageButton) findViewById(R.id.bt_toggle_items);
        lyt_expand_items = (View) findViewById(R.id.lyt_expand_items);
//        bt_toggle_items.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSection(view, lyt_expand_items);
//            }
//        });
        }


    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
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
            Intent intent = new Intent(LeaveTeam.this, LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



  /*  @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
