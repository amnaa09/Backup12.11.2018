package com.example.sammrabatool.solutions5d.OTL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.OTL.Utils;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.UploadSignature;

import com.example.sammrabatool.solutions5d.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OTLDialogActivity extends Activity {
    TextView txt,empNameText;
    private ProgressDialog progressDialog ;
    private JSONObject jsonObject;
    private static int counterImages=0;
    private static int picCounter=0;
    ImageView profileImage;
    EditText txt1;
    double user_latitude;
    double user_longitude;
    String instanceStr,note,  userID, token, empPicture, empName, timesheetID,personID,attendanceDate,type,  project, task, activity,checkinTime  ;
    int lg,bg;
    ImageView imageview1, imageview2,imageview3,imageview4,imageview5;
    Button upload_img, upload_signature, submit;
     SharedPreferences checkin_preferences;
    byte[] byteArray;
  //  public static Bitmap bm1,bm2,bm3,bm4,bm5,bm6,bm7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_otl_remarks);
        progressDialog = new ProgressDialog(this);
        // toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileImage=(ImageView) findViewById(R.id.img);
        empNameText=(TextView) findViewById(R.id.dialog_profilename);
        txt1 = (EditText) findViewById(R.id.et_post);
        upload_img=(Button) findViewById(R.id.upload_image) ;
        upload_signature=(Button) findViewById(R.id.upload_signature) ;
        submit=(Button) findViewById(R.id.submit_checkin) ;
        imageview1 = (ImageView)findViewById(R.id.imageView_pic);
        imageview2 = (ImageView)findViewById(R.id.imageView_pic2);
        imageview3 = (ImageView)findViewById(R.id.imageView_pic3);
        imageview4 = (ImageView)findViewById(R.id.imageView_pic4);
        imageview5 = (ImageView)findViewById(R.id.imageView_pic5);


        imageview1.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        imageview3.setVisibility(View.GONE);
        imageview4.setVisibility(View.GONE);
        imageview5.setVisibility(View.GONE);

    //    final double longitude = getIntent().getDoubleExtra("Longitude", 0);
     //   final double latitude = getIntent().getDoubleExtra("Latitude", 0);
    //    int attendence = getIntent().getIntExtra("Attendence", 0);
    //   final String time = getIntent().getStringExtra("Time");
        empName="";
        empPicture="";
        checkin_preferences = getSharedPreferences("checkin_preferences", Context.MODE_PRIVATE);
        instanceStr=getIntent().getStringExtra("instanceStr");
        token=getIntent().getStringExtra("token");
        lg=getIntent().getIntExtra("lg", 0);
        bg=getIntent().getIntExtra("bg", 0);
        userID=getIntent().getStringExtra("userID");
        user_longitude=getIntent().getDoubleExtra("longitude", 0);
        user_latitude=getIntent().getDoubleExtra("latitude", 0);
        empName=getIntent().getStringExtra("empName");
        empPicture=getIntent().getStringExtra("empPic");
        timesheetID=getIntent().getStringExtra("timesheetID");
        personID=getIntent().getStringExtra("personID");
        attendanceDate=getIntent().getStringExtra("attendanceDate");
        type=getIntent().getStringExtra("type");
        project=getIntent().getStringExtra("project");
        task=getIntent().getStringExtra("task");
        activity=getIntent().getStringExtra("activity");
        checkinTime=getIntent().getStringExtra("checkinTime");


     //   Toast.makeText(this, "name="+empName+"pic="+empPicture, Toast.LENGTH_SHORT).show();
        if(empName.compareTo("")==0)
            empNameText.setText("Unknown");
        else
            empNameText.setText(empName);

        if(empPicture.compareTo("")!=0)
        {
            int SDK_INT = Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
           //     try {
                   //    URL url = new URL(data.getString(""));
                    //    Toast.makeText(ProfilePurple.this, "picccc", Toast.LENGTH_SHORT).show();

                   Picasso.get().load(empPicture).transform(new CircleTransform()).into(profileImage);

              //  }
            //    catch ( ) {
             //       Toast.makeText(OTLDialogActivity.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

             //   }

            }
        }
        picCounter=0;
   //     counterImages=0;
        jsonObject = new JSONObject();



//-------------getting signature image from signature activity-----------------------------------
       /* byte[] byteArray = getIntent().getByteArrayExtra("image");
        if(byteArray!=null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
           // imageview1.setVisibility(View.VISIBLE);
          //  imageview1.setImageBitmap(bmp);
            int counterFlag = getIntent().getIntExtra("fromSignature", 0);
           // picCounter++;
            if(picCounter==0) {
                imageview1.setVisibility(View.VISIBLE);
                imageview1.setImageBitmap(bmp);
                picCounter++;
            }
            else if(picCounter==1) {
                imageview2.setVisibility(View.VISIBLE);
                imageview2.setImageBitmap(bmp);
                picCounter++;
            }
            else if(picCounter==2) {
                imageview3.setVisibility(View.VISIBLE);
                imageview3.setImageBitmap(bmp);
                picCounter++;
            }
            else if(picCounter==3) {
                imageview4.setVisibility(View.VISIBLE);
                imageview4.setImageBitmap(bmp);
                picCounter++;
            }
            else if(picCounter==4) {
                picCounter++;
                imageview5.setVisibility(View.VISIBLE);
                imageview5.setImageBitmap(bmp);
            }
          //  if (counterFlag == 1)
            //    picCounter = 1;
        }*/

        //----------------------------------end---------------------------------------

            upload_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, Utils.REQCODE);
                    }
            });

        upload_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = txt1.getText().toString().trim();
                if (review.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill review text", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(OTLDialogActivity.this, UploadSignature.class);
                    startActivityForResult(i, 1);


                  /*  Intent intent=new Intent(OTLDialogActivity.this, UploadSignature.class);

                    intent.putExtra("instanceStr", instanceStr);
                    intent.putExtra("lg", lg);
                    intent.putExtra("bg", bg);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);
                    intent.putExtra("timesheetID", timesheetID);
                    intent.putExtra("personID", personID);
                    intent.putExtra("attendanceDate", attendanceDate);
                    intent.putExtra("type", type);
                    intent.putExtra("project", project);
                    intent.putExtra("task", task);
                    intent.putExtra("activity", activity);
                    intent.putExtra("checkinTime", checkinTime);
                    intent.putExtra("longitude", user_longitude);
                    intent.putExtra("latitude", user_latitude);
                    intent.putExtra("empName", empName);
                    intent.putExtra("empPic", empPicture);
                    startActivity(intent);*/
                   // finish();


                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image1, image2,image3,image4,image5;
                String imageArray[]=new String[5];
                progressDialog.show();
                if(picCounter==1) {
                    image1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                    imageArray[0]=encodedImage1;
                }
                else if(picCounter==2){
                    image1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                    imageArray[0]=encodedImage1;

                    image2=((BitmapDrawable) imageview2.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    image2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    String encodedImage2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
                    imageArray[1]=encodedImage2;
                }

                else if(picCounter==3){
                    image1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                    imageArray[0]=encodedImage1;

                    image2=((BitmapDrawable) imageview2.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    image2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    String encodedImage2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
                    imageArray[1]=encodedImage2;

                    image3=((BitmapDrawable) imageview3.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    image3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                    String encodedImage3 = Base64.encodeToString(byteArrayOutputStream3.toByteArray(), Base64.DEFAULT);
                    imageArray[2]=encodedImage3;
                }
                else if(picCounter==4){
                    image1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                    imageArray[0]=encodedImage1;

                    image2=((BitmapDrawable) imageview2.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    image2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    String encodedImage2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
                    imageArray[1]=encodedImage2;

                    image3=((BitmapDrawable) imageview3.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    image3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                    String encodedImage3 = Base64.encodeToString(byteArrayOutputStream3.toByteArray(), Base64.DEFAULT);
                    imageArray[2]=encodedImage3;

                    image4=((BitmapDrawable) imageview4.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                    image4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream4);
                    String encodedImage4 = Base64.encodeToString(byteArrayOutputStream4.toByteArray(), Base64.DEFAULT);
                    imageArray[3]=encodedImage4;
                }
                else if(picCounter==5){
                    image1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                    imageArray[0]=encodedImage1;

                    image2=((BitmapDrawable) imageview2.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    image2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    String encodedImage2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
                    imageArray[1]=encodedImage2;

                    image3=((BitmapDrawable) imageview3.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    image3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                    String encodedImage3 = Base64.encodeToString(byteArrayOutputStream3.toByteArray(), Base64.DEFAULT);
                    imageArray[2]=encodedImage3;

                    image4=((BitmapDrawable) imageview4.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                    image4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream4);
                    String encodedImage4 = Base64.encodeToString(byteArrayOutputStream4.toByteArray(), Base64.DEFAULT);
                    imageArray[3]=encodedImage4;

                    image5=((BitmapDrawable) imageview5.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream();
                    image5.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream5);
                    String encodedImage5 = Base64.encodeToString(byteArrayOutputStream5.toByteArray(), Base64.DEFAULT);
                    imageArray[4]=encodedImage5;
                }


                JSONArray imgArray=new JSONArray();
                JSONArray nameArray=new JSONArray();
                for(int i=0;i<picCounter;i++) {
                    imgArray.put(imageArray[i]);
                    nameArray.put(userID+"_"+counterImages++);
                }
                //   String jsonObjectString = new Gson().toJson(imageArray);



                try {
                  //  jsonObject.put(Utils.imageName, userID);
                    //   Log.e("Image name", etxtUpload.getText().toString().trim());
                    jsonObject.put("picCounter", picCounter);
                //    jsonObject.put("titleCounter", counterImages);
                    //  jsonObject.put(Utils.image, tmp);
                    jsonObject.put(Utils.image, imgArray);
                    jsonObject.put(Utils.imageName, nameArray);


                    //  messageText.setText(encodedImage1);
                    Log.e("Images", imgArray.toString());

                    Log.e("JSON", jsonObject.toString());

                    Toast.makeText(OTLDialogActivity.this, "pic counter="+picCounter, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                }

          /*      Location loc= new Location("");
                loc.setLongitude((double)user_longitude);
                loc.setLatitude((double)user_latitude);
                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();
                Date d = new Date(loc.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/ kk:mm:ss");
                sdf.setTimeZone(tz);
                String time= sdf.format(d);
                if(loc!=null)*/
                Toast.makeText(OTLDialogActivity.this, "time="+checkinTime+"long="+user_longitude, Toast.LENGTH_SHORT).show();

                note=txt1.getText().toString();
                //--------------------calling webservice for tasks-------------------------------

                final JSONObject jsonObject = new JSONObject();
                try {
         /*           jsonObject.put("bg", String.valueOf(bg));
                    jsonObject.put("lg", String.valueOf(lg));
                    jsonObject.put("userID", String.valueOf(userID));
                    jsonObject.put("token", token);
                    jsonObject.put("timesheetID", String.valueOf(timesheetID));
                    jsonObject.put("personID", personID);
                    jsonObject.put("attendanceDate", String.valueOf(attendanceDate));
                    jsonObject.put("type", type);
                    jsonObject.put("project", project);
                    jsonObject.put("task", task);
                    jsonObject.put("activity", activity);
                    jsonObject.put("note", note);
                    jsonObject.put("checkinTime", String.valueOf(checkinTime));
                    jsonObject.put("costing", "0");
                    jsonObject.put("longitude", String.valueOf(user_longitude));
                    jsonObject.put("latitude", String.valueOf(user_latitude));*/

         jsonObject.put("test","test");
        /*            try {
                        if (jsonObject.getInt("picCounter") > 0) {
                            jsonObject.put("attachments", String.valueOf(jsonObject));
                            //   Toast.makeText(OTLDialogActivity.this, " image attached", Toast.LENGTH_SHORT).show();

                        }
                        //   else
                        //   Toast.makeText(OTLDialogActivity.this, "no image attached", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e("json", "not available");
                    }*/

                }
                catch (JSONException e) {
                    // handle exception
                    Toast.makeText(OTLDialogActivity.this, "Error json:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                RequestQueue MyRequestQueue = Volley.newRequestQueue(OTLDialogActivity.this);
               String url = "http://" + instanceStr + ".5dsurf.com/app/webservice/checkIn";
                String url1 = "http://" + instanceStr + ".5dsurf.com/app/webservice/checkIn/"+ bg + "/" + lg  + "/" + userID+"/"+token+"/"+timesheetID+"/"+personID+"/"+attendanceDate+"/"+type+"/"+project+"/"+task+"/"+activity+"/"+note+"/"+checkinTime+"/"+"0/"+user_longitude+"/"+user_latitude;
               Log.e("url:", url1);
                final JsonObjectRequest   MyStringRequest = new JsonObjectRequest (Request.Method.PUT, url,jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                       // Toast.makeText(OTLDialogActivity.this, "res="+response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject data = new JSONObject(response.toString());

                            Toast.makeText(OTLDialogActivity.this, "check in done"+response.toString(), Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            //   message = data.getString("message");
                            // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            // }

                        }
                        catch (JSONException e) {
                            if (progressDialog.isShowing())
                                progressDialog.hide();
                            e.printStackTrace();
                            //
                            //                            //  instance.setText("error= " + e.getMessage());
                            Toast.makeText(OTLDialogActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("OTL Error" , "error: " + error);
                        //This code is executed if there is an error.
                        String message = null;
                   //     Log.d("Maps:", " Error: " + (error.networkResponse.data).toString());
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
                        Toast.makeText(OTLDialogActivity.this, "mesg="+message, Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.hide();



                    }
                }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                 //   params.put("tess", "2222");
                 //   params.put("Authorization", "def1bc98d032");

                    // add this parameter
                    params.put("Content-Type", "application/json; charset=utf-8");
                    return params;
                }

                    @Override
                    public byte[] getBody() {

                        try {
                            Log.i("json", jsonObject.toString());
                            return jsonObject.toString().getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

             /*   {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();

                      //  String images = getStringImage(bitmap);
                       // Log.i("Mynewsam",""+images);
                        param.put("bg", String.valueOf(bg) );
                        param.put("lg", String.valueOf(lg) );
                        param.put("userID", String.valueOf(userID) );
                        param.put("token", token );
                        param.put("timesheetID", String.valueOf(timesheetID) );
                        param.put("personID", personID );
                        param.put("attendanceDate", String.valueOf(attendanceDate) );
                        param.put("type", type );
                        param.put("project", project );
                        param.put("task", task );
                        param.put("activity", activity );
                        param.put("note", note );
                        param.put("checkinTime", String.valueOf(checkinTime) );
                        param.put("costing", "0" );
                        param.put("longitude", String.valueOf(user_longitude) );
                        param.put("latitude", String.valueOf(user_latitude) );
                        try {
                           if (jsonObject.getInt("picCounter") > 0) {
                                param.put("attachments", String.valueOf(jsonObject));
                             //   Toast.makeText(OTLDialogActivity.this, " image attached", Toast.LENGTH_SHORT).show();

                            }
                         //   else
                             //   Toast.makeText(OTLDialogActivity.this, "no image attached", Toast.LENGTH_SHORT).show();
                        }catch (JSONException e)
                        {
                            Log.e("json", "not available");
                        }
                        return param;
                    }
                };*/
                MyStringRequest.setShouldCache(false);
                MyRequestQueue.add(MyStringRequest);

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                //    progressDialog.show();

                //---------------------------end calling webservice--------------------

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Uri selectedImageUri=null;
        if (requestCode == Utils.REQCODE && resultCode == RESULT_OK && data != null) {

            selectedImageUri = data.getData();
            String [] proj={MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery( selectedImageUri,
                    proj, // Which columns to return
                    null, // WHERE clause; which rows to return (all rows)
                    null, // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s= cursor.getString(column_index);
            String ImName = s.substring(s.lastIndexOf("/") + 1);
        //    Toast.makeText(this, "name="+ImName, Toast.LENGTH_SHORT).show();
            // Utils.imageName=ImName;
            //    String s= getRealPathFromURI(selectedImageUri);
            //  File f = new File("" + selectedImageUri);
            //  Utils.imageName=f.getName();

            picCounter++;
            if(picCounter==1) {
                imageview1.setVisibility(View.VISIBLE);
                imageview1.setImageURI(selectedImageUri);
            }
            else if(picCounter==2) {
                imageview2.setVisibility(View.VISIBLE);
                imageview2.setImageURI(selectedImageUri);

            }
            else if(picCounter==3) {
                imageview3.setVisibility(View.VISIBLE);
                imageview3.setImageURI(selectedImageUri);
            }
            else if(picCounter==4) {
                imageview4.setVisibility(View.VISIBLE);
                imageview4.setImageURI(selectedImageUri);
            }
            else if(picCounter==5) {
                imageview5.setImageURI(selectedImageUri);
                imageview5.setVisibility(View.VISIBLE);
            }


/*
try {
    Bitmap image1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
    //   Bitmap image1 = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
    dialog.show();
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
    String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
    try {
        jsonObject.put(Utils.imageName, ImName);
        Log.e("Image name", etxtUpload.getText().toString().trim());
        jsonObject.put(Utils.image, encodedImage1);
    } catch (JSONException e) {
        Log.e("JSONObject Here", e.toString());
    }
    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, Utils.urlUpload, jsonObject,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.e("Message from server", jsonObject.toString());
                    dialog.dismiss();
                    messageText.setText("Image Uploaded Successfully");
                    Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("Message from server:   ", volleyError.toString());
            messageText.setText("Message from server:   " + volleyError.toString());
            dialog.dismiss();
        }
    });
    jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    Volley.newRequestQueue(this).add(jsonObjectRequest1);
}
catch (IOException e)
{
    Toast.makeText(this, "Error uploading image: Please select an image first.", Toast.LENGTH_SHORT).show();
}
*/
        }

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){


                byteArray = data.getByteArrayExtra("image");
                if(byteArray!=null) {

                 //   Toast.makeText(this, "back here="+picCounter, Toast.LENGTH_SHORT).show();
                    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    // imageview1.setVisibility(View.VISIBLE);
                    //  imageview1.setImageBitmap(bmp);
                    int counterFlag = getIntent().getIntExtra("fromSignature", 0);
                    // picCounter++;
                    if(picCounter==0) {
                        imageview1.setVisibility(View.VISIBLE);
                        imageview1.setImageBitmap(bmp);
                        picCounter++;
                    }
                    else if(picCounter==1) {
                        imageview2.setVisibility(View.VISIBLE);
                        imageview2.setImageBitmap(bmp);
                        picCounter++;
                      //  Toast.makeText(this, "im here", Toast.LENGTH_SHORT).show();

                    }
                    else if(picCounter==2) {
                        imageview3.setVisibility(View.VISIBLE);
                        imageview3.setImageBitmap(bmp);
                        picCounter++;
                    }
                    else if(picCounter==3) {
                        imageview4.setVisibility(View.VISIBLE);
                        imageview4.setImageBitmap(bmp);
                        picCounter++;
                    }
                    else if(picCounter==4) {
                        picCounter++;
                        imageview5.setVisibility(View.VISIBLE);
                        imageview5.setImageBitmap(bmp);
                    }
                    //  if (counterFlag == 1)
                    //    picCounter = 1;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
