package com.example.sammrabatool.solutions5d.list;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.sammrabatool.solutions5d.OTL.CircleTransform;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.widget.LineItemDecoration;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListMultiSelection extends AppCompatActivity  {

    private View parent_view, list_view;
    public ImageView image;
    private RecyclerView recyclerView;
    private AdapterListInbox mAdapter;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Toolbar toolbar;
    static String userID,userName,instanceStr,message, type;
    static String notification,notification_count,notification_id,message_type,message_name,status,message_subject,date,touser,pic,notifications;
    static String userToken;
    String messagename, tousername,subject,remarks,Status,begindate,workFlowID,messagehtml;
    private static boolean user_valid=false;
    LinearLayout list;
    int redirection;
    int message_type_int;
    int message_url_flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        parent_view = findViewById(R.id.lyt_parent);
        list=(LinearLayout) findViewById(R.id.Linear_list);
        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        userToken=getIntent().getStringExtra("token");
        type=getIntent().getStringExtra("type");

        initToolbar(type);
        initComponent();
     //   Toast.makeText(this, "Long press for multi selection", Toast.LENGTH_SHORT).show();
    }

    private void initToolbar(String type) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
      //  Toast.makeText(this, "type="+type, Toast.LENGTH_SHORT).show();
        if(type.equals("1"))
            getSupportActionBar().setTitle("For Your Information");
        else
            getSupportActionBar().setTitle("For Your Response");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);
       // recyclerView.setDrawingCacheEnabled(true);
        LinearLayoutManager llm = new LinearLayoutManager(ListMultiSelection.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

       getNotificationData(ListMultiSelection.this);




       // actionModeCallback = new ActionModeCallback();

    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Tools.setSystemBarColor(ListMultiSelection.this, R.color.blue_grey_700);
            mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                deleteInboxes();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            actionMode = null;
            Tools.setSystemBarColor(ListMultiSelection.this, R.color.colorPrimary);
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

           // showCustomDialog();
        }
        return super.onOptionsItemSelected(item); */
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
            Intent intent=new Intent(ListMultiSelection.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getNotificationData(final Context ctx) {

        //  list_view=findViewById(R.id.lyt_list);
        //View itemView = LayoutInflater.from(list_view.getContext()).inflate(R.layout.item_inbox, (ViewGroup) list_view, false);
        final LayoutInflater factory = getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.item_inbox, null);

        image = (ImageView) textEntryView.findViewById(R.id.list_image);


        // image = (ImageView) itemView.findViewById(R.id.list_image);

        final   List<Notification> items = new ArrayList<>();


        RequestQueue MyRequestQueue = Volley.newRequestQueue(ctx);
        //  JSONObject response;
        //   RequestFuture<JSONObject> future = RequestFuture.newFuture();

        String url = "http://"+instanceStr+".5dsurf.com/app/webservice/getusernotifications/"+userID+"/"+userToken+"/"+type;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(ListMultiSelection.this, "in func", Toast.LENGTH_SHORT).show();
                JSONObject data = null;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                 //   Toast.makeText(ListMultiSelection.this, response, Toast.LENGTH_SHORT).show();
                    data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if(user_valid==true) {

                        notification=data.getString("notifications");
                        JSONArray not_data=new JSONArray(notification);
                        for(int i =0; i<not_data.length(); i++) {
                            Notification obj = new Notification();
                            JSONObject not_obj= (JSONObject)not_data.get(i);

                            notification_count = not_obj.getString("notification_count");
                            notification_id = not_obj.getString("notification_id");
                            obj.id=notification_id;
                            message_type = not_obj.getString("message_type");
                            if(not_obj.getString("message_name").trim().equals(""))
                            {
                                obj.message_name="No name";
                            }
                            else {
                                message_name = not_obj.getString("message_name");
                                obj.message_name = message_name;
                            }
                            status=not_obj.getString("status");
                            if(not_obj.getString("message_subject").trim().equals(""))
                            {
                                obj.message_subject="No subject";
                            }
                            else
                                {
                                message_subject = not_obj.getString("message_subject");
                                obj.message_subject = message_subject;
                            }
                            date=not_obj.getString("date");
                            obj.date=date;
                            if (not_obj.getString("touser").trim().equals(""))
                            {
                                obj.touser="Unknown";
                            }
                            else
                            {
                                touser=not_obj.getString("touser");
                                obj.touser=touser;
                            }
                           touser=not_obj.getString("touser");

                            pic=not_obj.getString("pic");
                            obj.imagelist=pic;
                            items.add(obj);

                            }
                        mAdapter = new AdapterListInbox(ListMultiSelection.this, items);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                            //   ViewCompat.setNestedScrollingEnabled(recyclerView, false);
                        if ( progressDialog.isShowing())
                            progressDialog.hide();
                            mAdapter.setOnClickListener(new AdapterListInbox.OnClickListener() {
                                @Override
                                public void onItemClick(View view, Notification obj, int pos) {
                                    if (mAdapter.getSelectedItemCount() > 0) {
                                        enableActionMode(pos);
                                    } else {
                                        // read the inbox which removes bold from the row
                                        Notification objNot = mAdapter.getItem(pos);
                                        //   Toast.makeText(getApplicationContext(), "Read: " + objNot.message_subject, Toast.LENGTH_SHORT).show();
                                        showCustomDialog(objNot.id,message_type, ctx,items,pos);
                                    }
                                }



                            });
                     //       Toast.makeText(ctx, "Download completed successfully", Toast.LENGTH_SHORT).show();
                   //     }


                    }

                    else
                    {
                        message = data.getString("message");
                        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(ctx, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //                            // tx.setText( "Error: " + e.getMessage());

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
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();

            }
        });
        MyStringRequest.setShouldCache(true);
        MyRequestQueue.add(MyStringRequest);


        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
/*
        MyRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {
                if ( progressDialog.isShowing()) {
                    progressDialog.hide();
                    mAdapter = new AdapterListInbox(ListMultiSelection.this, items);
                    LinearLayoutManager llm = new LinearLayoutManager(ListMultiSelection.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(mAdapter);
                    //   ViewCompat.setNestedScrollingEnabled(recyclerView, false);
                    mAdapter.setOnClickListener(new AdapterListInbox.OnClickListener() {
                        @Override
                        public void onItemClick(View view, Notification obj, int pos) {
                            if (mAdapter.getSelectedItemCount() > 0) {
                                enableActionMode(pos);
                            } else {
                                // read the inbox which removes bold from the row
                                Notification objNot = mAdapter.getItem(pos);
                                //   Toast.makeText(getApplicationContext(), "Read: " + objNot.message_subject, Toast.LENGTH_SHORT).show();
                                showCustomDialog(notification_id,message_type, ctx);
                            }
                        }



                    });
                    Toast.makeText(ctx, "Download completed successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }

    private void showCustomDialog(final String notification_id, final String message_type, Context ctx, final List<Notification> items,final int pos)
    {

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.dialog_notification_detail);
        dialog.setCancelable(true);

        final  TextView not_from_user=(TextView) dialog.findViewById(R.id.not_from_user);
        final  TextView not_message_name=(TextView) dialog.findViewById(R.id.not_message_name);
        final  TextView not_subject=(TextView) dialog.findViewById(R.id.not_subject);
        final   TextView not_status=(TextView) dialog.findViewById(R.id.not_status);
        final   TextView not_begin_date=(TextView) dialog.findViewById(R.id.not_begin_date);
        final   EditText not_remarks=(EditText) dialog.findViewById(R.id.not_remarks);
        final ImageButton close=(ImageButton) dialog.findViewById(R.id.bt_Close);
        final ImageView pic=(ImageView) dialog.findViewById(R.id.listpic);

        not_remarks.setVisibility(View.VISIBLE);


         String remarks="";

        if(message_type.equals("FYI")) {
            message_type_int = 1;
            message_url_flag=4;
        }
        else {
            message_type_int = 2;
            message_url_flag=3;
        }

       final RequestQueue MyRequestQueue1 = Volley.newRequestQueue(ListMultiSelection.this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if(message_type.equals("FYI"))
        {
            ((AppCompatButton) dialog.findViewById(R.id.bt_approve)).setVisibility(View.INVISIBLE);
            ((AppCompatButton) dialog.findViewById(R.id.bt_reject)).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.not_remarks).setVisibility(View.INVISIBLE);
            ((Button) dialog.findViewById(R.id.bt_cancel)).setGravity(View.FOCUS_RIGHT);
            ((AppCompatButton)dialog.findViewById(R.id.view_html)).setVisibility(View.INVISIBLE);

        }




        ((AppCompatButton) dialog.findViewById(R.id.view_html)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(ListMultiSelection.this, ViewHtml.class);
                intent.putExtra("userID",userID);
                intent.putExtra("instance",instanceStr);
                intent.putExtra("token",userToken);
                intent.putExtra("Notification_id", notification_id) ;
                startActivity(intent);
            }
        });


        ((Button) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  dialog.dismiss();
                //remarks=not_remarks.getText().toString();
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/respondwf/"+userID+"/"+userToken+"/"+message_type_int+"/"+notification_id+"/"+message_url_flag+"/"+not_remarks.getText().toString();
                if(redirection==1)
                    redirection=0;
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response.toString());
                            user_valid = data.getBoolean("valid_user");
                            if(user_valid==true) {
                                message = data.getString("message");

                                user_valid=false;
                                items.remove(pos);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(ListMultiSelection.this, message.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            else
                            {
                                message = data.getString("message");
                                Toast.makeText(ListMultiSelection.this, message.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ListMultiSelection.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //                            // tx.setText( "Error: " + e.getMessage());
                            dialog.dismiss();
                        }
                    }

                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
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
                        Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                {
                };

                MyStringRequest.setShouldCache(false);
                MyRequestQueue1.add(MyStringRequest);

            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_reject)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dialog.dismiss();
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/respondwf/"+userID+"/"+userToken+"/"+message_type_int+"/"+notification_id+"/2";


                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response.toString());
                            user_valid = data.getBoolean("valid_user");
                            if(user_valid==true) {
                                message = data.getString("message");
                                user_valid=false;

                                Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_LONG).show();
                                items.remove(pos);
                                mAdapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }

                            else
                            {
                                message = data.getString("message");
                                Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ListMultiSelection.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //                            // tx.setText( "Error: " + e.getMessage());
                            dialog.dismiss();
                        }
                    }

                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
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
                        Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                {
                };

                MyStringRequest.setShouldCache(false);
                MyRequestQueue1.add(MyStringRequest);
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_approve)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  dialog.dismiss();
                //remarks=not_remarks.getText().toString();
                String url = "http://"+instanceStr+".5dsurf.com/app/webservice/respondwf/"+userID+"/"+userToken+"/"+message_type_int+"/"+notification_id+"/1"+not_remarks.getText().toString();
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response.toString());
                            user_valid = data.getBoolean("valid_user");
                            if(user_valid==true) {
                                message = data.getString("message");

                                user_valid=false;
                                items.remove(pos);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(ListMultiSelection.this, message.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            else
                            {
                                message = data.getString("message");
                                Toast.makeText(ListMultiSelection.this, message.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ListMultiSelection.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //                            // tx.setText( "Error: " + e.getMessage());
                            dialog.dismiss();
                        }
                    }

                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
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
                        Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                {
                };

                MyStringRequest.setShouldCache(false);
                MyRequestQueue1.add(MyStringRequest);

            }
        });

     final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();



        RequestQueue MyRequestQueue = Volley.newRequestQueue(ctx);


        String url = "http://"+instanceStr+".5dsurf.com/app/webservice/getnotificationsdetails/"+userID+"/"+userToken+"/"+notification_id;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(ListMultiSelection.this, "in func", Toast.LENGTH_SHORT).show();
                JSONObject data = null;

                try {
                    //   Toast.makeText(ListMultiSelection.this, response, Toast.LENGTH_SHORT).show();
                    data = new JSONObject(response.toString());
                    user_valid = data.getBoolean("valid_user");
                    if(user_valid==true) {

                        notification=data.getString("notifications");
                        JSONObject not_obj=new JSONObject(notification);

                        messagename = not_obj.getString("messagename");
                        if(not_obj.getString("tousername").trim().equals("")) {
                            tousername ="Unknown";
                        }
                        else
                        tousername = not_obj.getString("tousername");

                        subject = not_obj.getString("subject");
                      //  remarks=not_obj.getString("remarks");

                        Status=not_obj.getString("status");
                        begindate=not_obj.getString("begindate");

                        workFlowID=not_obj.getString("ID");

                        messagehtml=not_obj.getString("messagehtml");
                        redirection=not_obj.getInt("redirectionflag");
                      //  Toast.makeText(ListMultiSelection.this, "redir="+redirection, Toast.LENGTH_SHORT).show();


                         not_from_user.setText(tousername);
                         not_message_name.setText(messagename);
                         not_subject.setText(subject);
                         not_status.setText(Status);
                         not_begin_date.setText(begindate);
                        Picasso.get().load(items.get(pos).imagelist).transform(new CircleTransform()).into(pic);

                     //    not_remarks.setText("Remarks: "+remarks);

                        if(message_type.equals("FYR") && redirection==1)
                        {

                            ((AppCompatButton) dialog.findViewById(R.id.bt_approve)).setVisibility(View.INVISIBLE);
                            ((AppCompatButton) dialog.findViewById(R.id.bt_reject)).setVisibility(View.INVISIBLE);
                            dialog.findViewById(R.id.not_remarks).setVisibility(View.INVISIBLE);
                            ((Button) dialog.findViewById(R.id.bt_cancel)).setGravity(View.FOCUS_RIGHT);
                            message_type_int = 1;
                        }

                        if ( progressDialog.isShowing())
                            progressDialog.hide();

                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.show();
                        dialog.getWindow().setAttributes(lp);


                    }

                    else
                    {
                        if ( progressDialog.isShowing())
                            progressDialog.hide();
                        message = data.getString("message");
                        Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    if ( progressDialog.isShowing())
                        progressDialog.hide();
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(ListMultiSelection.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //                            // tx.setText( "Error: " + e.getMessage());

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
                Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.hide();

            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();





    }


}