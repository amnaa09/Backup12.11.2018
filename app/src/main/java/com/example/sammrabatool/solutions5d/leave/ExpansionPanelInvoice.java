package com.example.sammrabatool.solutions5d.leave;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.sammrabatool.solutions5d.Activity.LoginCardOverlap;
import com.example.sammrabatool.solutions5d.OTL.CheckIn;
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
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class ExpansionPanelInvoice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
            leavetype[],duration[],status[];

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
        setContentView(R.layout.activity_expansion_panel_invoice);
          progressDialog = new ProgressDialog(this);
    //    progressDialog=new ProgressDialog(this);
        count0 = (TextView) findViewById(R.id.count0);
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
        employtype = (Spinner) findViewById(R.id.spinnerType);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setVisibility(View.GONE);



        //.....................settingspinner........................................//
        listtype.add("Select employee");
        hashSpinnerType.put(0, "0");
        hashSpinnerimage.put(0,"");

        employtype.setOnItemSelectedListener(ExpansionPanelInvoice.this);

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listtype);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        employtype.setAdapter(typeAdapter);
        initComponent();
        //--------------------calling webservice-------------------------------//

        RequestQueue MyRequestQueue = Volley.newRequestQueue(ExpansionPanelInvoice.this);
        String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/getEmployeeList/" + bg + "/" + lg + "/" + userID + "/" + token;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response.toString());
                    if (data.getJSONArray("emp") != null) {
                        otlPorjectArray = data.getJSONArray("emp");
                        //  if(otlPorjectArray!=null) {
                        otlProjectdetail = new JSONObject[otlPorjectArray.length()];
                        for (int i = 0; i < otlPorjectArray.length(); i++) {
                            otlProjectdetail[i] = otlPorjectArray.getJSONObject(i);
                            projecrID = otlProjectdetail[i].getString("id");
                            projectName = otlProjectdetail[i].getString("name");
                            projectfullname = otlProjectdetail[i].getString("fullname");
                            pic = otlProjectdetail[i].getString("pic");
                            listtype.add(projectfullname);
                            hashSpinnerType.put(i + 1, projecrID);
                            hashSpinnerimage.put(i+1,pic);
                            int SDK_INT = Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                //your codes here
                                try {
                                    //   URL url = new URL(data.getString(""));
                                    //    Toast.makeText(ProfilePurple.this, "picccc", Toast.LENGTH_SHORT).show();

                                    Picasso.get().load(otlProjectdetail[i].getString("pic")).transform(new CircleTransform()).into(profileImage);

                                } catch (JSONException error) {
                                    Toast.makeText(ExpansionPanelInvoice.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                            //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                        }
                        //-------setting data to spinner----------------
                        ArrayAdapter projectAdapter = new ArrayAdapter(ExpansionPanelInvoice.this, android.R.layout.simple_spinner_item, listtype);
                        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        employtype.setAdapter(projectAdapter);
                        //--------------------------------------------------------------------------
                    }


                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(ExpansionPanelInvoice.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ExpansionPanelInvoice.this, message, Toast.LENGTH_SHORT).show();
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

        //----------------------spinner methods----------------
        //Performing action onItemSelected and onNothing selected
        @Override
        public void onItemSelected(AdapterView<?> parent, View arg1, int position, final long id) {

            final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);


            final String text=parent.getItemAtPosition(position).toString();
            Toast.makeText(ExpansionPanelInvoice.this, "text=" + text, Toast.LENGTH_SHORT).show();
            if (parent.getId() == R.id.spinnerType && position!=0) {
               // employeeid = hashSpinnerType.get(employtype.getSelectedItemPosition());
                employeeid = hashSpinnerType.get(employtype.getSelectedItemPosition());
                Toast.makeText(ExpansionPanelInvoice.this, "employeeid=" + employeeid, Toast.LENGTH_SHORT).show();
                RequestQueue MyRequestQueue = Volley.newRequestQueue(ExpansionPanelInvoice.this);
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
                                    t1.setText("Annual Leave Entitlement Yearly:" + anuuleavEntit);
                                    t2.setText("Annual Leave:" + annuleav);
                                    t3.setText("Outstanding Annual Leave:" + outannuleav);
                                    t4.setText("Sick Leave:" + sickleav);
                                    t5.setText("Unpaid Leave:" + unpdleav);
                                    empNameText.setText(text);

//                                    pic = leavdetail.getString("pic");

//                                    listtype.add(taskName);
//                                    hashSpinnerType.put(1, taskID);
                                    //Toast.makeText(MainActivity.this, "id="+projecrID+"name="+projectName, Toast.LENGTH_SHORT).show();
                                }
                                if(data.getString("absenseInfo")!="null") {
                                    absenseInform = data.getJSONArray("absenseInfo");//tableview code
                                    getjsonarray = new JSONObject[absenseInform.length()];//tableview code
                                    idd=new String[absenseInform.length()];
                                    leavetype=new String[absenseInform.length()];
                                    duration=new String[absenseInform.length()];
                                    status=new String[absenseInform.length()];
                                    for (int i = 0; i < absenseInform.length(); i++) {
                                        getjsonarray[i] = absenseInform.getJSONObject(i);
                                        idd[i] = getjsonarray[i].getString("a");
                                        leavetype[i] = getjsonarray[i].getString("b");
                                        duration[i] = getjsonarray[i].getString("c");
                                        status[i] = getjsonarray[i].getString("d");
                                    }
                                    populateData(absenseInform.length());

                                 //   final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
                                    tb.setColumnCount(3);
                                    tb.setColumnWeight(0,2);
                                    tb.setColumnWeight(2,1);

                                    tb.setHeaderBackgroundColor(Color.parseColor("#1E88E5"));
                                    //ADAPTERS
                                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(ExpansionPanelInvoice.this, spaceProbeHeaders));
                                    tb.setDataAdapter(new SimpleTableDataAdapter(ExpansionPanelInvoice.this, spaceProbes));
                                    tb.setVisibility(View.VISIBLE);
                                   // tb.addDataClickListener(new TableDataClickListener<String[]>() {
//                                        @Override
//                                        public void onDataClicked(int rowIndex, String[] clickedData) {
//                                            Toast.makeText(ExpansionPanelInvoice.this, ((String[]) clickedData)[1], Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                }
                                else
                                    tb.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ExpansionPanelInvoice.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ExpansionPanelInvoice.this, message, Toast.LENGTH_SHORT).show();
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


                Spaceprobe spaceprobe = new Spaceprobe();
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
        spaceprobeList.add(spaceprobe);

        spaceProbes = new String[size][3];
        // spaceProbes= new String[][]{{}};

        for (int i = 0; i < size; i++) {

         //   Spaceprobe s = spaceprobeList.get(i);

//            spaceProbes[i][0] = s.getId();
            spaceProbes[i][0] = leavetype[i];
            spaceProbes[i][1] = duration[i];
            spaceProbes[i][2] = status[i];
            Toast.makeText(ExpansionPanelInvoice.this, "leavetype" + leavetype, Toast.LENGTH_SHORT).show();

        }

    }




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
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
