package com.example.hospitalapplication.hospital_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentAdminAppointmentBinding;

public class AdminAppointmentFragment extends Fragment {
    private FragmentAdminAppointmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminAppointmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}