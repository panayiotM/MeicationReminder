package com.example.medreminder3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class RefillFragment extends Fragment {
    Button nextBtn;

    Button skipBtn;
    TimePicker refillTime;
    Medicine medicine;
    TextView medLeft;


    public RefillFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refill, container, false);

        refillTime = view.findViewById(R.id.time_pickerRefill);
        medLeft = view.findViewById(R.id.medicineCap);

        medicine = medicine.getInstance();


        nextBtn = view.findViewById(R.id.nexbtn);
        nextBtn.setOnClickListener(btnView -> {
            Calendar cal = Calendar.getInstance();
            int hour = refillTime.getHour();
            int minute = refillTime.getMinute();
            cal.setTimeZone(TimeZone.getDefault());
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            SimpleDateFormat format = new SimpleDateFormat("k:mm a");
            String time = format.format(cal.getTime());
            Medicine medicine = Medicine.getInstance();
            medicine.setRefillTime(time);
            medicine.setMedCap(Integer.parseInt(medLeft.getText().toString()));
            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.setFragment);

        });
        skipBtn = view.findViewById(R.id.skipbtn);

        skipBtn.setOnClickListener(btnView -> {
            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.setFragment);


        });
        return view;

    }
}