package com.example.hospitalapplication.hospital_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentAdminDoctorBinding;

public class AdminDoctorFragment extends Fragment {
    private FragmentAdminDoctorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDoctorBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}