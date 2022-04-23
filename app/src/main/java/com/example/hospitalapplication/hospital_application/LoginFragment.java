package com.example.hospitalapplication.hospital_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getActivity(),DashboardActivity.class));
            getActivity().finish();
        }

        binding.btnsignup.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_signupFragment);
        });
        binding.btnforgotpassword.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        });
        auth =FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        binding.btnlogin.setOnClickListener(view1 -> {

            String email = binding.ETlogin.getText().toString().trim();
            String password = binding.ETsignup.getText().toString().trim();

            if(email.equals("t@gmail.com") && password.equals("123456")) {


                Intent i = new Intent(getActivity(), AdminActivity.class);
                startActivity(i);

            }

            if (!email.isEmpty() && !password.isEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_dashboardActivity);
                        getActivity().finish();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }else{
                binding.ETlogin.setError("Fill the gmail");
            }
            clear();
        });

    }
    void clear(){
        binding.ETlogin.setText("");
        binding.ETsignup.setText("");
    }
}