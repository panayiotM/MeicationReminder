package com.example.medreminder3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Medicine> mediceneArray = new ArrayList<>();




    public HistoryRecyclerViewAdapter(List<Medicine> mediceneArray, Context context) {
        this.context = context;
        this.mediceneArray = mediceneArray;
    }

    public void setList(List<Medicine> spesificList) {
        this.mediceneArray = spesificList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        TextView medName;
        TextView startDate,endDate;

        TextView medTaken;

        ImageView imgView;

        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            row = convertView;
            medName = row.findViewById(R.id.medcineTxtView25);
            startDate = row.findViewById(R.id.editStartDate3);
            endDate = row.findViewById(R.id.editEndDate3);
            medTaken =row.findViewById(R.id.pillsTaken);
            imgView = row.findViewById(R.id.imageView2);
            constraintLayout = row.findViewById(R.id.constraint_layout);

        }


        public View getRow() {
            return row;
        }


        public TextView getMedName() {
            return medName;
        }

        public TextView getStartDate() {
            return startDate;
        }
        public TextView getEndDate() {
            return endDate;
        }
        public TextView getMedTaken() {
            return medTaken;
        }

        public ImageView getImgView() {
            return imgView;
        }

    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row_show_medicine_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Medicine medicine = mediceneArray.get(position);
        holder.getMedName().setText(mediceneArray.get(position).getName());
        holder.getStartDate().setText(mediceneArray.get(position).getStartDate());
        holder.getEndDate().setText(mediceneArray.get(position).getEndDate());
        holder.getMedTaken().setText(mediceneArray.get(position).getPillCount()+ " ");
        holder.getImgView().setImageResource(mediceneArray.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + mediceneArray.size());
        return mediceneArray.size();
    }


}

