package com.example.mafia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mafia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextPassword;
    Button buttonRegister;
    FirebaseAuth firebaseAuth;
    TextView textViewGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextRegisterActivityNameInput);
        editTextEmail = findViewById(R.id.editTextRegisterActivityEmailInput);
        editTextPassword = findViewById(R.id.editTextRegisterActivityPasswordInput);
        buttonRegister = findViewById(R.id.buttonRegisterActivityRegister);
        textViewGoToLogin = findViewById(R.id.textViewRegisterActivityAlreadyRegisteredString);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
            finish();
        }

        textViewGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void registerNewUser(View view) {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("The name is a mandatory field.");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("The email is a mandatory field.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("The password is a mandatory field.");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User created successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}