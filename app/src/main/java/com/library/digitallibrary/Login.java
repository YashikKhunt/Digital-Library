package com.library.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button signUp,signIn;
    ImageView logo_image;
    TextView logo_name,slogan_name;
    TextInputLayout username,Password;

    String databaseUrl = "https://digital-library-290309.firebaseio.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        signUp = findViewById(R.id.sign_up);
        signIn = findViewById(R.id.sign_In);
        logo_image = findViewById(R.id.logo_image);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        Password = findViewById(R.id.password);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo_image, "logo_image");
                pairs[1] = new Pair<View,String>(logo_name, "logo_text");
                pairs[2] = new Pair<View,String>(slogan_name, "logo_desc");
                pairs[3] = new Pair<View,String>(username, "username_trans");
                pairs[4] = new Pair<View,String>(Password, "password_trans");
                pairs[5] = new Pair<View,String>(signIn, "button_trans");
                pairs[6] = new Pair<View,String>(signUp, "login_signup_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUsername() | !validatePassword()){
                    return;
                }
                else{
                    isUser();
                }
            }
        });


    }

    private void isUser() {
        final String UserEnterdUsername = username.getEditText().getText().toString().trim();
        final String UserEnterdPassword = Password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance(databaseUrl).getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(UserEnterdUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(UserEnterdUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(UserEnterdPassword)){
                        Password.setError(null);
                        Password.setErrorEnabled(false);


                        String UsernameFromDB = snapshot.child(UserEnterdUsername).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(UserEnterdUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(Login.this,Home.class);
                        intent.putExtra("username",UsernameFromDB);
                        intent.putExtra("email",emailFromDB);

                        startActivity(intent);
                        finish();

                    }else {
                        Password.setError("Wrong Password");
                        Password.requestFocus();
                    }
                }else {
                    username.setError("No such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Password.getEditText().getText().toString();
        if (val.isEmpty()) {
            Password.setError("Field cannot be empty");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

}