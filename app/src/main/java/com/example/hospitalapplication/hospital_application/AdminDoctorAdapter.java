package com.example.hospitalapplication.hospital_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalapplication.databinding.AdminDoctorListRvBinding;

import java.util.ArrayList;

public class AdminDoctorAdapter extends RecyclerView.Adapter<AdminDoctorAdapter.DoctorRowHolder> {

    ArrayList<Doctor> list;
    Context context;
    public AdminDoctorAdapter(ArrayList<Doctor> list){
        this.list=list;
    }



    @NonNull
    @Override
    public DoctorRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        AdminDoctorListRvBinding binding = AdminDoctorListRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DoctorRowHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorRowHolder holder, int position) {
        Doctor d = list.get(position);

        Glide.with(context).load(d.photoUrl).into(holder.binding.ivdocphoto);

        holder.binding.setObj(d);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DoctorRowHolder extends RecyclerView.ViewHolder {
        AdminDoctorListRvBinding binding;
        public DoctorRowHolder(@NonNull AdminDoctorListRvBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
