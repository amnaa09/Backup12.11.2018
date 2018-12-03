package com.example.sammrabatool.solutions5d.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.Tools;
import com.example.sammrabatool.solutions5d.dialog.DialogAddReview;
import com.example.sammrabatool.solutions5d.list.ListMultiSelection;
import com.example.sammrabatool.solutions5d.list.ViewHtml;
import com.example.sammrabatool.solutions5d.model.Notification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ProfileFabMenu extends AppCompatActivity {
LinearLayout Basic,Employee,Passport,Visa,National,Labor;
FloatingActionButton basi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fab_menu);
        basi=(FloatingActionButton)findViewById(R.id.basicc);
Basic=(LinearLayout)findViewById(R.id.basic);
Employee=(LinearLayout)findViewById(R.id.employeemnt);
Passport=(LinearLayout)findViewById(R.id.passport);
Visa=(LinearLayout)findViewById(R.id.visa);
National=(LinearLayout)findViewById(R.id.national);
Labor=(LinearLayout)findViewById(R.id.labor);
basi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(ProfileFabMenu.this, "working", Toast.LENGTH_SHORT).show();

    }
});


       initToolbar();
    }
    private void showCustomDialog(final String notification_id, final String message_type, Context ctx, final List<Notification> items, final int pos)
    {

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        final ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.setContentView(R.layout.dialog_notification_detail);
        dialog.setCancelable(true);

        final TextView not_from_user=(TextView) dialog.findViewById(R.id.not_from_user);
        final  TextView not_message_name=(TextView) dialog.findViewById(R.id.not_message_name);
        final  TextView not_subject=(TextView) dialog.findViewById(R.id.not_subject);
        final   TextView not_status=(TextView) dialog.findViewById(R.id.not_status);
        final   TextView not_begin_date=(TextView) dialog.findViewById(R.id.not_begin_date);
        final EditText not_remarks=(EditText) dialog.findViewById(R.id.not_remarks);
        final ImageButton close=(ImageButton) dialog.findViewById(R.id.bt_Close);


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
            ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setGravity(View.FOCUS_RIGHT);
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


        ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
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


                        not_from_user.setText("From User: "+tousername);
                        not_message_name.setText("Message: "+messagename);
                        not_subject.setText("Subject: "+subject);
                        not_status.setText("Status: "+Status);
                        not_begin_date.setText("Begin Date: "+begindate);
                        //    not_remarks.setText("Remarks: "+remarks);

                        if(message_type.equals("FYR") && redirection==1)
                        {

                            ((AppCompatButton) dialog.findViewById(R.id.bt_approve)).setVisibility(View.INVISIBLE);
                            ((AppCompatButton) dialog.findViewById(R.id.bt_reject)).setVisibility(View.INVISIBLE);
                            dialog.findViewById(R.id.not_remarks).setVisibility(View.INVISIBLE);
                            ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setGravity(View.FOCUS_RIGHT);
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

            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();





    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HR Profile");
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
        } else if (id == R.id.logout){
            Toast.makeText(this,"You have successfully logged out",Toast.LENGTH_LONG).show();
            SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent=new Intent(ProfileFabMenu.this,LoginCardOverlap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
