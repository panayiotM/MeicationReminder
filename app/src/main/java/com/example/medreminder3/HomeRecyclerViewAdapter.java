package com.example.medreminder3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Medicine> mediceneArray = new ArrayList<>();


    private Button takebtn;
    private ImageButton editbtn;


    public HomeRecyclerViewAdapter(List<Medicine> mediceneArray, Context context) {
        this.context = context;
        this.mediceneArray = mediceneArray;
    }

    public void setList(List<Medicine> spesificList) {
        this.mediceneArray = spesificList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        TextView medName;
        TextView takenDate;
        TextView medDescription;

        ImageView imgView;

        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            row = convertView;
            medName = row.findViewById(R.id.medcineTxtView25);
            takenDate = row.findViewById(R.id.takenTimeTxtView);
            medDescription = row.findViewById(R.id.descriptionTxtView);
            constraintLayout = row.findViewById(R.id.constraint_layout);
            imgView = row.findViewById(R.id.imageView);
            takebtn = row.findViewById(R.id.takebtn);
            editbtn = row.findViewById(R.id.editbtn);

        }


        public View getRow() {
            return row;
        }


        public TextView getMedName() {
            return medName;
        }

        public TextView getTakenDate() {
            return takenDate;
        }

        public TextView getMedDescription() {
            return medDescription;
        }

        public ImageView getImgView() {
            return imgView;
        }

    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row_show_medicine_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Medicine medicine = mediceneArray.get(position);
        holder.getMedName().setText(mediceneArray.get(position).getName());
        holder.getMedDescription().setText(mediceneArray.get(position).getDescription());
        holder.getImgView().setImageResource(mediceneArray.get(position).getImage());

        takebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int capacity = mediceneArray.get(position).getMedCap();
                int counter = mediceneArray.get(position).getPillCount();
                counter++;
                capacity --;
                medicine.setPillCount(counter);
                medicine.setMedCap(capacity);
                String time, xhours, date;
                date = mediceneArray.get(position).getStartDate();
                xhours = mediceneArray.get(position).getXhours();
                time = mediceneArray.get(position).getTime();
                try {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

                    Date date2 = dateFormatter.parse(date);

                    SimpleDateFormat timeFormatter = new SimpleDateFormat("k:mm a");
                    Date time2 = timeFormatter.parse(time);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date2);
                    String amPmMarker = timeFormatter.format(time2).substring(6);
                    calendar.set(Calendar.HOUR, time2.getHours());
                    calendar.set(Calendar.MINUTE, time2.getMinutes());


                    switch (xhours) {
                        case "2 hours":
                            calendar.add(Calendar.HOUR, 2);
                            break;

                        case "4 hours":
                            calendar.add(Calendar.HOUR, 4);
                            break;

                        case "6 hours":
                            calendar.add(Calendar.HOUR, 6);
                            break;

                        case "8 hours":
                            calendar.add(Calendar.HOUR, 8);
                            break;

                        case "10 hours":
                            calendar.add(Calendar.HOUR, 10);
                            break;

                        case "12 hours":
                            calendar.add(Calendar.HOUR, 12);
                            break;

                        case "24 hours":
                            calendar.add(Calendar.HOUR, 24);
                            break;


                    }
                    Date dateTime = calendar.getTime();
                    String newDate = dateFormatter.format(dateTime);
                    String newTime = timeFormatter.format(dateTime);
                    medicine.setTime(newTime);
                    medicine.setStartDate(newDate);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                updateMedicine(medicine);


            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(context.getApplicationContext(), EditMedicineView.class);
                Gson gson = new Gson();
                String json = gson.toJson(medicine);
                editIntent.putExtra("json pojo", json);
                context.startActivity(editIntent);
            }
        });



        String date = mediceneArray.get(position).getTime();
        StringTokenizer stringTokenizer = new StringTokenizer(date, ",");
        String newDate = "";
        while (stringTokenizer.hasMoreTokens()) {
            String x = (String) stringTokenizer.nextElement();
            String[] separated = x.split(":");
            int N = Integer.parseInt(separated[0]);
            String C = separated[1];
            if (C.equals("0")) {
                C = "00";
                Log.i("TAG", "onBindViewHolder: " + C);
            }
            Log.i("TAG", "onBindViewHolder:  seperated " + separated[0]);
            Log.i("TAG", "onBindViewHolder: separated " + separated[1]);
            if (N == 24) {
                newDate = newDate + "\n" + 12 + C + " AM";

            } else {
                if (N > 12) {
                    N = N - 12;

                    newDate = newDate + "\n" + N + ":" + C + " PM";

                } else
                    newDate = newDate + "\n" + N + ":" + C + " PM";

            }
        }
        holder.getTakenDate().setText(date);


    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + mediceneArray.size());
        return mediceneArray.size();
    }

    public void updateMedicine(Medicine medicine) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medicineList");
        HashMap hashMap = new HashMap();
        hashMap.put(medicine.getUid(), medicine); //add to firebase using the KEY
        reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(context.getApplicationContext(), "Medicine has been taken", Toast.LENGTH_LONG).show();
            }
        });
    }

}

