package com.example.sammrabatool.solutions5d.Reminder;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.sammrabatool.solutions5d.list.AdapterListInbox;
import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context ctx;
    private List<Model> list;
    private SparseBooleanArray selected_items;
    private View.OnClickListener onClickListener = null;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    String instanceStr, userID, token, userName,message,notificationID,type;
    int lg, bg;
    public CustomAdapter(Context ctx, List<Model> list) {

        inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
        this.list=list;
        selected_items = new SparseBooleanArray();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Model obj = list.get(position);
      //  holder.fyr.setText(list.get(position).getFyr());
       // holder.id.setText(String.valueOf(list.get(position).getNotifcation_id()));
        holder.name.setText(list.get(position).getName());
        holder.date.setText(String.valueOf(list.get(position).getDate()));
       // holder.status.setText(list.get(position).getStatus());
        holder.msg.setText(String.valueOf(list.get(position).getMessage()));
        //holder.image.setImageURI(Uri.parse(inbox.imagelist));
        try {
            URL url = new URL(obj.image);

            //   try {

            Picasso.get().load(obj.image).transform(new CircleTransform()).into(holder.image);

        }
        catch (MalformedURLException error) {
            //  Toast.makeText(ListMultiSelection.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

        }
//        holder.lyt_parent.setActivated(selected_items.get(position,false));
//        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickListener == null) return;
//                onClickListener.onItemClick(v, obj, position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected FloatingActionButton btn_plus, btn_minus;
        private TextView fyr, id,name,date,status,msg,image_letter;
       private ImageView imageView;
        public ImageView image, imaget1, imaget2;
        public RelativeLayout lyt_checked, lyt_image;
//        public View lyt_parent;

        public MyViewHolder(View itemView) {
            super(itemView);
//imageView=(ImageView)itemView.findViewById(R.id.image);
//            id=(TextView)itemView.findViewById(R.id.a);
//            fyr = (TextView) itemView.findViewById(R.id.g);
            name = (TextView) itemView.findViewById(R.id.h);
            date = (TextView) itemView.findViewById(R.id.e);
//            status = (TextView) itemView.findViewById(R.id.d);
            msg = (TextView) itemView.findViewById(R.id.b);
            image_letter = (TextView) itemView.findViewById(R.id.image_letter);
            image = (ImageView) itemView.findViewById(R.id.list_image);
            lyt_checked = (RelativeLayout) itemView.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) itemView.findViewById(R.id.lyt_image);
//            lyt_parent = (View) itemView.findViewById(R.id.lyt_parent);

            btn_plus = (FloatingActionButton) itemView.findViewById(R.id.done);
            btn_minus = (FloatingActionButton) itemView.findViewById(R.id.working);

            btn_plus.setTag(R.integer.btn_plus_view, itemView);
            btn_minus.setTag(R.integer.btn_minus_view, itemView);
            btn_plus.setOnClickListener(this);
            btn_minus.setOnClickListener(this);

        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == btn_plus.getId()){
                RequestQueue MyRequestQueue = Volley.newRequestQueue(ctx);

                String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/UpdateReminderStatus/" + bg + "/" + lg + "/" + userID + "/" + token+"/"+notificationID+"/"+"1";
                final ProgressDialog progressDialog = new ProgressDialog(ctx);
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject data = null;
                        try {
                            data = new JSONObject(response.toString());
                            message = data.getString("message");

                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ctx, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                    }
                }
                );
                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                progressDialog.show();
//                View tempview = (View) btn_plus.getTag(R.integer.btn_plus_view);
//                TextView tv = (TextView) tempview.findViewById(R.id.a);
//                int number = Integer.parseInt(tv.getText().toString()) + 1;
//                tv.setText(String.valueOf(number));
//                list.get(getAdapterPosition()).setName("name");

            } else if(v.getId() == btn_minus.getId()) {
                RequestQueue MyRequestQueue = Volley.newRequestQueue(ctx);

                String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/UpdateReminderStatus/" + bg + "/" + lg + "/" + userID + "/" + token+"/"+notificationID+"/"+"2";
                final ProgressDialog progressDialog = new ProgressDialog(ctx);
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject data = null;
                        try {
                            data = new JSONObject(response.toString());
                            message = data.getString("message");

                        } catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(ctx, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                    }
                }
                );
                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                progressDialog.show();

//                View tempview = (View) btn_minus.getTag(R.integer.btn_minus_view);
//                TextView tv = (TextView) tempview.findViewById(R.id.number);
//                int number = Integer.parseInt(tv.getText().toString()) - 1;
//                tv.setText(String.valueOf(number));
//                Recyclerview.modelArrayList.get(getAdapterPosition()).setNumber(number);
            }
        }

    }
}


