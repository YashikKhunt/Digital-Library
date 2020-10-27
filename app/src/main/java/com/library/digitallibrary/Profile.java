package com.library.digitallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Scanner;

public class Profile extends AppCompatActivity {

    ImageView prof_home_return_logo,prof_scan_logo;
    TextView prof_home_return,prof_scan_text,profile_username,profile_email,totla_book_count;
    Button profile_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Hooks
        prof_home_return_logo = (ImageView) findViewById(R.id.prof_home_return_logo);
        prof_scan_logo = (ImageView) findViewById(R.id.prof_scan_logo);
        prof_home_return = (TextView) findViewById(R.id.prof_home_return);
        prof_scan_text = (TextView) findViewById(R.id.prof_scan_text);
        profile_username = (TextView) findViewById(R.id.profile_username);
        profile_email = (TextView) findViewById(R.id.profile_email);
        totla_book_count = (TextView) findViewById(R.id.totla_book_count);
        profile_logout = (Button) findViewById(R.id.profile_logout);

        //Returning To home page
        prof_home_return_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Profile.this,Home.class);
                startActivity(i1);
            }
        });
        prof_home_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Profile.this,Home.class);
                startActivity(i2);
            }
        });

        //For directed to scanner
        prof_scan_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Profile.this, Codescanner.class);
                startActivity(i3);
            }
        });
        prof_scan_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(Profile.this, Codescanner.class);
                startActivity(i4);
            }
        });

        //Displying Username and email
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        profile_username.setText(username);
        profile_email.setText(email);

        //logout from the app
        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(Profile.this,Login.class);
                startActivity(i5);
                finish();
            }
        });

    }
}