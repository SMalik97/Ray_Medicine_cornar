package com.example.ray_medicals;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText mobno;
    Button btn;
    String login_status="No";
    String login_phone="Null";
    String url="https://raymedicinecorner.com/android_app/check_ph.php";
    List<List_Data_cp> list_data_cp;
    boolean f;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mobno=(EditText)findViewById(R.id.mobno);
        btn=(Button)findViewById(R.id.btn);


//initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        list_data_cp=new ArrayList<>();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobno.getText().toString().trim().equals("")){
                mobno.setError("Please enter your mobile number");
                }else if (mobno.getText().toString().length()!=10){
                    mobno.setError("Please enter a valid mobile number");
                }else {
                    progressDialog.show();
                    check_ph cp=new check_ph();
                    new Thread(cp).start();

                }
            }
        });

        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
        String p=b.getString("loginPhone","No");

        SharedPreferences a=getSharedPreferences(login_status, Context.MODE_PRIVATE);
        String v=a.getString("loginStatus","No");
        if (v.equals("Yes")){
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            i.putExtra("ph",p);
            finish();
        }


    }

    class check_ph implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray array=jsonObject.getJSONArray("data");
                        for (int i=0; i<array.length(); i++ ){
                            JSONObject ob=array.getJSONObject(i);
                            final List_Data_cp listData=new List_Data_cp(ob.getString("phone_no"));
                            list_data_cp.add(listData);
                            if (mobno.getText().toString().trim().equals(listData.getPhone())){
                                f=true;
                            }else {
                                f=false;
                            }
                        }
                        if (f){
                            progressDialog.dismiss();
                            SharedPreferences sp = getSharedPreferences(login_status, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("loginStatus", "Yes");
                            editor.apply();

                            SharedPreferences sp2 = getSharedPreferences(login_phone, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sp2.edit();
                            editor2.putString("loginPhone", mobno.getText().toString().trim());
                            editor2.apply();

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            finish();


                        }else{
                            progressDialog.dismiss();
                            showdialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Some error occurred!",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("phone",mobno.getText().toString().trim());
                    return params;

                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void showdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("This number is not registered!");
        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(),OTP_Verification.class);
                intent.putExtra("ph",mobno.getText().toString().trim());
                startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }



}
