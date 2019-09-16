package com.stardev.soigolexy.firebaseauth;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    TextView loginTextView;
    ConstraintLayout constraintLayout;
    AnimationDrawable animationDrawable;
    private ProgressBar progressBar;
    Button signUpButton;
    EditText emailEditText;
    EditText passwordEditText;

    String email,password;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passEditText);
        progressBar = findViewById(R.id.progressBar);
        signUpButton = findViewById(R.id.logInButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add user to the database
                hideKeyboard();
                signUp();
            }
        });
        loginTextView = findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        constraintLayout = findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
    }

    public void signUp() {

        hideKeyboard();
        progressBar.setVisibility(View.VISIBLE);

        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please put an email address");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please put an email address");
            passwordEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please put an a valid email address");
            emailEditText.requestFocus();
            return;
        }

        if (password.length()<6) {
            passwordEditText.setError("Minimum Length of password should be 6 characters");
            passwordEditText.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(),"Registration Failed! Please try Again",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }

    public void hideKeyboard() {
        InputMethodManager img = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (img != null) {
            img.hideSoftInputFromWindow(passwordEditText.getWindowToken(),0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            //start animation
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            //stop animation
            animationDrawable.stop();
        }
    }
}
