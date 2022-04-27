package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.databinding.ActivityDoctorDetailsBinding;
import com.example.hospitalapplication.databinding.BookAppointmentDailogBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ActivityDoctorDetailsBinding binding;
    private BookAppointmentDailogBinding binding1;
    private Doctor d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Intent i =getIntent();
        d = (Doctor) i.getSerializableExtra("doc");

        binding.tvnamedoc.setText(d.name);
        binding.tvexpdoc.setText(d.expertise);
        binding.tvemaildoc.setText(d.email);
        binding.tvadddoc.setText(d.address);
        binding.tvhourdoc.setText(d.hour);
        Glide.with(DoctorDetailsActivity.this)
                .load(d.photoUrl).into(binding.ivdocp);

        binding.btnbook.setOnClickListener(view -> {
            binding1 = BookAppointmentDailogBinding.inflate(getLayoutInflater());

            binding1.dornametv.setText(d.name);
            binding1.exptv.setText(d.expertise);
            Glide.with(DoctorDetailsActivity.this)
                    .load(d.photoUrl).into(binding1.ivproflieview1);


            binding1.btnbookadd.setOnClickListener(view1 -> {

            });
            Dialog dialog = new Dialog(DoctorDetailsActivity.this, android.R.style.Theme_Light);
            dialog.setContentView(binding1.getRoot());
            dialog.show();

        });

    }
}