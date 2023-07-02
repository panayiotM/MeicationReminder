package com.example.medreminder3;

import static com.example.medreminder3.R.layout.list_hours;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class TimeFragment extends Fragment {
    String[] hours ={"2 hours","4 hours","6 hours","8 hours","10 hours","12 hours","16 hours","24 hours"};
    Button nextBtn;
    TimePicker medTime;

    ArrayAdapter<String> adapterItems;
    Medicine medicine;
    TextView medLeft;
    AutoCompleteTextView autoCompleteTxt;


    public TimeFragment() {
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

        View view = inflater.inflate(R.layout.fragment_time, container, false);

        medTime = view.findViewById(R.id.time_picker);
        autoCompleteTxt = view.findViewById(R.id.autoComplete_txt);
        adapterItems = new ArrayAdapter<String>(getContext(), list_hours,hours);
        autoCompleteTxt.setAdapter(adapterItems);

        medicine = medicine.getInstance();
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String hour = parent.getItemAtPosition(position).toString();
                Medicine medicine = Medicine.getInstance();
                medicine.setXhours(hour);

            }
        });


        nextBtn = view.findViewById(R.id.nextTimebtn);

        nextBtn.setOnClickListener(btnView -> {
                Calendar cal = Calendar.getInstance();
                int hour = medTime.getHour();
                int minute = medTime.getMinute();
                cal.setTimeZone(TimeZone.getDefault());
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                String time = format.format(cal.getTime());
                Medicine medicine = Medicine.getInstance();
                medicine.setTime(time);


            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.refillFragment);

        });


        return view;

    }
}