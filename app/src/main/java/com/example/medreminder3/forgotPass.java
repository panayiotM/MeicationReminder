package com.example.medreminder3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPass extends AppCompatActivity  {


    private EditText emailEditText;
    private Button resetPasswordButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailEditText = (EditText) findViewById(R.id.editEmailInput);
        resetPasswordButton = (Button) findViewById(R.id.reset);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword()
    {
     String email = emailEditText.getText().toString().trim();

     if(email.isEmpty())
     {
         emailEditText.setError("Please enter an email in the field provided!");
         emailEditText.requestFocus();
         return;
     }
     if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
     {
         emailEditText.setError("Please provide a valid email!");
         emailEditText.requestFocus();
         return;
     }

     auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if(task.isSuccessful())
             {
                 Toast.makeText(forgotPass.this, "Check your email for the password reset link!", Toast.LENGTH_LONG).show();
                 startActivity(new Intent(forgotPass.this, MainActivity.class));

             }else {
                 Toast.makeText(forgotPass.this, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

             }
         }
     });


    }
}