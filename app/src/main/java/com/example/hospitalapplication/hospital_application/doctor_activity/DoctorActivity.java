package com.example.hospitalapplication.hospital_application.doctor_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivityDoctorBinding;
import com.example.hospitalapplication.hospital_application.Appointment;
import com.example.hospitalapplication.hospital_application.DashboardActivity;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.example.hospitalapplication.hospital_application.userAppoinment.UserSeeAppointmentActivity;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    private ActivityDoctorBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();;

        auth =FirebaseAuth.getInstance();

        binding.bnvdoctor.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.docPending:
                        Navigation.findNavController(DoctorActivity.this,R.id.fcvdoctor).navigate(R.id.doctorAllAppointmentFragment);
                        break;
                    case R.id.docAccept:
                        Navigation.findNavController(DoctorActivity.this,R.id.fcvdoctor).navigate(R.id.doctorAcceptAppointmentFragment);
                        break;
                    case R.id.docReject:
                        Navigation.findNavController(DoctorActivity.this,R.id.fcvdoctor).navigate(R.id.doctorRejectAppointmentFragment);
                        break;
                }
                return false;
            }
        });
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.signout:
                        auth.signOut();
                        startActivity(new Intent(DoctorActivity.this, UserActivity.class));
                        break;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (checkCurrentFragment("fragment_doctor_all_appointment")){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }else if (checkCurrentFragment("fragment_doctor_accept_appointment")){
            Navigation.findNavController(this,R.id.fcvdoctor).navigate(R.id.doctorAllAppointmentFragment);
        }else if (checkCurrentFragment("fragment_doctor_reject_appointment")) {
            Navigation.findNavController(this, R.id.fcvdoctor).navigate(R.id.doctorAllAppointmentFragment);
        }
    }
    Boolean checkCurrentFragment(String name){
        return Navigation.findNavController(this,R.id.fcvdoctor).getCurrentDestination().getLabel().toString().equals(name);
    }
}