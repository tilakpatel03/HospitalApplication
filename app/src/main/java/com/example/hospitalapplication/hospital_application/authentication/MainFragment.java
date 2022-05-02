package com.example.hospitalapplication.hospital_application.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
private FragmentMainBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btngologin.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_loginFragment);
        });

    }
}