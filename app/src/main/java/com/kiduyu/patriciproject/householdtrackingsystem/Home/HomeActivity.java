package com.kiduyu.patriciproject.householdtrackingsystem.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiduyu.patriciproject.householdtrackingsystem.Account.LoginActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.ConsumablesFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.HistoryFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.HomeFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.ReportsFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.SharedPref.SharedPrefManager;
import com.kiduyu.patriciproject.householdtrackingsystem.StatusColor.StatusBar;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView textViewUsername, textViewUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        textViewUsername = (TextView) findViewById(R.id.user_name_hs);
        textViewUserEmail = (TextView) findViewById(R.id.user_id_hs);

        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());

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

                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                            new ReportsFragment()).commit();
                    return true;
                case  R.id.navigationdelivery:

                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
                    return true;
                case  R.id.navigationMenu:
                    return true;
            }



            return false;
        }

    };

    public void logout(View view) {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
