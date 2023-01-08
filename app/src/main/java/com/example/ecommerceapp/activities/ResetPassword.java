package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;
    private FirebaseAuth firebaseAuth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        registeredEmail = findViewById(R.id.forgot_password_email);
        resetPasswordBtn = findViewById(R.id.reset_password_btn);
        goBack = findViewById(R.id.forgot_password_go_back);
        firebaseAuth = FirebaseAuth.getInstance();

        registeredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this,LoginActivity.class));
            }
        });
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = registeredEmail.getText().toString();
                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));

                if (TextUtils.isEmpty(email)) {
                    registeredEmail.setError("Fill Email");       //if blank display error
                }

//                progressBar.setVisibility(View.VISIBLE);   //set visibility

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this, "Successfully send you response", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ResetPassword.this, "Failed to Send", Toast.LENGTH_SHORT).show();
                        }
                        resetPasswordBtn.setEnabled(true);
                        resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
                    }
                });
            }
        });
    }

    private void CheckInputs() {
        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }else{
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
        }
    }
}