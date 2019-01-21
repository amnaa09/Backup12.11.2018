package com.example.sammrabatool.solutions5d.OTL.SignatureUpload;

import android.Manifest;
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
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.Utils;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_signature);

        setTitle("Signature test");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

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

           // Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
            dialog.show();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                jsonObject.put(Utils.imageName, "Signature"+imgCounter++);

                        //etxtUpload.getText().toString().trim());
                Log.e("Image name", "Signature"+imgCounter++);
                        //etxtUpload.getText().toString().trim());
                jsonObject.put(Utils.image, encodedImage);
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
            Volley.newRequestQueue(this).add(jsonObjectRequest);



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

            Toast.makeText(getApplicationContext(), "Signature file is saved to " + filePath, Toast.LENGTH_LONG).show(); */
            Intent intent=new Intent(UploadSignature.this, CheckIn.class);
            //  intent.putExtra("userID",userID);
            //  intent.putExtra("token",token);
            //  intent.putExtra("instance", instanceStr);
            //  intent.putExtra("lg",lg);
            //  intent.putExtra("bg",bg);
            // intent.putExtra("type","in");
            startActivity(intent);

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
