package com.example.hospitalapplication.hospital_application.doctor_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentDoctorAllAppointmentBinding;
import com.example.hospitalapplication.hospital_application.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorAllAppointmentFragment extends Fragment {
    private FragmentDoctorAllAppointmentBinding binding;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ArrayList<Appointment> list;
    private ArrayList<String> keys;
    private ArrayAdapter<Appointment> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorAllAppointmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_add_doctor_details,list);

        binding.listViewAppointment.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("appointment");

        Log.i("doctorUID",auth.getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                keys.clear();
                for(DataSnapshot childSnap : snapshot.getChildren()){

                    Appointment appointment = childSnap.getValue(Appointment.class);

//                    if(auth.getCurrentUser().getUid().equals(appointment.getDoctorID()) && appointment.getStatus().equals("request")){
//
//                        list.add(appointment);
//
//                    }
                    if(appointment.getDoctorID().equals(auth.getCurrentUser().getUid())){
                        list.add(appointment);
                    }

//                    if(appointment.getDoctorID().equals(auth.getCurrentUser().getUid())){
//                        list.add(appointment);
//                    }
                    keys.add(childSnap.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.listViewAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Appointment appointment = list.get(position);
                String key = keys.get(position);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Please Choose One")
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                appointment.setStatus("approved");

                                ref.child(key).setValue(appointment);

                                adapter.notifyDataSetChanged();
                            }
                        }).setNeutralButton("Denied", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        appointment.setStatus("rejected");

                        ref.child(key).setValue(appointment);

                        adapter.notifyDataSetChanged();

                    }
                }).create().show();

            }
        });
    }
}