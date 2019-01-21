package com.example.sammrabatool.solutions5d.OTL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sammrabatool.solutions5d.OTL.Utils;
import com.example.sammrabatool.solutions5d.OTL.SignatureUpload.UploadSignature;

import com.example.sammrabatool.solutions5d.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class OTLDialogActivity extends Activity {
    TextView txt,empNameText;
    private ProgressDialog progressDialog ;
    private JSONObject jsonObject;
    private static int counterImages=0;
    private static int picCounter=0;
    ImageView profileImage;
    EditText txt1;
    ImageView imageview1, imageview2,imageview3,imageview4,imageview5,imageview6,imageview7;
    Button upload_img, upload_signature, submit;
  //  public static Bitmap bm1,bm2,bm3,bm4,bm5,bm6,bm7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_otl_remarks);

        final double longitude = getIntent().getDoubleExtra("Longitude", 0);
        final double latitude = getIntent().getDoubleExtra("Latitude", 0);
        int attendence = getIntent().getIntExtra("Attendence", 0);
        final String time = getIntent().getStringExtra("Time");


        picCounter=0;
        counterImages=0;
        jsonObject = new JSONObject();
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
         imageview6 = (ImageView)findViewById(R.id.imageView_pic6);
         imageview7 = (ImageView)findViewById(R.id.imageView_pic7);

        imageview1.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        imageview3.setVisibility(View.GONE);
        imageview4.setVisibility(View.GONE);
        imageview5.setVisibility(View.GONE);
        imageview6.setVisibility(View.GONE);
        imageview7.setVisibility(View.GONE);

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

                    Intent intent=new Intent(OTLDialogActivity.this, UploadSignature.class);
                    //  intent.putExtra("userID",userID);
                    //  intent.putExtra("token",token);
                    //  intent.putExtra("instance", instanceStr);
                    //  intent.putExtra("lg",lg);
                    //  intent.putExtra("bg",bg);
                    startActivity(intent);


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
                for(int i=0;i<picCounter;i++)
                    imgArray.put(imageArray[i]);
                //   String jsonObjectString = new Gson().toJson(imageArray);



                try {
                    jsonObject.put(Utils.imageName, "upload"+counterImages++);
                    //   Log.e("Image name", etxtUpload.getText().toString().trim());
                    jsonObject.put("picCounter", picCounter);
                    //  jsonObject.put(Utils.image, tmp);
                    jsonObject.put(Utils.image, imgArray);


                    //  messageText.setText(encodedImage1);
                    Log.e("Images", imgArray.toString());


                    Log.e("JSON", jsonObject.toString());

                    Toast.makeText(OTLDialogActivity.this, "pic counter="+picCounter, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                }
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, Utils.urlUpload, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Message from server", jsonObject.toString());
                                progressDialog.dismiss();
                                //     messageText.setText("Image Uploaded Successfully");
                                Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Message from server:   ", volleyError.toString());
                        //  messageText.setText("Message from server:   "+ volleyError.toString());
                        progressDialog.dismiss();
                    }
                });
                jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(OTLDialogActivity.this).add(jsonObjectRequest1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        picCounter++;
        if(picCounter==1)
            imageview1.setVisibility(View.VISIBLE);
        else if(picCounter==2)
            imageview2.setVisibility(View.VISIBLE);
        else if(picCounter==3)
            imageview3.setVisibility(View.VISIBLE);
        else if(picCounter==4)
            imageview4.setVisibility(View.VISIBLE);
        else if(picCounter==5)
            imageview5.setVisibility(View.VISIBLE);
        else if(picCounter==6)
            imageview6.setVisibility(View.VISIBLE);
        else if(picCounter==7)
            imageview7.setVisibility(View.VISIBLE);

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
            Toast.makeText(this, "name="+ImName, Toast.LENGTH_SHORT).show();
            // Utils.imageName=ImName;
            //    String s= getRealPathFromURI(selectedImageUri);
            //  File f = new File("" + selectedImageUri);
            //  Utils.imageName=f.getName();
            if(counterImages==0)
            {   imageview1.setImageURI(selectedImageUri);
                counterImages++;
            }

            else if(counterImages==1)
            {   imageview2.setImageURI(selectedImageUri);
                counterImages++;
            }
            else if(counterImages==2)
            {   imageview3.setImageURI(selectedImageUri);
                counterImages++;
            }
            else if(counterImages==3)
            {   imageview4.setImageURI(selectedImageUri);
                counterImages++;
            }
            else if(counterImages==4)
            {   imageview5.setImageURI(selectedImageUri);
                counterImages++;
            }
            else if(counterImages==5)
            {   imageview6.setImageURI(selectedImageUri);
                counterImages++;
            }
            else if(counterImages==6)
            {   imageview7.setImageURI(selectedImageUri);
                counterImages++;
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
    }
}
