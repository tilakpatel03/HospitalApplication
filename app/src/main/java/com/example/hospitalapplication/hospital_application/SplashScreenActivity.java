package com.example.hospitalapplication.hospital_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hospitalapplication.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(500);
                }catch (Exception e){

                }
                Intent i = new Intent(SplashScreenActivity.this,UserActivity.class);
                startActivity(i);
                finish();
            }
        }).start();
    }
}