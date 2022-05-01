package com.example.hospitalapplication.hospital_application.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivityAdminBinding;
import com.example.hospitalapplication.hospital_application.DashboardActivity;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.doctor01:
                        Navigation.findNavController(AdminActivity.this,R.id.fcv).navigate(R.id.adminDoctorFragment);
                        break;

                    case R.id.Appointments01:
                        Navigation.findNavController(AdminActivity.this,R.id.fcv).navigate(R.id.adminAppointmentFragment);
                        break;

                    case R.id.testre:
                        Navigation.findNavController(AdminActivity.this,R.id.fcv).navigate(R.id.adminTestrpFragment);
                        break;
                }

                return true;
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.signout:
                        auth.signOut();
                        startActivity(new Intent(AdminActivity.this, UserActivity.class));
                        break;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (checkCurrentFragment("fragment_admin_doctor")){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }else if (checkCurrentFragment("fragment_admin_appointment")){
            Navigation.findNavController(this,R.id.fcv).navigate(R.id.adminDoctorFragment);
        }else if (checkCurrentFragment("fragment_admin_testrp")) {
            Navigation.findNavController(this, R.id.fcv).navigate(R.id.adminDoctorFragment);
        }
    }
    Boolean checkCurrentFragment(String name){
        return Navigation.findNavController(this,R.id.fcv).getCurrentDestination().getLabel().toString().equals(name);
    }

}