package com.example.loaddata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_TIME_OUT =2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slpashscreen_animation);

        //Show Splash Screen
        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }, SPLASH_TIME_OUT);
}
}
