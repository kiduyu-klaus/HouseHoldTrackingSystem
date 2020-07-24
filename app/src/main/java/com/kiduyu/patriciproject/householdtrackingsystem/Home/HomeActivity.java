package com.kiduyu.patriciproject.householdtrackingsystem.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.ConsumablesFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.HomeFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.R;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState== null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigationHome);}
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                            new HomeFragment()).commit();
                    return true;
                case R.id.navigationconsumable:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                            new ConsumablesFragment()).commit();
                    //MenuItem.se
                    return true;
                case R.id.navigationreports:
                    return true;
                case  R.id.navigationdelivery:
                    return true;
                case  R.id.navigationMenu:
                    return true;
            }



            return false;
        }

    };
}
