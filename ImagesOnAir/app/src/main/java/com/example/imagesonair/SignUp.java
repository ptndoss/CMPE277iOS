package com.example.imagesonair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText userName_editText, password_editText;
    String userName, password,userid;
    Button signUp;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        FirebaseApp.initializeApp(this);
//        Log.i("<DOSS>", "--"+FirebaseApp.initializeApp(this).getName());
        mAuth = FirebaseAuth.getInstance();
        Log.i("<DOSS>", ""+mAuth.toString());
        userName_editText = findViewById(R.id.editText_signIn_username);
        password_editText = findViewById(R.id.editText_signIn_password);

        signUp= findViewById(R.id.btn_signup);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_signup:
                signUpUser();
                break;
            case R.id.btn_logIn1:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void signUpUser() {

        Log.i("<DOSS>", "----In SignUp method---");
        userName =userName_editText.getText().toString().trim();
        password =userName_editText.getText().toString().trim();

        if(userName.isEmpty()){
            userName_editText.setError("User Name is Required");
            userName_editText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
            userName_editText.setError("Enter Valid Email address");
            userName_editText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password_editText.setError("User Name is Required");
            password_editText.requestFocus();
            return;
        }

        if(password.length()<6){
            password_editText.setError("Password must be greater than 6 char");
            password_editText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignUp.this, ShowImages.class);
                    userid = userName.substring(0,userName.indexOf('@'));
                    Log.i("<DOSS> USer Name",userid);
                    i.putExtra("username",userid );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Email ID already exists. Please login", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
