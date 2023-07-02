package com.example.medreminder3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RefillDialog extends AppCompatActivity {
    Button btnOk;
    String medicineName;
    String uid;
    int medicineLimit;
    int medicineAmount, image;
    String refillTime;
    Button btnRefill;
    TextView textViewName;

    // ImageView refillImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        btnOk = findViewById(R.id.btnOk);
        btnRefill = findViewById(R.id.btnRefill);
        textViewName = findViewById(R.id.textViewName);

        this.setFinishOnTouchOutside(true);
        Intent intent = getIntent();
        medicineName = intent.getStringExtra(ReminderActivity.NAME);
        uid = intent.getStringExtra(ReminderActivity.UID);
        medicineLimit = intent.getIntExtra(ReminderActivity.LIMIT, -1);
        medicineAmount = intent.getIntExtra(ReminderActivity.AMOUNT, -1);
        refillTime = intent.getStringExtra(ReminderActivity.REFILLTIME);
        image = intent.getIntExtra("image", -1);
        //refillImageView.setImageResource(image);
        textViewName.setText("Your Medicine " + medicineName + " has " + medicineAmount + " Pills Left ");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RefillReminder.class);
                intent.putExtra(ReminderActivity.NAME, medicineName);
                intent.putExtra(ReminderActivity.UID, uid);
                intent.putExtra(ReminderActivity.LIMIT, medicineLimit);
                intent.putExtra(ReminderActivity.AMOUNT, medicineAmount);
                intent.putExtra(ReminderActivity.REFILLTIME, refillTime);
                startActivity(intent);
                finish();
            }
        });
    }
}