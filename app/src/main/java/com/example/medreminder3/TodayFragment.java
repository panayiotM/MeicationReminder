package com.example.medreminder3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TodayFragment extends Fragment {


    private Button btnAdd;
    View view;

    RecyclerView recyclerView;
    DatabaseReference database;
    HomeRecyclerViewAdapter medicationAdapter;
    ArrayList<Medicine> list;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);


        Calendar startDate = Calendar.getInstance();

        startDate.add(Calendar.MONTH, -1);


        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        recyclerView = view.findViewById(R.id.recyclerViewHome);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medicineList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        list = new ArrayList<>();
        medicationAdapter = new HomeRecyclerViewAdapter((List<Medicine>) list, (Context) getActivity());
        recyclerView.setAdapter(medicationAdapter);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + 0+(month+1) + "/" + year;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   try {
                       Medicine medicine = dataSnapshot.getValue(Medicine.class);
                       Date startDatePojo = sdf.parse(medicine.getStartDate());
                       Date endDatePojo = sdf.parse(medicine.getEndDate());
                       Date dateSelected = sdf.parse(date);

                       if (dateSelected.compareTo(startDatePojo) >= 0 && dateSelected.compareTo(endDatePojo) <= 0 ) {
                           list.add(medicine);

                       } else {

                       }
                       medicationAdapter.notifyDataSetChanged();
                       ReminderActivity.setMedicineArrayList(list);
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                           ReminderActivity.findNextAlarm();

                       }

                   }
                   catch(ParseException e){
                       e.printStackTrace();
                   }
               }

            }

            @Override
           public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAdd = view.findViewById(R.id.add_button);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code To Add HealthTaker
                startActivity(new Intent(getContext(), add_medicine.class));
            }
        });

        return view;
    }
    
}