package com.example.medreminder3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment {


    View view;
    CalendarView cale;
    TextView dateShow;
    RecyclerView recyclerView;
    DatabaseReference database;
    HomeRecyclerViewAdapter medicationAdapter;
    ArrayList<Medicine> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        cale = view.findViewById(R.id.calendarView);
        cale.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull final CalendarView view, final int year, final int month,
                                            final int dayOfMonth) {
                Toast.makeText(getActivity(), "Date changed to" + dayOfMonth + "." + month + "." + year, Toast.LENGTH_SHORT).show();
                String date = 0+dayOfMonth + "/" + 0+(month+1) + "/" + year;
                showDateTimeDialog(date);
            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        return root;
    }

    public void showDateTimeDialog(String date) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_cal);
        dateShow = dialog.findViewById(R.id.editTextDate);
        recyclerView = dialog.findViewById(R.id.recyclerViewCal);
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("medicineList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        list = new ArrayList<>();
        medicationAdapter = new HomeRecyclerViewAdapter((List<Medicine>) list, (Context) getActivity());
        recyclerView.setAdapter(medicationAdapter);
        dateShow.setText(date);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    try {
                        Medicine medicine = dataSnapshot.getValue(Medicine.class);

                        java.util.Date startDatePojo = sdf.parse(medicine.getStartDate());
                        Date endDatePojo = sdf.parse(medicine.getEndDate());
                        Date dateSelected = sdf.parse(date);
                        if (dateSelected.compareTo(startDatePojo) >= 0 && dateSelected.compareTo(endDatePojo) <= 0 ) {

                            list.add(medicine);
                        } else {

                       }

                    }
                    catch(ParseException e){
                        e.printStackTrace();
                    }
                }
                medicationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}

