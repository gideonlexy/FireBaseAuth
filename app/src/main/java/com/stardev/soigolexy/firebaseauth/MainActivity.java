package com.stardev.soigolexy.firebaseauth;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    TextView signUpTextView;

    private FirebaseAuth mAuth;

    ProgressBar progressBar;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passEditText);
        loginButton = findViewById(R.id.logInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Authenticate user
                InputMethodManager img = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                img.hideSoftInputFromWindow(passwordEditText.getWindowToken(),0);
                logIn();
            }
        });
        signUpTextView = findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        constraintLayout = findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

    }

    public void logIn() {
        progressBar.setVisibility(View.VISIBLE);

        String email,password;
         email = emailEditText.getText().toString();
         password = passwordEditText.getText().toString();


         if (TextUtils.isEmpty(email)) {
             Toast.makeText(getApplicationContext(),"Please Enter Email..",Toast.LENGTH_LONG).show();
             return;
         }
         if (TextUtils.isEmpty(password)) {
             Toast.makeText(getApplicationContext(),"Please Enter Password..",Toast.LENGTH_LONG).show();
             return;
         }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successfull..",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),BaseActivity.class));

                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });
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

        if (animationDrawable != null && !animationDrawable.isRunning()){
            //stop animation
            animationDrawable.stop();
        }
    }
}
