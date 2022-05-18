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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEdt, pwdEdt;
    private Button loginBtn;
    private ProgressBar loadingPB;
    private TextView registerTV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEdt = findViewById(R.id.idUsername);
        pwdEdt =findViewById(R.id.idPassword);
        loginBtn =findViewById(R.id.idLoginBtn);
        loadingPB =findViewById(R.id.idProgressBar);
        registerTV = findViewById(R.id.idTVRegister);
        mAuth = FirebaseAuth.getInstance();

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String _username = usernameEdt.getText().toString();
                String _password = pwdEdt.getText().toString();
                if(TextUtils.isEmpty(_username) && TextUtils.isEmpty(_password)){
                    Toast.makeText(LoginActivity.this, "please enter your credentials", Toast.LENGTH_SHORT).show();
                return;
                }
                else{
                    mAuth.signInWithEmailAndPassword(_username,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           loadingPB.setVisibility(View.GONE);
                           Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT);
                           Intent i = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(i);
                           finish();
                       }
                       else{
                           loadingPB.setVisibility(View.GONE);
                           Toast.makeText(LoginActivity.this, "fail to login", Toast.LENGTH_SHORT);
                       }
                        }
                    });
                }
            }
        });
    }

    //check on start if the user is already login
    @Override
    protected void onStart() {
        super.onStart();
        //get user
        FirebaseUser _user = mAuth.getCurrentUser();
        //if user is already login
        if(_user!=null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}