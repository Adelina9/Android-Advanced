package com.example.mafia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mafia.BuildConfig;
import com.example.mafia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!BuildConfig.my_flag) {
//            Timber.plant(new Timber.DebugTree());
//            Timber.wtf("Nu e setat");
//        } else {
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
                if (priority < Log.INFO) {
                    return;
                }

                if (t != null) {
                    FirebaseCrashlytics.getInstance().recordException(t);
                }

                String crashlyticsMessage = String.format("[%s] %s", tag, message);
                FirebaseCrashlytics.getInstance().log(crashlyticsMessage);
            }
        });
//        }
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextLoginActivityEmailInput);
        editTextPassword = findViewById(R.id.editTextLoginActivityPasswordInput);
        buttonLogin = findViewById(R.id.buttonLoginActivityLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
            finish();
        }

        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void loginExistingUser(View view) {
        Timber.tag(TAG).w(new IOException(), "Something went wrong");
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