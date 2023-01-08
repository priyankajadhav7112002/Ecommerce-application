package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    TextView alreadyHaveAccount;
    EditText input_username,input_password,input_email,input_conformPassword;
    String username,email,password,conformPassword;
    Button btn_register;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_username = findViewById(R.id.Username);
        input_password = findViewById(R.id.InputPassword);
        input_conformPassword = findViewById(R.id.InputConformPassword);
        input_email = findViewById(R.id.Admin_InputEmail);
        btn_register = findViewById(R.id.btnRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = input_username.getText().toString();
                email = input_email.getText().toString();
                password = input_password.getText().toString();
                conformPassword = input_conformPassword.getText().toString();

                if(!TextUtils.isEmpty(username)) {
                    if (!TextUtils.isEmpty(email)) {
                        if (email.matches(emailPattern)) {
                            if (!TextUtils.isEmpty(password)) {
                                if (!TextUtils.isEmpty(conformPassword)) {
                                    if(conformPassword.equals(password)) {
                                        PerformAuth();
                                    }else {
                                        input_conformPassword.setError("Conform password and Password should be same");
                                    }
                                }else {
                                    input_conformPassword.setError("Conform password field can't be empty");
                                }
                            }else {
                                input_password.setError("Please enter password");
                            }
                        } else {
                            input_email.setError("Enter a valid Email Address");
                        }
                    }else {
                        input_email.setError("Please enter your Email id");
                    }
                }else {
                    input_username.setError("Please enter Username");
                }

            }

        });
    }

    private void PerformAuth() {
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("Username",username);
                    user.put("email",email );
                    user.put("User Id",firebaseAuth.getUid());
                    // The "users" is collection name. If the collection is already created then it will not create the same one again.
                    // Document Id for users fields. Here the document it is the user Id .
                    // Here the userInfo are Fields and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error - "+ e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
    }
    }

//    public static void userRegistrationSuccess(){
//        progressDialog.dismiss();
//        sendUserToNextActivity();
//        Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
//    }


//    private void sendUserToNextActivity() {
//
//    }

