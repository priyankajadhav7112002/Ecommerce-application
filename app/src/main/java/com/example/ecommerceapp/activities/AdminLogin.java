package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {
    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    TextView signUp, forgotPassword,loginAdmin,login;
    Button btnLogin;
    EditText input_email, input_password;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        input_email = findViewById(R.id.Admin_InputEmail);
        input_password = findViewById(R.id.Admin_Input_Password);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        forgotPassword = findViewById(R.id.Admin_forgotPassword);
        login = findViewById(R.id.Admin_login);

        signUp = findViewById(R.id.Admin_SignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        btnLogin = findViewById(R.id.Admin_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = input_email.getText().toString();
                password = input_password.getText().toString();

                if(!TextUtils.isEmpty(email)){
                    if(email.matches(emailPattern)){
                        if(!TextUtils.isEmpty(password)){
                            performLoginAdmin();
                        }else{
                            input_password.setError("Password field can't be empty");
                        }
                    }else{
                        input_email.setError("Enter a valid Email Address");
                    }
                }else{
                    input_email.setError("Please enter your Email id");
                }

               performLoginAdmin();

            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,ResetPassword.class));
            }
        });


    }

    private void performLoginAdmin() {
        progressDialog.setMessage("Please wait while Login...");
        progressDialog.setTitle("Login");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();
                Toast.makeText(AdminLogin.this,"Login Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminLogin.this,Admin_Panel.class);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AdminLogin.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    }
