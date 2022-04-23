package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DatabaseReference ref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.toolbardashboard.toolbar.setNavigationOnClickListener(view -> {
            binding.Drawer.openDrawer(Gravity.LEFT);
        });

        ref = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    Log.i("Myerror",snapshot.toString());

                    User user = childSnap.getValue(User.class);
                    View view = binding.drawer.getHeaderView(0);
                    TextView name =view.findViewById(R.id.nameTV);
                    TextView email = view.findViewById(R.id.emailTV);
                    ImageView imageView = view.findViewById(R.id.ivproflie);


                    if (user.uid.equals(userId)){

                        name.setText(user.name);

                        email.setText(user.email);

                        Glide.with(DashboardActivity.this)
                                .load(user.imageUrl).into(imageView);


                    }else {
                        Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.toolbardashboard.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.signout:
                        auth.signOut();
                        startActivity(new Intent(DashboardActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}

