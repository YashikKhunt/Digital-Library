package com.library.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    //variables
    TextInputLayout regUsername, regEmail, regPassword;
    Button regSignUp, regToLoginBtn;

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;
    String databaseUrl = "https://digital-library-290309.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //hooks to all xml ID's
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regSignUp = findViewById(R.id.reg_sign_up);
        regToLoginBtn = findViewById(R.id.reg_signUp_to_LogIn);


        //save data into the firebase on signUp button click and Authentication using Email and password
        regSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cheak validation
                if(!registerUser() | !registerEmail() | !registerPassword()){
                    return;
                }

                //connection
                database = FirebaseDatabase.getInstance(databaseUrl);
                myRef = database.getReference("users");



                //get all the values
                final String username = regUsername.getEditText().getText().toString();
                final String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(username, email, password);
                myRef.child(username).setValue(helperClass);

                Toast.makeText(SignUp.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this,Home.class);
                startActivity(intent);
                finish();

            }
        });


        //Already registerd user back to login Screen
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private Boolean registerUser() {
        String val = regUsername.getEditText().getText().toString();
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean registerEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean registerPassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +       //at least 1 digit
                //"(?=.*[a-z])" +       //at least 1 lower case letter
                //"(?=.*[A-Z])" +       //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

}