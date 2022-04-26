package com.example.hospitalapplication.hospital_application;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalapplication.databinding.DoctorListRvBinding;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocViweHolder> {

    ArrayList<Doctor>list;

    DoctorAdapter(ArrayList<Doctor> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public DocViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DoctorListRvBinding binding = DoctorListRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DocViweHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocViweHolder holder, int position) {

        Doctor d = list.get(position);

        holder.binding.setObj(d);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DocViweHolder extends RecyclerView.ViewHolder {
        DoctorListRvBinding binding;
        public DocViweHolder(@NonNull DoctorListRvBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
