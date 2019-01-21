package com.example.sammrabatool.solutions5d.OTL;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.UploadSignature;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivityOut extends AppCompatActivity {
    TextView txt;
    Button btn;
    Toolbar toolbar;
    Button in, out;
    CircularImageView map;
    //  public static final int GET_FROM_GALLERY = 3;
    private static int RESULT_LOAD_IMAGE = 1;

    //private static int SELECTED_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_out);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        txt = (TextView) findViewById(R.id.date);
        in=(Button) findViewById(R.id.attendenceIn);
        out=(Button) findViewById(R.id.attendenceOut);
        map=(CircularImageView) findViewById(R.id.mapButton);
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txt.setText(currentdate);

        Thread t = new Thread() {
            @Override
            public void run() {
                try{
                    while (!isInterrupted()) {
                        Thread.sleep(300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView txt1 = (TextView) findViewById(R.id.date2);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = sdf.format(date);
                                txt1.setText(dateString);
                            }
                        });
                    }
                }catch(InterruptedException e){

                }
            }
        };
        t.start();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "map", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivityOut.this, MapAttendence.class);
                //  intent.putExtra("userID",userID);
                //  intent.putExtra("token",token);
                //  intent.putExtra("instance", instanceStr);
                //  intent.putExtra("lg",lg);
                //  intent.putExtra("bg",bg);
                startActivity(intent);

            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityOut.this, "innnnn", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivityOut.this, CheckIn.class);
                //  intent.putExtra("userID",userID);
                //  intent.putExtra("token",token);
                //  intent.putExtra("instance", instanceStr);
                //  intent.putExtra("lg",lg);
                //  intent.putExtra("bg",bg);
                startActivity(intent);
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityOut.this, "outtttttttt", Toast.LENGTH_SHORT).show();
            }
        });

        btn = (Button) findViewById(R.id.checkout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOTLDialog();
            }

        });

    }


    private void showOTLDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_otl_remarks);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText txt1 = (EditText) dialog.findViewById(R.id.et_post);

        // final AppCompatRatingBar rating_bar = (AppCompatRatingBar) dialog.findViewById(R.id.rating_bar);
        ((Button) dialog.findViewById(R.id.upload_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//           startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

//                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
              //  Intent i = new Intent(
                //        Intent.ACTION_PICK,
                  //      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                //startActivityForResult(i, RESULT_LOAD_IMAGE);

//                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Utils.REQCODE);

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();

            }

        });


        ((Button) dialog.findViewById(R.id.upload_signature)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = txt1.getText().toString().trim();
                if (review.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill review text", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent=new Intent(MainActivityOut.this, UploadSignature.class);
                  //  intent.putExtra("userID",userID);
                  //  intent.putExtra("token",token);
                  //  intent.putExtra("instance", instanceStr);
                  //  intent.putExtra("lg",lg);
                  //  intent.putExtra("bg",bg);
                    startActivity(intent);

//                    items.add("(" + rating_bar.getRating() + ") " + review);
//                    adapter.notifyDataSetChanged();
                }
//                if (!adapter.isEmpty()) {
//                    txt_no_item.setVisibility(View.GONE);
//                }
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         JSONObject jsonObject;
        jsonObject = new JSONObject();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_otl_remarks);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

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
         //   Toast.makeText(this, "name="+ImName, Toast.LENGTH_SHORT).show();
            // Utils.imageName=ImName;
            //    String s= getRealPathFromURI(selectedImageUri);
            //  File f = new File("" + selectedImageUri);
            //  Utils.imageName=f.getName();
            // imageview.setImageURI(selectedImageUri);
            try {
                Bitmap image1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                //   Bitmap image1 = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                dialog.show();
                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                image1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                String encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
                try {
                    jsonObject.put(Utils.imageName, ImName);
                //    Log.e("Image name", etxtUpload.getText().toString().trim());
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
                              //  messageText.setText("Image Uploaded Successfully");
                                Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Message from server:   ", volleyError.toString());
                      //  messageText.setText("Message from server:   " + volleyError.toString());
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
        }
    }
}


