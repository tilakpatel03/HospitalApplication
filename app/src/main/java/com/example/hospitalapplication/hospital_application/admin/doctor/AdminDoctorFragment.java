package com.example.hospitalapplication.hospital_application.admin.doctor;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.AddDoctorDailogBinding;
import com.example.hospitalapplication.databinding.FragmentAdminDoctorBinding;
import com.example.hospitalapplication.databinding.UpdateDoctorDailogBinding;
import com.example.hospitalapplication.hospital_application.AdminDoctorAdapter;
import com.example.hospitalapplication.hospital_application.Doctor;
import com.example.hospitalapplication.hospital_application.DoctorDetailsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class AdminDoctorFragment extends Fragment {
    private FragmentAdminDoctorBinding binding;
    private @NonNull AddDoctorDailogBinding binding1;
    private UpdateDoctorDailogBinding binding2;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private StorageReference storageRef;
    private ActivityResultLauncher<String> gallery;
    private Uri uri = null;
    private Uri uri1 = null;
    private String uid;
    private String docId;
    private Dialog dialog;
    private ArrayList<Doctor> list;
    private ArrayList<String> keys;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDoctorBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Doctor");


        list = new ArrayList<>();
        keys = new ArrayList<>();

        ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_add_doctor_details,list);

        binding.listdoc.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                keys.clear();
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    Doctor d =  childSnap.getValue(Doctor.class);
                    String key = childSnap.getKey();

                    list.add(d);
                    keys.add(key);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                uri = result;
                binding1.ivproflieview1.setImageURI(result);

            }
        });


        binding.btnadddoc.setOnClickListener(view1 -> {

            binding1 = AddDoctorDailogBinding.inflate(getLayoutInflater());

            binding1.ivproflieview1.setOnClickListener(view3 -> {
                gallery.launch("*/*");
            });

            binding1.btndocadd.setOnClickListener(view2 -> {

                String name = binding1.name.getText().toString();
                String email = binding1.email.getText().toString().trim();
                String expertise = binding1.expertise.getText().toString();
                String address = binding1.address.getText().toString();
                String hour = binding1.worktime.getText().toString();
                String password = binding1.password.getText().toString().trim();
                String rpassword = binding1.confirmpassword.getText().toString().trim();

                Doctor d = new Doctor(name,email,docId,expertise,address,hour,uid);

                list.add(d);

                adapter.notifyDataSetChanged();

                if(!email.isEmpty() && !password.isEmpty() && !rpassword.isEmpty()){

                    if (password.equals(rpassword)){

                        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                uid = auth.getCurrentUser().getUid();

                                storageRef = FirebaseStorage.getInstance().getReference("images").child("DoctorImage").child(uid);

                                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                docId = uri.toString();

                                                Doctor doctor = new Doctor(name,email,docId,expertise,address,hour,uid);

                                                ref.push().setValue(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Myerror",e.toString());
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Fill the same password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding1.name.setError("Fill the name");
                    binding1.email.setError("Fill the gmail");
                    binding1.expertise.setError("Fill the expertise");
                    binding1.address.setError("Fill the address");
                    binding1.worktime.setError("Fill your worktime");
                }
            });
            dialog = new Dialog(getActivity(),android.R.style.Theme_Light);
            dialog.setContentView(binding1.getRoot());
            dialog.show();
            clear();
        });
        binding2 = UpdateDoctorDailogBinding.inflate(getLayoutInflater());

//        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                uri1 = result;
//                binding2.ivproflieview1.setImageURI(result);
//            }
//        });
//
//        binding2.ivproflieview1.setOnClickListener(view2 -> {
//            gallery.launch("image/*");
//        });

        binding.listdoc.setOnItemClickListener((adapterView, view1, i, l) -> {

            Doctor d = list.get(i);
            String key = keys.get(i);

            binding2.name.setText(d.getName());
            binding2.email.setText(d.getEmail());
            binding2.expertise.setText(d.getExpertise());
            binding2.address.setText(d.getAddress());
            binding2.worktime.setText(d.getHour());
            Glide.with(getActivity())
                    .load(d.getPhotoUrl()).into(binding2.ivproflieview1);

            new AlertDialog.Builder(getActivity())
                    .setTitle("Update doctor details?")
                    .setCancelable(false)
                    .setPositiveButton("Update",(dialogInterface, i1) -> {
                        new AlertDialog.Builder(getActivity())
                                .setCancelable(false)
                                .setView(binding2.getRoot())
                                .setPositiveButton("Update",(dialogInterface1, i2) ->{

                                    String name = binding2.name.getText().toString();
                                    String email = binding2.email.getText().toString().trim();
                                    String expertise = binding2.expertise.getText().toString();
                                    String address = binding2.address.getText().toString();
                                    String hour = binding2.worktime.getText().toString();

                                    Doctor doctor = new Doctor(name,email,d.getPhotoUrl(),expertise,address,hour,d.getUid());

                                    ref.child(key).setValue(doctor);

                                    adapter.notifyDataSetChanged();

//                                    storageRef = FirebaseStorage.getInstance().getReference("images").child("DoctorImage").child(uid);
//
//                                    storageRef.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//
//                                                    String docId = uri.toString();
//
//                                                    Doctor doctor = new Doctor(name,email,docId,expertise,address,hour,d.getUid());
//
//                                                    ref.push().setValue(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            ref.child(key).setValue(doctor);
//                                                            dialog.dismiss();
//                                                        }
//                                                    }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.i("Myerror",e.toString());
//                                                        }
//                                                    });
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.i("Myerror",e.toString());
//                                                }
//                                            });
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.i("Myerror",e.toString());
//                                        }
//                                    });

                                }).setNegativeButton("cancel",(dialogInterface1, i2) -> {
                            dialogInterface.dismiss();
                        }).create().show();

                    }).setNegativeButton("Delete",(dialogInterface, i1) -> {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Are you sure delete a doctor?")
                                .setCancelable(false)
                                .setPositiveButton("Delete",(dialogInterface1, i2) -> {
                                    Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                                    ref.child(key).removeValue();
                        }).setNegativeButton("Cancel",(dialogInterface1, i2) -> {
                    dialogInterface.dismiss();
                }).create().show();

            }).setNeutralButton("Cancel",(dialogInterface, i1) -> {
                dialogInterface.dismiss();
            }).create().show();
        });
    }
    void clear(){
        binding1.name.setText("");
        binding1.email.setText("");
        binding1.expertise.setText("");
        binding1.address.setText("");
        binding1.worktime.setText("");
        binding1.password.setText("");
        binding1.confirmpassword.setText("");
    }
}
