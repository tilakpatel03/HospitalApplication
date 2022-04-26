package com.example.hospitalapplication.hospital_application.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentSignupBinding;
import com.example.hospitalapplication.hospital_application.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private StorageReference storeref;
    private ActivityResultLauncher<String> gallery;
    private Uri uri = null;
    private String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tblohin.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).popBackStack(R.id.loginFragment,false);
        });

            auth = FirebaseAuth.getInstance();
            ref = FirebaseDatabase.getInstance().getReference("User");

            gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    uri = result;
                    binding.ivproflieview.setImageURI(result);
                }
            });
            binding.ivp.setOnClickListener(view2 -> {
                gallery.launch("image/*");
            });
            binding.btnsignup.setOnClickListener(view2 -> {
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                String confirmpassword = binding.confirmpassword.getText().toString().trim();


                if(!email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){

                    if (password.equals(confirmpassword)){

                        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                uid = auth.getCurrentUser().getUid();

                                storeref = FirebaseStorage.getInstance().getReference("images").child("userImages").child(uid);

                                storeref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storeref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String userId = uri.toString();

                                                User user = new User(name,email,uid,userId);

                                                ref.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Navigation.findNavController(getView()).popBackStack();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.i("Myerrors",e.toString());
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Myerrors",e.toString());
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Add Profile Photo", Toast.LENGTH_SHORT).show();
                                        Log.i("Myerrors",e.toString());
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Myerrors",e.toString());
                            }
                        });

                    }else {
                        Toast.makeText(getActivity(), "Fill the same password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.name.setError("Fill the name");
                    binding.email.setError("Fill the gmail");
                }
                clear();
            });

    }
    void clear(){
        binding.name.setText("");
        binding.email.setText("");
        binding.password.setText("");
        binding.confirmpassword.setText("");
    }
}