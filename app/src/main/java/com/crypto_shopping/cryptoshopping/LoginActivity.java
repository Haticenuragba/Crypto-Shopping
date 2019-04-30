package com.crypto_shopping.cryptoshopping;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;

    private Button btnLogin;
    private Button btnRegister;

    private SessionManager mSessionManager = null;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mSessionManager = new SessionManager(this);

        if (!mSessionManager.getEmail().equalsIgnoreCase("") &&
                !mSessionManager.getPassword().equalsIgnoreCase("")) {
            login(mSessionManager.getEmail(), mSessionManager.getPassword());
        }




        editEmail = findViewById(R.id.login_activity_editEmail);
        editPassword = findViewById(R.id.login_activity_editPassword);
        btnLogin = findViewById(R.id.login_activity_btnLogin);
        btnRegister = findViewById(R.id.login_activity_btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEmail.getText().toString().equalsIgnoreCase("") ||
                        editPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(editEmail.getText().toString(), editPassword.getText().toString());
            }
        });
    }

    private void login(final String email, final String password) {
        final AlertDialog progressDialog = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("In Progres...")
                .setMessage("Please Wait...")
                .setCancelable(false)
                .show();

        Task<AuthResult> task =
                mFirebaseAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    // login is successfull
                    mSessionManager.setLoginData(email, password);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
