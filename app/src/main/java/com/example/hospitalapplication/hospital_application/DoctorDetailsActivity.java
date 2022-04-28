package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivityDoctorDetailsBinding;
import com.example.hospitalapplication.databinding.BookAppointmentDailogBinding;
import com.example.hospitalapplication.databinding.TimeZoneSBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ActivityDoctorDetailsBinding binding;
    private BookAppointmentDailogBinding binding1;
    private TimeZoneSBinding binding2;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private StorageReference storeref;
    private Doctor d;
    private Dialog dialog2;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("appointment").child(d.uid);

        Intent intent =getIntent();
        d = (Doctor) intent.getSerializableExtra("doc");

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

            binding1.btntime.setOnClickListener(view1 -> {
                binding2 = TimeZoneSBinding.inflate(getLayoutInflater());

                binding2.timerg.setOnCheckedChangeListener((radioGroup, i1) -> {
                    String am10 = binding2.am10.getText().toString();
                    String am1030 = binding2.am1030.getText().toString();
                    String am1100 = binding2.am1100.getText().toString();
                    String am1130 = binding2.am1130.getText().toString();
                    String pm0130 = binding2.pm0130.getText().toString();
                    String pm0200 = binding2.pm0200.getText().toString();
                    String pm0230 = binding2.pm0230.getText().toString();
                    String pm0300 = binding2.pm0300.getText().toString();
                    String pm0330 = binding2.pm0330.getText().toString();
                    String pm0530 = binding2.pm0530.getText().toString();
                    String pm0600 = binding2.pm0600.getText().toString();
                    String pm0630 = binding2.pm0630.getText().toString();
                    String pm0700 = binding2.pm0700.getText().toString();

                    switch (radioGroup.getCheckedRadioButtonId()){
                        case R.id.am10:
                            binding1.btntime.setText(am10);
                            dialog2.dismiss();
                            break;
                        case R.id.am1030:
                            binding1.btntime.setText(am1030);
                            dialog2.dismiss();
                            break;
                        case R.id.am1100:
                            binding1.btntime.setText(am1100);
                            dialog2.dismiss();
                            break;
                        case R.id.am1130:
                            binding1.btntime.setText(am1130);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0130:
                            binding1.btntime.setText(pm0130);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0200:
                            binding1.btntime.setText(pm0200);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0230:
                            binding1.btntime.setText(pm0230);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0300:
                            binding1.btntime.setText(pm0300);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0330:
                            binding1.btntime.setText(pm0330);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0530:
                            binding1.btntime.setText(pm0530);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0600:
                            binding1.btntime.setText(pm0600);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0630:
                            binding1.btntime.setText(pm0630);
                            dialog2.dismiss();
                            break;
                        case R.id.pm0700:
                            binding1.btntime.setText(pm0700);
                            dialog2.dismiss();
                            break;
                    }
                });

                dialog2 = new Dialog(DoctorDetailsActivity.this,android.R.style.Theme_Light);
                dialog2.setContentView(binding2.getRoot());
                dialog2.show();

            });

            binding1.btndate.setOnClickListener(view1 -> {

                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(this,(datePicker, i, i1, i2) -> {

                    Log.i("date",i+" "+i1+" "+i2);
                    binding1.btndate.setText(i2+"/"+(i1+1)+"/"+i);

                },year,month,day);

                dialog.show();

            });

            binding1.btnbookadd.setOnClickListener(view1 -> {
                String patientname = binding1.patientname.getText().toString();
                String patientDiagnosis = binding1.patientDiagnosis.getText().toString();
                String diagnosisdescription = binding1.Diagnosisdescription.getText().toString();
                String time = binding1.btntime.getText().toString();
                String date = binding1.btndate.getText().toString();



            });
            Dialog dialog = new Dialog(DoctorDetailsActivity.this, android.R.style.Theme_Light);
            dialog.setContentView(binding1.getRoot());
            dialog.show();

        });

    }
}