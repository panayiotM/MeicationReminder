package com.example.medreminder3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Calendar;


public class DateFragment extends Fragment {


    Button button;
    Medicine medicine;
    EditText startDate,endDate;


    public DateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_date, container, false);

        startDate = view.findViewById(R.id.editStartDate2);
        endDate = view.findViewById(R.id.editEndDate2);
        medicine= medicine.getInstance();
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + 0+(month+1) + "/" + year;

        startDate.setText(date);
        endDate.setText(date);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog instance and set the OnDateSetListener
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // Set the selected date to the TextView
                                startDate.setText(0+selectedDayOfMonth + "/" + 0+(selectedMonth + 1) + "/" + selectedYear);
                            }
                        }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog instance and set the OnDateSetListener
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Set the selected date to the TextView
                        endDate.setText(0+selectedDayOfMonth + "/" + 0+(selectedMonth + 1) + "/" + selectedYear);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
        button = view.findViewById(R.id.nexbtn);
        button.setOnClickListener(btnView -> {
                    medicine.setStartDate(startDate.getText().toString());
                    medicine.setEndDate(endDate.getText().toString());
                    NavController navController = Navigation.findNavController(btnView);
                    navController.navigate(R.id.timeFragment);

        });
        return  view;
    }



}