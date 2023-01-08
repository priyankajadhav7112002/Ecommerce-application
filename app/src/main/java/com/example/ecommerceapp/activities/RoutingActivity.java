package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerceapp.R;

public class RoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        Intent intent = new Intent(RoutingActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
        setContentView(R.layout.activity_routing);
    }
}