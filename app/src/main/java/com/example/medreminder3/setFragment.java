package com.example.medreminder3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class setFragment extends Fragment {
    Button setBtn,cancelBtn;

    Medicine medicine;




    public setFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set, container, false);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medicine = Medicine.getInstance();
        setBtn = view.findViewById(R.id.setBtn2);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setUid("");
                medicine.setId(0);             /*   Medicine object = new Medicine(0, medicine.getName(), medicine.getForm(), medicine.getStrength(), medicine.getReason(), medicine.getIsDaily(),
                        medicine.getOften(), medicine.getTime(), medicine.getStartDate(), medicine.getEndDate(), medicine.getMedLeft(), medicine.getRefillLimit(), medicine.getImage()
                        , medicine.getUid(), medicine.getTimeRefill());
         //       medicineToFireBase = object;
*/
                addMedToFireBase(medicine);
                startActivity(new Intent(getContext(), Menu.class));
                getActivity().finish();
            }
        });


    }
    public void addMedToFireBase(Medicine medicine) {
        DatabaseReference referencee = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medicineList").push();
        String MedUid = referencee.getKey();
        medicine.setUid(MedUid);
        referencee.setValue(medicine);
    }
}