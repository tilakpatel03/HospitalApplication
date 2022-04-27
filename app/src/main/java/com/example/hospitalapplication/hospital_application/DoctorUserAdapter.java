package com.example.hospitalapplication.hospital_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.databinding.DoctorListRvUserBinding;

import java.util.ArrayList;

public class DoctorUserAdapter extends RecyclerView.Adapter<DoctorUserAdapter.DocViweHolder> {

    ArrayList<Doctor>list;
    Context context;
    OnAppointmentClickInterface clickInterface;
    DoctorUserAdapter(ArrayList<Doctor> list, OnAppointmentClickInterface clickInterface) {
        this.list=list;
        this.clickInterface = clickInterface;
    }

    interface OnAppointmentClickInterface{

        void onAppointmentClick(int position);

    }

    @NonNull
    @Override
    public DocViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        DoctorListRvUserBinding binding = DoctorListRvUserBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DocViweHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocViweHolder holder, int position) {

        Doctor d = list.get(position);

        Glide.with(context).load(d.photoUrl).into(holder.binding.ivdocphoto);

        holder.binding.setObj(d);

        holder.binding.btnc.setOnClickListener(view -> {

            clickInterface.onAppointmentClick(position);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DocViweHolder extends RecyclerView.ViewHolder {
        DoctorListRvUserBinding binding;
        public DocViweHolder(@NonNull DoctorListRvUserBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
