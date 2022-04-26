package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivitySeeDocListBinding;
import com.example.hospitalapplication.databinding.DoctorListRvBinding;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeDocListActivity extends AppCompatActivity {
    private ActivitySeeDocListBinding binding;
    private DatabaseReference ref;
    private ArrayList<Doctor> list;
    private ArrayList<String> keyList;
    private FirebaseAuth auth;
    private DoctorListRvBinding binding1;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeDocListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Fetching Doctor Data");
        pd.setMessage("Please Wait");
        pd.show();

        auth = FirebaseAuth.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("Doctor");

        binding.rvdoc.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        keyList = new ArrayList<>();

        DoctorAdapter adapter = new DoctorAdapter(list);

        binding.rvdoc.setAdapter(adapter);

        binding1=DoctorListRvBinding.inflate(getLayoutInflater());
        binding1.getRoot();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                keyList.clear();

                for (DataSnapshot childSnap :snapshot.getChildren()){

                    Doctor d = childSnap.getValue(Doctor.class);
                    String key = childSnap.getKey();

                    list.add(d);
                    keyList.add(key);

                    Glide.with(SeeDocListActivity.this).load(d.photoUrl).into(binding1.ivdocphoto);

                    pd.dismiss();
                }
                adapter.notifyDataSetChanged();
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
                        startActivity(new Intent(SeeDocListActivity.this, UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}