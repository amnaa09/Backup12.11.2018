package com.example.sammrabatool.solutions5d.list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.example.sammrabatool.solutions5d.AdapterListInbox;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.data.DataGenerator;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.example.sammrabatool.solutions5d.widget.LineItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        parent_view = findViewById(R.id.lyt_parent);

        userID=getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        userToken=getIntent().getStringExtra("token");
        type=getIntent().getStringExtra("type");

        initToolbar(type);
        initComponent();
     //   Toast.makeText(this, "Long press for multi selection", Toast.LENGTH_SHORT).show();
    }

  /*  @Override
    public void onItemClick(View view, Inbox obj, int pos) {
        // The onClick implementation of the RecyclerView item click
        //ur intent code here
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        showCustomDialog();
    } */



    private void initToolbar(String type) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        if(type=="1")
            getSupportActionBar().setTitle("FYI");
        else
            getSupportActionBar().setTitle("FYR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);


       getNotificationData(ListMultiSelection.this);




        actionModeCallback = new ActionModeCallback();

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
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

           // showCustomDialog();
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
                            message_name=not_obj.getString("message_name");
                            obj.message_name=message_name;
                            status=not_obj.getString("status");
                            message_subject=not_obj.getString("message_subject");
                            obj.message_subject=message_subject;
                            date=not_obj.getString("date");
                            obj.date=date;
                            touser=not_obj.getString("touser");
                            obj.touser=touser;
                            pic=not_obj.getString("pic");
                            obj.imagelist=pic;
                            items.add(obj);

                            }

                        mAdapter = new AdapterListInbox(ListMultiSelection.this, items);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.setOnClickListener(new AdapterListInbox.OnClickListener() {
                            @Override
                            public void onItemClick(View view, Notification obj, int pos) {
                                if (mAdapter.getSelectedItemCount() > 0) {
                                    enableActionMode(pos);
                                } else {
                                    // read the inbox which removes bold from the row
                                    Notification objNot = mAdapter.getItem(pos);
                                    Toast.makeText(getApplicationContext(), "Read: " + objNot.message_subject, Toast.LENGTH_SHORT).show();
                                    showCustomDialog(notification_id, ctx);
                                }
                            }



                        });


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
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

    }

    private void showCustomDialog(String notification_id, Context ctx) {


        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_notification_detail);
        dialog.setCancelable(true);

        final  TextView not_from_user=(TextView) dialog.findViewById(R.id.not_from_user);
        final  TextView not_message_name=(TextView) dialog.findViewById(R.id.not_message_name);
        final  TextView not_subject=(TextView) dialog.findViewById(R.id.not_subject);
        final   TextView not_status=(TextView) dialog.findViewById(R.id.not_status);
        final   TextView not_begin_date=(TextView) dialog.findViewById(R.id.not_begin_date);
        final   TextView not_remarks=(TextView) dialog.findViewById(R.id.not_remarks);



        ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
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
                        tousername = not_obj.getString("tousername");

                        subject = not_obj.getString("subject");
                        remarks=not_obj.getString("remarks");

                        Status=not_obj.getString("status");
                        begindate=not_obj.getString("begindate");

                        workFlowID=not_obj.getString("ID");

                        messagehtml=not_obj.getString("messagehtml");


                         not_from_user.setText("From User: "+tousername);
                         not_message_name.setText("Message: "+messagename);
                         not_subject.setText("Subject: "+subject);
                         not_status.setText("Status: "+Status);
                         not_begin_date.setText("Begin Date: "+begindate);
                         not_remarks.setText("Remarks: "+remarks);

                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.show();
                        dialog.getWindow().setAttributes(lp);


                    }

                    else
                    {
                        message = data.getString("message");
                        Toast.makeText(ListMultiSelection.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
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

            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);





    }


}