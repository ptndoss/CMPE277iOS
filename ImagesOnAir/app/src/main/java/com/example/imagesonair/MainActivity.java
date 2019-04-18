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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth userAuth;
    EditText userName_editText, password_editText;
    String userName, password, userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAuth = FirebaseAuth.getInstance();

        userName_editText = findViewById(R.id.editText_username);
        password_editText = findViewById(R.id.editText_password);



        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(MainActivity.this);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.btn_login:
                loginValidation();
                break;
        }
    }

    private void loginValidation() {

        Log.i("<DOSS>", "----In LogIn method---");
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

        userAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(MainActivity.this, ShowImages.class);

                   if(userName.isEmpty()){
                       userName = getIntent().getStringExtra("username");
                       userid = userName.substring(0,userName.indexOf('@'));
                   }else{
                       userid = userName.substring(0,userName.indexOf('@'));
                   }

                    Log.i("<DOSS> USer Name",userid);
                    i.putExtra("username",userid );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
