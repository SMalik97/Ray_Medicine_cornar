package com.example.ray_medicals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class orderby_Prescription extends AppCompatActivity {
    ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    ImageView cam;
    String url="https://raymedicinecorner.com/android_app/prescription_order.php";
    String url2="https://raymedicinecorner.com/android_app/user_details.php";
    ImageView pesimg;
    Button upload;
    String login_phone="Null";
    String p,s;
    List<List_Data_sl> list_data_sl;
    String login_status="No";
    EditText order_name,order_phone,order_address,order_description,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderby__prescription);

        list_data_sl=new ArrayList<>();


        order_name=(EditText)findViewById(R.id.order_name);
        order_phone=(EditText)findViewById(R.id.order_phone);
        order_address=(EditText)findViewById(R.id.order_address);
        order_description=(EditText)findViewById(R.id.order_description);
        pin=(EditText)findViewById(R.id.order_pin);

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);

        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        p=b.getString("loginPhone","No");

        SharedPreferences sta=getSharedPreferences(login_status, Context.MODE_PRIVATE);
        s=sta.getString("loginStatus","No");

        if (s.equals("No")){
            AlertDialog.Builder  dialog=new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle("Login");
            dialog.setMessage("To order medicine from our shop you have to login to your account");
            dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent l=new Intent(getApplicationContext(),Login.class);
                    startActivity(l);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.create().show();

        }


        cam=(ImageView)findViewById(R.id.cam);
        pesimg=(ImageView)findViewById(R.id.pesimg);
        upload=(Button)findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order_name.getText().toString().trim().equals("")){
                    order_name.setError("Please Enter Your Name");
                    order_name.requestFocus();
                }else if (order_phone.getText().toString().trim().equals("")){
                    order_phone.setError("Please Enter Your Phone Number");
                    order_phone.requestFocus();
                }else if (order_address.getText().toString().trim().equals("")){
                    order_address.setError("Please Enter Your Address");
                    order_address.requestFocus();
                }else if (pin.getText().toString().trim().equals("")){
                    pin.setError("Please Enter Your Pin Number");
                    pin.requestFocus();
                }else if (!pin.getText().toString().trim().equals("711101") && !pin.getText().toString().trim().equals("711102") && !pin.getText().toString().trim().equals("711103")){
                    showdialog();
                }else {
                    progressDialog.show();
                    imgupload iu=new imgupload();
                    new Thread(iu).start();
                }

            }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
            }
        });

    }
    //select image .........................................................
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Camera
        try {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
           pesimg.setImageBitmap(bitmap);
            // Uri uri = data.getData();
            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        }catch (Exception e){
            e.printStackTrace();
        }





        // Gallery

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                pesimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openCamera(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestCameraPermission();
        }else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
            startActivityForResult(intent, 1);
        }
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebytes= outputStream.toByteArray();
        //return Base64.encodeToString(imagebytes,Base64.DEFAULT);

        String encodedImage= Base64.encodeToString(imagebytes,Base64.DEFAULT);
        return  encodedImage;
    }

    class imgupload implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(orderby_Prescription.this, response+"", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                   }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(orderby_Prescription.this, "SomeError Occurred!", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params=new HashMap<>();

                    String imageData=imageToString(bitmap);
                    params.put("image", imageData);
                    params.put("phone",p);
                    params.put("name",order_name.getText().toString().trim());
                    params.put("phoneno",order_phone.getText().toString().trim());
                    params.put("address",order_address.getText().toString().trim());
                    params.put("pin",pin.getText().toString().trim());
                    params.put("description",order_description.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }
    }


    /*class details implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject ob = array.getJSONObject(i);
                            List_Data_sl listDatasl = new List_Data_sl(
                                    ob.getString("name"),
                                    ob.getString("address"),
                                    ob.getString("phone_no"));
                            list_data_sl.add(listDatasl);
                            order_name.setText(listDatasl.getNamef());
                            order_address.setText(listDatasl.getAddressf());
                            order_phone.setText(p);
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(orderby_Prescription.this, "e", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(orderby_Prescription.this, "e1", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("phone",p);

                    return params;
                }
            };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        }*/
    public void showdialog(){
        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Sorry! Medicine delivery service for your address is not available yet. It will be available shortly.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void showOptions(){
        new MaterialDialog.Builder(this)
                .title("Choose Image")
                .items(R.array.uploadImages)
                .itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                break;
                            case 1:
                                openCamera();
                                break;
                            case 2:
                                pesimg.setImageResource(R.drawable.ic_wallpaper_black_24dp);
                                break;
                        }
                    }
                })
                .show();

    }

    }





