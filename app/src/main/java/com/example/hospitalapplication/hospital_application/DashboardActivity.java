package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.example.hospitalapplication.hospital_application.userAppoinment.UserSeeAppointmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Fetching User Data");
        pd.setMessage("Please Wait");
        pd.show();

        ref = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();

        binding.toolbar.setNavigationOnClickListener(view -> {
            binding.Drawer.openDrawer(Gravity.LEFT);
        });
        binding.drawer.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Appointments:
                    Intent i = new Intent(DashboardActivity.this, UserSeeAppointmentActivity.class);
                    startActivity(i);
                    break;
                case R.id.getappointments:
                    Toast.makeText(this, "getappointments", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.doctor:
                    Intent i1 = new Intent(DashboardActivity.this,SeeDocListActivity.class);
                    startActivity(i1);
                    break;
                case R.id.testreport:
                    Toast.makeText(this, "testreport", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.help:
                    Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting:
                    Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });

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

                        pd.dismiss();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.signout:
                        auth.signOut();
                        startActivity(new Intent(DashboardActivity.this, UserActivity.class));
                        break;
                }
                return false;
            }
        });
        binding.doctorcard.setOnClickListener(view -> {
            Intent i = new Intent(this,SeeDocListActivity.class);
            startActivity(i);
        });
    }
}

