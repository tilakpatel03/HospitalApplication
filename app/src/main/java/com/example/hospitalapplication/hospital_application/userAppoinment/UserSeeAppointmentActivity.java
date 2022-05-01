package com.example.hospitalapplication.hospital_application.userAppoinment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivityUserSeeAppointmentBinding;
import com.example.hospitalapplication.hospital_application.DashboardActivity;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.example.hospitalapplication.hospital_application.doctor_activity.DoctorActivity;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class UserSeeAppointmentActivity extends AppCompatActivity {
    private ActivityUserSeeAppointmentBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSeeAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        binding.bnvuser.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.userPending:
                        Navigation.findNavController(UserSeeAppointmentActivity.this,R.id.fcvuser).navigate(R.id.userPendingAppoinmentFragment);
                        break;

                    case R.id.userAccept:
                        Navigation.findNavController(UserSeeAppointmentActivity.this,R.id.fcvuser).navigate(R.id.userAcceptAppoimtmentFragment);
                        break;

                    case R.id.userReject:
                        Navigation.findNavController(UserSeeAppointmentActivity.this,R.id.fcvuser).navigate(R.id.userRejectAppoinmentFragment);
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
                        startActivity(new Intent(UserSeeAppointmentActivity.this, UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (checkCurrentFragment("fragment_user_pending_appointment")){
            Intent i = new Intent(UserSeeAppointmentActivity.this,DashboardActivity.class);
            startActivity(i);
        }else if (checkCurrentFragment("fragment_user_accept_appointment")){
            Navigation.findNavController(this,R.id.fcvuser).navigate(R.id.userPendingAppoinmentFragment);
        }else if (checkCurrentFragment("fragment_user_reject_appointment")) {
            Navigation.findNavController(this, R.id.fcvuser).navigate(R.id.userPendingAppoinmentFragment);
        }
    }
    Boolean checkCurrentFragment(String name){
        return Navigation.findNavController(this,R.id.fcvuser).getCurrentDestination().getLabel().toString().equals(name);
    }

}