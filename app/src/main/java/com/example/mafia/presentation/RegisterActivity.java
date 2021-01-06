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
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonRegister;
    FirebaseAuth firebaseAuth;
    TextView textViewGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextRegisterActivityUsernameInput);
        editTextEmail = findViewById(R.id.editTextRegisterActivityEmailInput);
        editTextPassword = findViewById(R.id.editTextRegisterActivityPasswordInput);
        editTextConfirmPassword = findViewById(R.id.editTextRegisterActivityPasswordConfirmInput);
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
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmation = editTextConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("The username is a mandatory field.");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("The email is a mandatory field.");
            return;
        }

        if (!isEmailValid(email)){
            editTextEmail.setError("Not a valid email.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("The password is a mandatory field.");
            return;
        }

        if (password.length() < 7) {
            editTextPassword.setError("The password should be at least 7 characters.");
            return;
        }

        if (TextUtils.isEmpty(passwordConfirmation)) {
            editTextConfirmPassword.setError("You should confirm your password.");
            return;
        }

        if (!password.equals(passwordConfirmation)) {
            editTextPassword.setError("Passwords are not identical.");
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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}