//package com.example.hospitalapplication.hospital_application;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.example.hospitalapplication.R;
//import com.example.hospitalapplication.databinding.ActivityUserSeeAppointmentBinding;
//import com.google.android.material.navigation.NavigationBarView;
//
//public class UserSeeAppointmentActivityOld extends AppCompatActivity {
//    private ActivityUserSeeAppointmentBinding binding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityUserSeeAppointmentBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
////
//        binding.bnvuser.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.userPending:
//                        Navigation.findNavController(UserSeeAppointmentActivityOld.this,R.id.fcv).navigate(R.id.userPandingAppoinmentFragment);
//                        break;
//
//                    case R.id.userAccept:
//                        Navigation.findNavController(UserSeeAppointmentActivityOld.this,R.id.fcv).navigate(R.id.userAcceptAppoimtmentFragment);
//                        break;
//
//                    case R.id.userReject:
//                        Navigation.findNavController(UserSeeAppointmentActivityOld.this,R.id.fcv).navigate(R.id.userRejectAppoinmentFragment);
//                        break;
//                }
//
//                return true;
//            }
//        });
//
//    }
//    @Override
//    public void onBackPressed() {
//        if (checkCurrentFragment("fragment_user_pending_appointment")){
//            Intent i = new Intent(Intent.ACTION_MAIN);
//            i.addCategory(Intent.CATEGORY_HOME);
//            startActivity(i);
//        }else if (checkCurrentFragment("fragment_user_accept_appointment")){
//            Navigation.findNavController(this,R.id.fcv).navigate(R.id.userPandingAppoinmentFragment);
//        }else if (checkCurrentFragment("fragment_user_reject_appointment")) {
//            Navigation.findNavController(this, R.id.fcv).navigate(R.id.userPandingAppoinmentFragment);
//        }
//    }
//
//
//    Boolean checkCurrentFragment(String name){
//        return Navigation.findNavController(this,R.id.fcv).getCurrentDestination().getLabel().toString().equals(name);
//    }
//}