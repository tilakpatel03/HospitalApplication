package com.example.hospitalapplication.hospital_application;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.example.hospitalapplication.databinding.EditHederBinding;
import com.example.hospitalapplication.hospital_application.authentication.UserActivity;
import com.example.hospitalapplication.hospital_application.userAppoinment.UserSeeAppointmentActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private EditHederBinding binding1;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private User user;
    private User user2;
    private View view1;
    private Uri uri = null;
    private Uri uri1 = null;
    private String userId;
    private StorageReference storageRef;
    private ActivityResultLauncher<String> gallery;
    private ArrayList<User> list;
    private ArrayList<String> keys;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Fetching User Data");
        pd.setMessage("Please Wait");
        pd.show();

        ref = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        list = new ArrayList<>();
        keys = new ArrayList<>();

        binding.toolbar.setNavigationOnClickListener(view -> {
            binding.Drawer.openDrawer(Gravity.LEFT);
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                keys.clear();
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    Log.i("Myerror",snapshot.toString());

                    user = childSnap.getValue(User.class);
                    key = childSnap.getKey();

                    View view = binding.drawer.getHeaderView(0);
                    TextView name =view.findViewById(R.id.nameTV);
                    TextView email = view.findViewById(R.id.emailTV);
                    ImageView imageView = view.findViewById(R.id.ivproflie);

                    list.add(user);
                    keys.add(key);

                    if (user.uid.equals(userId)){
                        user2 = user;
                        name.setText(user.name);
                        email.setText(user.email);
                        Glide.with(DashboardActivity.this)
                                .load(user.imageUrl).into(imageView);
                        pd.dismiss();
                    }

                    keys.add(key);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                    uri1 = result;
                    binding1.ivproflieuser.setImageURI(result);

            }
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
                case R.id.editpro:

                    binding1 = EditHederBinding.inflate(getLayoutInflater());

                    view1 = binding.drawer.getHeaderView(0);

                    TextView name = view1.findViewById(R.id.nameTV);
                    TextView email = view1.findViewById(R.id.emailTV);
                    String name1 = name.getText().toString();
                    String email1 =email.getText().toString();

                    binding1.etname.setText(name1);
                    binding1.etemail.setText(email1);
                    Glide.with(DashboardActivity.this)
                                .load(user2.getImageUrl()).into(binding1.ivproflieuser);

                    binding1.ivproflieuser.setOnClickListener(view3 -> {
                        gallery.launch("*/*");
                    });

                        new AlertDialog.Builder(DashboardActivity.this)
                                .setView(binding1.getRoot())
                                .setCancelable(false)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        String name = binding1.etname.getText().toString();
                                        String email = binding1.etemail.getText().toString();

//                                        User user1 = new User(name,email,userId,user2.imageUrl);

//                                        ref.child(key).setValue(user1);

                                        storageRef = FirebaseStorage.getInstance().getReference("images").child("userImages").child(user2.uid);

                                        storageRef.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        String UserId = uri.toString();

                                                        User user3 = new User(name,email,userId,UserId);

                                                        ref.push().setValue(user3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                ref.child(key).setValue(user3);
                                                                dialogInterface.dismiss();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.i("Myerror",e.toString());
                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.i("Myerror",e.toString());
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Myerror",e.toString());
                                            }
                                        });

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                    break;

                case R.id.setting:
                    Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
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