package com.example.hospitalapplication.hospital_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.ActivitySeeDocListBinding;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class SeeDocListActivity extends AppCompatActivity implements DoctorUserAdapter.OnAppointmentClickInterface {
    private ActivitySeeDocListBinding binding;
    private DatabaseReference ref;
    private ArrayList<Doctor> list;
    private ArrayList<String> keyList;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private View image;

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

        DoctorUserAdapter adapter = new DoctorUserAdapter(list,this);

        binding.rvdoc.setAdapter(adapter);



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

    @Override
    public void onAppointmentClick(int position) {
        Doctor d = list.get(position);
        Intent i = new Intent(SeeDocListActivity.this,DoctorDetailsActivity.class);
        i.putExtra("doc",d);
        startActivity(i);
    }
}