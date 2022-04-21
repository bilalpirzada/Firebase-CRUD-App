package com.bilalpirzada.firebasecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEdt, pwdEdt, cnfPwdEdt;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEdt = findViewById(R.id.idUsername);
        pwdEdt =findViewById(R.id.idPassword);
        cnfPwdEdt = findViewById(R.id.idCnfPassword);
        registerBtn =findViewById(R.id.idRegisterBtn);
        loadingPB =findViewById(R.id.idProgressBar);
        loginTV = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displaying progress bar
                loadingPB.setVisibility(View.VISIBLE);
                String _userName = usernameEdt.getText().toString();
                String _password = pwdEdt.getText().toString();
                String _cnfPWD = cnfPwdEdt.getText().toString();

                if(TextUtils.isEmpty((_userName)) && TextUtils.isEmpty(_password) && TextUtils.isEmpty(_cnfPWD)){
                    Toast.makeText(RegistrationActivity.this, "please fill all the fields", Toast.LENGTH_LONG);
                }
                else if(!_password.equals(_cnfPWD)){

                    Toast.makeText(RegistrationActivity.this, "password doesn't match", Toast.LENGTH_LONG);
                }
                else{
                    //if all set
                    //creating new user
                    mAuth.createUserWithEmailAndPassword(_userName,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //hide progress bar
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "user registered successfully", Toast.LENGTH_LONG);
                                //opening login activity
                                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                //close current activity
                                finish();
                            }
                            else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "failed to register user", Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }
        });


        };

    }
