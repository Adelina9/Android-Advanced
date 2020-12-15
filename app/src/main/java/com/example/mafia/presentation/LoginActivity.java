package com.example.mafia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mafia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = findViewById(R.id.editTextLoginActivityEmailInput);
        editTextPassword = findViewById(R.id.editTextLoginActivityPasswordInput);
        buttonLogin = findViewById(R.id.buttonLoginActivityLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
            finish();
        }
    }

    public void loginExistingUser(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("The email is a mandatory field.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("The password is a mandatory field.");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "User logged in successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}