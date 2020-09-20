package com.library.digitallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button signUp,signIn;
    ImageView logo_image;
    TextView logo_name,slogan_name;
    TextInputLayout username,password;

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
        password = findViewById(R.id.password);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo_image, "logo_image");
                pairs[1] = new Pair<View,String>(logo_name, "logo_text");
                pairs[2] = new Pair<View,String>(slogan_name, "logo_desc");
                pairs[3] = new Pair<View,String>(username, "username_trans");
                pairs[4] = new Pair<View,String>(password, "password_trans");
                pairs[5] = new Pair<View,String>(signIn, "button_trans");
                pairs[6] = new Pair<View,String>(signUp, "login_signup_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

    }
}