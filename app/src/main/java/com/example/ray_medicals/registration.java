package com.example.ray_medicals;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText name,address,pin;
    Button finish;
    String phone;
    String url="https://raymedicinecorner.com/android_app/register.php";
    ProgressDialog progressDialog;
    String login_status="No";
    String login_phone="Null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        pin=(EditText)findViewById(R.id.pin);
        finish=(Button)findViewById(R.id.finish);

        //initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);


        phone=getIntent().getExtras().getString("phone");


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")){
                    name.setError("Please enter your name");
                    name.requestFocus();
                }else if (address.getText().toString().trim().equals("")){
                    address.setError("Please enter your address");
                    address.requestFocus();
                }else if (pin.getText().toString().trim().equals("")){
                    pin.setError("Please enter your pin number");
                    pin.requestFocus();
                }else if (!pin.getText().toString().trim().equals("711101") && !pin.getText().toString().trim().equals("711102") && !pin.getText().toString().trim().equals("711103")){
                    showdialog();
                }else {
                    progressDialog.show();
                    reg r=new reg();
                    new Thread(r).start();

                }
            }
        });

    }
    public void showdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Sorry! Medicine delivery service for your address is not available yet. It will available shortly.");
        builder.setPositiveButton("Register Anyway", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.show();
                reg r=new reg();
                new Thread(r).start();
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        builder.create().show();
    }

    class reg implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            SharedPreferences sp = getSharedPreferences(login_status, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("loginStatus", "Yes");
                            editor.apply();

                            SharedPreferences sp2 = getSharedPreferences(login_phone, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sp2.edit();
                            editor2.putString("loginPhone", phone);
                            editor2.apply();

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            i.putExtra("phone",phone);
                            startActivity(i);
                            finish();
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Some error occurred!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("name",name.getText().toString().trim());
                    params.put("address",address.getText().toString().trim());
                    params.put("pin",pin.getText().toString().trim());
                    params.put("phone",phone);

                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }
    }
}
