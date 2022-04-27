package com.example.hospitalapplication.hospital_application.doctor_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hospitalapplication.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity {
    private ActivityDoctorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}