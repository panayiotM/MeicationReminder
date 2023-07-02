package com.example.medreminder3;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medreminder3.databinding.ActivityMenuBinding;


public class Menu extends AppCompatActivity {

    private Button Logout;
    ActivityMenuBinding binding;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        Logout = (Button) findViewById(R.id.logout);
        setContentView(binding.getRoot());
        changeFragment(new TodayFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.home:
                    changeFragment(new TodayFragment());
                    break;
                case R.id.calendar:
                    changeFragment(new CalendarFragment());
                    break;
                case R.id.settings:
                    changeFragment(new ProfileFragment());
                    break;
                case R.id.history:
                    changeFragment(new historyFragment());
                    break;


            }

            return true;

        });
    }


    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framelayer,fragment);
        fragmentTransaction.commit();
    }
}
