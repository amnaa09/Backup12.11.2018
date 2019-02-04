package com.example.sammrabatool.solutions5d.OTL.SignatureUpload;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.OTL.CheckIn;
import com.example.sammrabatool.solutions5d.OTL.OTLDialogActivity;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class UploadSignature extends AppCompatActivity {



    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static  int imgCounter=0;

    private GestureOverlayView gestureOverlayView = null;

    private Button redrawButton = null;

    private Button saveButton = null;
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;
    double latitude;
    double longitude;
    String instanceStr,  userID, token, empPicture, empName, timesheetID, personID, attendanceDate, type, project, task, activity, checkinTime;
    Double user_longitude, user_latitude;
    int lg,bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_signature);

        setTitle("Signature test");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

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

  /*      instanceStr=getIntent().getStringExtra("instanceStr");
        token=getIntent().getStringExtra("token");
        lg=getIntent().getIntExtra("lg", 0);
        bg=getIntent().getIntExtra("bg", 0);
        userID=getIntent().getStringExtra("userID");
        longitude=getIntent().getDoubleExtra("longitude", 0);
        latitude=getIntent().getDoubleExtra("latitude", 0);
        empName=getIntent().getStringExtra("empName");
        empPicture=getIntent().getStringExtra("empPic");*/

        jsonObject = new JSONObject();
        init();

        gestureOverlayView.addOnGesturePerformedListener(new CustomGestureListener());

        redrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gestureOverlayView.clear(false);
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndSaveSignature();
            }
        });


    }


    private void init()
    {
        if(gestureOverlayView==null)
        {
            gestureOverlayView = (GestureOverlayView)findViewById(R.id.sign_pad);
        }

        if(redrawButton==null)
        {
            redrawButton = (Button)findViewById(R.id.redraw_button);
        }

        if(saveButton==null)
        {
            saveButton = (Button)findViewById(R.id.save_button);
        }
    }


    private void checkPermissionAndSaveSignature()
    {
        try {

            // Check whether this app has write external storage permission or not.
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // If do not grant write external storage permission.
            if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
            {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(UploadSignature.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            }
            else
            {
                saveSignature();
            }

        } catch (Exception e) {
            Log.v("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }


    private void saveSignature()
    {
        try {

            // First destroy cached image.
            gestureOverlayView.destroyDrawingCache();

            // Enable drawing cache function.
            gestureOverlayView.setDrawingCacheEnabled(true);

            // Get drawing cache bitmap.
            Bitmap drawingCacheBitmap = gestureOverlayView.getDrawingCache();

            // Create a new bitmap
            Bitmap bitmap = Bitmap.createBitmap(drawingCacheBitmap);
            String imageArray[]=new String[1];
           // Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
         //   dialog.show();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);


            byte[] byteArray = byteArrayOutputStream.toByteArray();

           /* Intent intent = new Intent(this, OTLDialogActivity.class);
            intent.putExtra("image",byteArray);
            intent.putExtra("fromSignature", 1);
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
            startActivity(intent);
            finish();*/

            Intent returnIntent = new Intent();
          //  returnIntent.putExtra("result",result);
            returnIntent.putExtra("image",byteArray);
            returnIntent.putExtra("fromSignature", 1);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

       /*     String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            imageArray[0]=encodedImage;

            JSONArray imgArray=new JSONArray();
            for(int i=0;i<1;i++)
                imgArray.put(imageArray[i]);
            try {
                jsonObject.put(Utils.imageName, "Signature"+imgCounter++);

                        //etxtUpload.getText().toString().trim());
                Log.e("Image name", "Signature"+imgCounter++);
                jsonObject.put("picCounter", 1);
                        //etxtUpload.getText().toString().trim());
                Log.e("Image=", imageArray[0]);
                jsonObject.put(Utils.image, imgArray);
            } catch (JSONException e) {
                Log.e("JSONObject Here", e.toString());
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Utils.urlUpload, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.e("Message from server", jsonObject.toString());
                            dialog.dismiss();
                           // messageText.setText("Image Uploaded Successfully");
                            Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Message from server:   ", volleyError.toString());
               //     messageText.setText("Message from server:   "+ volleyError.toString());
                    dialog.dismiss();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(this).add(jsonObjectRequest);*/



            // Get image file save path and name.
        /*    String filePath = "http://192.168.1.9/AndroidFileUpload/file1.php";
                    //Environment.getExternalStorageDirectory().toString();

            filePath += File.separator;

            filePath += "sign"+ String.valueOf(imgCounter++)+".png";

            File file = new File(filePath);

            if(file.exists())
            {
                Toast.makeText(this, "pic exist", Toast.LENGTH_SHORT).show();
                file.delete();
            }


            file.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            // Compress bitmap to png image.
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            // Flush bitmap to image file.
            fileOutputStream.flush();

            // Close the output stream.
            fileOutputStream.close();

         //   Toast.makeText(getApplicationContext(), "Signature file is saved to " + filePath, Toast.LENGTH_LONG).show(); */
       //     Intent intent=new Intent(UploadSignature.this, CheckIn.class);
            //  intent.putExtra("userID",userID);
            //  intent.putExtra("token",token);
            //  intent.putExtra("instance", instanceStr);
            //  intent.putExtra("lg",lg);
            //  intent.putExtra("bg",bg);
            // intent.putExtra("type","in");
         //   startActivity(intent);

        } catch (Exception e) {
            Log.v("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveSignature();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
