package com.example.hospitalapplication.hospital_application.doctor_activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hospitalapplication.R;
import com.example.hospitalapplication.databinding.FragmentDoctorRejectAppointmentBinding;
import com.example.hospitalapplication.hospital_application.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorRejectAppointmentFragment extends Fragment {
    private FragmentDoctorRejectAppointmentBinding binding;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ArrayList<Appointment> list;
    private ArrayList<String> keys;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorRejectAppointmentBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        keys = new ArrayList<>();

        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_add_doctor_details,list);


        binding.listForRejAppointment.setAdapter(adapter);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("appointment");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                keys.clear();
                for(DataSnapshot childSnap : snapshot.getChildren()){

                    Appointment appointment = childSnap.getValue(Appointment.class);

                    if(auth.getCurrentUser().getUid().equals(appointment.getDoctorID()) && appointment.getStatus().equals("rejected")){

                        list.add(appointment);

                    }
                    keys.add(childSnap.getKey());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // log lelo
            }
        });
        binding.listForRejAppointment.setOnItemClickListener((adapterView, view1, i, l) -> {

            String key = keys.get(i);

            new AlertDialog.Builder(getActivity())
                    .setTitle("Delete this appointment ?")
                    .setCancelable(false)
                    .setPositiveButton("Delete",(dialogInterface, i1) -> {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Sure you delete this appointment?")
                                .setCancelable(false)
                                .setPositiveButton("Delete",(dialogInterface1, i2) ->{
                                    Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                                    ref.child(key).removeValue();
                                }).setNegativeButton("cancel",(dialogInterface1, i2) -> {
                            dialogInterface.dismiss();
                        }).create().show();
                    }).setNegativeButton("cancel",(dialogInterface, i1) -> {
                dialogInterface.dismiss();
            }).create().show();
        });

    }
}