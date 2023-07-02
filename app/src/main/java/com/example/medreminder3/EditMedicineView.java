package com.example.medreminder3;


import static androidx.fragment.app.FragmentManager.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


public class EditMedicineView extends AppCompatActivity  {

    private EditText medName;
    private EditText medDescription;
    private TextView endDate;

    private Button changeTime;
    private TextView timeView,refillTimeView;
    private Button changeRefillTime;

    private Button updateSetBtn;

    private Button updateCancelBtn;
    private Button deleteBtn;

   private TextView medCap;



    private Medicine medicine ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med);
        medName = findViewById(R.id.updateMedName);
        medDescription = findViewById(R.id.updateDescription);
        endDate = findViewById(R.id.updateEndDate);
        changeTime = findViewById(R.id.updateTimebtn);
        timeView= findViewById(R.id.updateTime);
        changeRefillTime = findViewById(R.id.updateRefillTimebtn);
        refillTimeView = findViewById(R.id.updateRefillTime);
        medCap = findViewById(R.id.updateCapacity);
        updateSetBtn= findViewById(R.id.updateSetBtn);
        updateCancelBtn= findViewById(R.id.updateCancelBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("json pojo");
        medicine = gson.fromJson(strObj, Medicine.class);
        medName.setText(medicine.getName());
        medDescription.setText(medicine.getDescription());
        endDate.setText(medicine.getEndDate());
        timeView.setText(medicine.getTime());
        refillTimeView.setText(medicine.getRefillTime());



        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog instance and set the OnDateSetListener
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditMedicineView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        endDate.setText(0+selectedDayOfMonth + "/" + 0+(selectedMonth + 1) + "/" + selectedYear);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                // Create a DatePickerDialog instance and set the OnDateSetListener
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditMedicineView.this, androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(cal.getTime());
                        medicine.setTime(time);
                        timeView.setText(time);
                    }

                }, hours,mins,false);
                timePickerDialog.show();
            }
        });
        changeRefillTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditMedicineView.this, androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(cal.getTime());
                        medicine.setRefillTime(time);
                        refillTimeView.setText(time);
                    }

                }, hours,mins,false);
                timePickerDialog.show();
            }
        });
        updateCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditMedicineView.this,Menu.class));
            }
        });

        updateSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine.setName(medName.getText().toString());
                medicine.setDescription(medDescription.getText().toString());
                medicine.setEndDate(endDate.getText().toString());
                updateMedicine(medicine);
                startActivity(new Intent(EditMedicineView.this,Menu.class));
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMedicine(medicine);
            }
        });

    }
    public void deleteMedicine(Medicine medicine) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medicineList");
        Query query = reference.orderByChild("uid").equalTo(medicine.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Medicine pojo = snap.getValue(Medicine.class);
                        Log.i(TAG, pojo.getName());
                        snap.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateMedicine(Medicine medicine) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medicineList");
        HashMap hashMap = new HashMap();
        hashMap.put(medicine.getUid(), medicine); //add to firebase using the KEY
        reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(), "Medicine has been updated", Toast.LENGTH_LONG).show();
            }
        });
    }

}

