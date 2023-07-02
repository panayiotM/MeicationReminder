package com.example.medreminder3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class NameFragment extends Fragment {

    Button button;
    Medicine medicine;
    EditText medName,medDescription;

    ImageView medIcon1;
    ImageView medIcon3;
    ImageView medIcon4;
    ImageView medIcon5;
    ImageView medIcon6;
    ImageView medIcon8;

    public NameFragment() {
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

        View view =inflater.inflate(R.layout.fragment_name, container, false);
        medName= view.findViewById(R.id.editMedName);
        medDescription = view.findViewById(R.id.editDescription);

        medicine= medicine.getInstance();

        medIcon1= view.findViewById(R.id.medIcon11);
        medIcon3= view.findViewById(R.id.medIcon33);
        medIcon4= view.findViewById(R.id.medIcon44);
        medIcon5= view.findViewById(R.id.medIcon55);
        medIcon6= view.findViewById(R.id.medIcon66);
        medIcon8= view.findViewById(R.id.medIcon88);

        medIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setImage(R.drawable.ic_tablet);
            }
        });


        medIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                medicine.setImage(R.drawable.ic_drops);
            }
        });
        medIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                medicine.setImage(R.drawable.ic_powder);
            }
        });
        medIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setImage(R.drawable.ic_liquid);

            }
        });
        medIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setImage(R.drawable.ic_drops2);

            }
        });

        medIcon8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                medicine.setImage(R.drawable.ic_injections);
            }
        });

        button = view.findViewById(R.id.nexbtn);
        button.setOnClickListener(btnView -> {
            if (getActivity() != null) {
                if (isValidateName()){

                    medicine.setName(medName.getText().toString());
                    medicine.setDescription(medDescription.getText().toString());
                    NavController navController = Navigation.findNavController(btnView);
                    navController.navigate(R.id.dateFragment);
                }

            }


        });
        return  view;
    }

    private boolean isValidateName() {
        String nameInput = medName.getText().toString().trim();
        boolean isValidate;
        if (nameInput.isEmpty()) {
            medName.setError("Enter medicine name");
            isValidate = false;
        } else {

            medName.setError(null);
            isValidate = true;
        }

        return isValidate;
    }
}