package com.example.ray_medicals;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.DOWNLOAD_SERVICE;

public class YouPage extends Fragment {
    View view;
//    String login_status="No";
//    String login_phone="Null";
//    TextView phno,showaddress,showname;
//    Button save,add;
//    CircleImageView editbutton,savebutton;
//    String address;
//    EditText editaddress,editname;
//    String url="https://raymedicinecorner.com/android_app/user_details.php";
//    List<List_Data_sl> list_data_sl;
//    String p;
//    Button logout;

    static WebView mWebView;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    SwipeRefreshLayout swipe;

    String url="https://raymedicinecorner.com/my-account/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.bottom_nav_you,container,false);
        //requested for storage permission
        if (Build.VERSION.SDK_INT >= 23) {
            runtimePermission();
        }

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipe.setRefreshing(true);


        //swipe refresh...........
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mWebView.getUrl());
                swipe.setRefreshing(true);
            }
        });

        mWebView = (WebView) view.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.loadUrl(url);//https://atnbanglagroup.com/atnkunmadmunmedicare


        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
//------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
//------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_LONG).show();
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override

            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override

            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                swipe.setRefreshing(false);

            }

            //to make call
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("mailto:")) {
                    Intent f = new Intent(Intent.ACTION_SEND);
                    f.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@atnmedicare.com"});
                    f.putExtra(Intent.EXTRA_SUBJECT, "Feedback from ATN Medicare");
                    f.putExtra(Intent.EXTRA_TEXT, "Message: ");
                    f.setType("message/email");
                    f.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(f, "Send Feedback"));
                    return true;
                }
                return false;
            }

        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            // For 3.0+ Devices (Start)

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
            }

            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            @Override

            public void onReceivedTitle(WebView view, String title) {

                super.onReceivedTitle(view, title);
                //getSupportActionBar().setTitle(title);


            }


            @Override

            public void onReceivedIcon(WebView view, Bitmap icon) {

                super.onReceivedIcon(view, icon);


            }


            // For Lollipop 5.0+ Devices
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e) {
                    uploadMessage = null;
                    Toast.makeText(getContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });


//        SharedPreferences b=getActivity().getSharedPreferences(login_phone, Context.MODE_PRIVATE);
//        p=b.getString("loginPhone","No");
//
//        SharedPreferences a=getActivity().getSharedPreferences(login_status, Context.MODE_PRIVATE);
//        String v=a.getString("loginStatus","No");
//
//        list_data_sl=new ArrayList<>();
//
//        phno=(TextView)view.findViewById(R.id.phno);
//        save=(Button)view.findViewById(R.id.save);
//        add=(Button)view.findViewById(R.id.add);
//        save=(Button)view.findViewById(R.id.save);
//        savebutton=(CircleImageView) view.findViewById(R.id.savebutton);
//        editbutton=(CircleImageView)view.findViewById(R.id.editbutton);
//        editaddress=(EditText)view.findViewById(R.id.editaddress);
//        showaddress=(TextView) view.findViewById(R.id.showaddress);
//        showname=(TextView)view.findViewById(R.id.showname);
//        editname=(EditText)view.findViewById(R.id.editname);
//        logout=(Button)view.findViewById(R.id.logout);
//
//
//        save.setVisibility(View.INVISIBLE);
//        editaddress.setVisibility(View.INVISIBLE);
//
//        editname.setVisibility(View.INVISIBLE);
//       savebutton.setVisibility(View.INVISIBLE);
//
//       profile p1=new profile();
//       new Thread(p1).start();
//
//
//        if (v.equals("Yes")){
//            phno.setText("Phone Number: "+p);
//        }
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                save.setVisibility(View.VISIBLE);
//                editaddress.setVisibility(View.VISIBLE);
//                add.setVisibility(View.INVISIBLE);
//                showaddress.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                address=editaddress.getText().toString().trim();
//                if (!address.equals("")){
//
//                }else {
//                    save.setVisibility(View.INVISIBLE);
//                    editaddress.setVisibility(View.INVISIBLE);
//                    add.setVisibility(View.VISIBLE);
//                    showaddress.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        editbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showname.setVisibility(View.INVISIBLE);
//                editbutton.setVisibility(View.INVISIBLE);
//                editname.setVisibility(View.VISIBLE);
//                savebutton.setVisibility(View.VISIBLE);
//            }
//        });
//
//        savebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showname.setVisibility(View.VISIBLE);
//                editbutton.setVisibility(View.VISIBLE);
//                editname.setVisibility(View.INVISIBLE);
//                savebutton.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp2 = getActivity().getSharedPreferences(login_phone, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor2 = sp2.edit();
//                editor2.putString("loginPhone", "No");
//                editor2.apply();
//
//                SharedPreferences sp = getActivity().getSharedPreferences(login_status, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("loginStatus", "No");
//                editor.apply();
//
//                Intent i=new Intent(getContext(),Login.class);
//                startActivity(i);
//                getActivity().finish();
//            }
//        });



        return view;
    }

//    class profile implements Runnable{
//        @Override
//        public void run() {
//            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(final String response) {
//                    try {
//                        JSONObject jsonObject=new JSONObject(response);
//                        JSONArray array=jsonObject.getJSONArray("data");
//                        for (int i=0; i<array.length(); i++ ){
//                            JSONObject ob=array.getJSONObject(i);
//                            final List_Data_sl listData=new List_Data_sl(ob.getString("name"),ob.getString("address"),ob.getString("pin"));
//                            list_data_sl.add(listData);
//                            showaddress.setText(listData.getAddressf());
//                            showname.setText(listData.getNamef());
//
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(getContext(),"Some error occurred!",Toast.LENGTH_LONG).show();
//                }
//            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> params=new HashMap<String, String>();
//                    params.put("phone",p);  //name should be same as database
//                    return params;
//
//                }
//            };
//            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
//            requestQueue.add(stringRequest);
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
// Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
// Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(getContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();

    }


//    @Override
//
//    public void onBackPressed() {
//
//        if (mWebView.canGoBack()) {
//
//            mWebView.goBack();
//
//        } else {
//
//            getActivity().finish();
//        }
//
//    }

    //method for runtime permission
    public void runtimePermission() {
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }

        }).check();
    }



}


