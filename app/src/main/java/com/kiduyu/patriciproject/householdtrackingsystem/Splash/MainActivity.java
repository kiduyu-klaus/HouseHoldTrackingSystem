package com.kiduyu.patriciproject.householdtrackingsystem.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kiduyu.patriciproject.householdtrackingsystem.Account.LoginActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.R;

public class MainActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Glide.with(getApplicationContext()).load("https://i1.wp.com/kenyanwallstreet.com/wp-content/uploads/2019/11/Household-items.jpg")
                .into((ImageView) findViewById(R.id.image_splash));

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(200);
                        if (_active) {
                            waited += 200;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(MainActivity.this,
                            LoginActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();
    }

}
