package com.example.hospitalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hospitalapplication.databinding.ActionbarBinding;

public class ActionbarActivity extends AppCompatActivity {
    private ActionbarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActionbarBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}