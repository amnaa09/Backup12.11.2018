package com.example.sammrabatool.solutions5d.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.sammrabatool.solutions5d.model.Notification;
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
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.verification.VerificationCode;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.sammrabatool.solutions5d.list.ListMultiSelection.notification;

public class ViewHtml extends AppCompatActivity {

    String userID,instanceStr,userToken, not_id,messagehtml="",message;
    boolean user_valid;
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_html);


        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        userID= getIntent().getStringExtra("userID");
        instanceStr=getIntent().getStringExtra("instance");
        userToken=getIntent().getStringExtra("token");
        not_id=getIntent().getStringExtra("Notification_id");

        wv=(WebView) findViewById(R.id.webView);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(ViewHtml.this);
        String url ="http://"+instanceStr+".5dsurf.com/app/webservice/getnotificationsdetails/"+userID+"/"+userToken+"/"+not_id;
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


                        messagehtml=not_obj.getString("messagehtml");

                        wv.loadDataWithBaseURL("", messagehtml, mimeType, encoding, "");


                    }

                    else
                    {
                        message = data.getString("message");
                        Toast.makeText(ViewHtml.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //
                    //                            //  instance.setText("error= " + e.getMessage());
                    Toast.makeText(ViewHtml.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ViewHtml.this, message, Toast.LENGTH_SHORT).show();

            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

    }
}
