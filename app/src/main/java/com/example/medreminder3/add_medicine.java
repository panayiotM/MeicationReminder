package com.example.medreminder3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

public class add_medicine extends AppCompatActivity {
    public static String typeofUser = "";
    private static final String TAG = "AddMActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmedicine);

//        getSupportActionBar().hide();
        typeofUser = getIntent().getStringExtra("TYPE");
        Log.i(TAG, "onCreate: typeofUser --------> " + typeofUser);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavigationContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavGraph navGraph = navHostFragment.getNavController().getNavInflater().inflate(R.navigation.nav_graph);
            navGraph.setStartDestination(R.id.nameFragment);
            navController.setGraph(navGraph);
        }
    }




}
