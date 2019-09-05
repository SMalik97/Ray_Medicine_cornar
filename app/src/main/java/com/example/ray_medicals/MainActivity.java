package com.example.ray_medicals;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView logIn;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);














        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });







        Fragment fragment = new HomePage();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

       BottomNavigationView bottom_nav=(BottomNavigationView)findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_home:
                        Fragment fragment = new HomePage();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.commit();
                        break;
                    /*case R.id.bottom_offers:
                        Fragment fragment2 = new Offers_page();
                        FragmentManager manager2 = getSupportFragmentManager();
                        FragmentTransaction transaction2 = manager2.beginTransaction();
                        transaction2.replace(R.id.fragment_container, fragment2);
                        transaction2.commit();
                        break;*/
                    case R.id.bottom_you:
                        Fragment fragment3 = new YouPage();
                        FragmentManager manager3 = getSupportFragmentManager();
                        FragmentTransaction transaction3 = manager3.beginTransaction();
                        transaction3.replace(R.id.fragment_container, fragment3);
                        transaction3.commit();
                        break;
                    case R.id.bottom_services:
                        Fragment fragment4 = new Services_page();
                        FragmentManager manager4 = getSupportFragmentManager();
                        FragmentTransaction transaction4 = manager4.beginTransaction();
                        transaction4.replace(R.id.fragment_container, fragment4);
                        transaction4.commit();
                        break;
                    case R.id.bottom_orders:
                        Fragment fragment5 = new Order_page();
                        FragmentManager manager5 = getSupportFragmentManager();
                        FragmentTransaction transaction5 = manager5.beginTransaction();
                        transaction5.replace(R.id.fragment_container, fragment5);
                        transaction5.commit();
                        break;
                }
                return true;
            }
        });




        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),Login.class);
               startActivity(i);

            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order_medicine) {
            Fragment fragment5 = new Order_page();
            FragmentManager manager5 = getSupportFragmentManager();
            FragmentTransaction transaction5 = manager5.beginTransaction();
            transaction5.replace(R.id.fragment_container, fragment5);
            transaction5.commit();
        } else if (id == R.id.order_medicine) {
            Intent i=new Intent(getApplicationContext(),orderby_Prescription.class);
            startActivity(i);

        } else if (id == R.id.track_order) {
            Fragment fragment5 = new Order_page();
            FragmentManager manager5 = getSupportFragmentManager();
            FragmentTransaction transaction5 = manager5.beginTransaction();
            transaction5.replace(R.id.fragment_container, fragment5);
            transaction5.commit();
        } else if (id == R.id.lab_booking) {
            Intent i=new Intent(getApplicationContext(),doctors.class);
            i.putExtra("a","lab");
            startActivity(i);

        } else if (id == R.id.review_product) {
            Intent i=new Intent(getApplicationContext(),doctors.class);
            i.putExtra("a","hgghg");
            startActivity(i);

        } else if (id == R.id.rate_us) {

        }else if(id==R.id.request_product)
        {

        }else if(id==R.id.feedback)
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



