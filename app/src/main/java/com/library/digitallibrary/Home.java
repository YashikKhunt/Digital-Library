package com.library.digitallibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    TextView home_username,home_email;
    Button home_logout,home_scan_btn,home_available_book,home_issue_book;
    ImageButton home_scan_img;
    ListView home_listview;
    ArrayList<String> myArraylist = new ArrayList<>();
    String databaseUrl = "https://digital-library-290309.firebaseio.com/";
    //Database refrance
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
         final ArrayAdapter<String> myArrayAddepter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1,myArraylist);
        //Hooks
        home_username = findViewById(R.id.home_username);
        home_email = findViewById(R.id.home_email);
        home_logout = findViewById(R.id.home_logout);
        home_scan_btn = findViewById(R.id.home_scan_btn);
        home_available_book = findViewById(R.id.home_available_book);
        home_issue_book = findViewById(R.id.home_issue_book);
        home_scan_img = findViewById(R.id.home_scan_img);
        home_listview = findViewById(R.id.home_listview);

        home_listview.setAdapter(myArrayAddepter);

        myRef = FirebaseDatabase.getInstance(databaseUrl).getReference().child("books");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren())
                    {
                        StringBuilder s = new StringBuilder();
                        if(postSnapshot.exists()){
                            for (DataSnapshot postSnapshot2: postSnapshot.getChildren())
                            {
                                s.append(String.valueOf(postSnapshot2.getValue()));
                                s.append("\n");
                            }
                            myArraylist.add(s.toString());
                            myArrayAddepter.notifyDataSetChanged();

                        }
//
//                        String bookname = String.valueOf(postSnapshot.getValue());
//                        bookname = bookname.replace("==\"\""," ");
//                        bookname = bookname + "\n";
//                        myArraylist.add(bookname);
//                        myArrayAddepter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //display username and email
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        Toast.makeText(getApplicationContext(),username+" "+email,Toast.LENGTH_SHORT).show();
        home_username.setText(username);
        home_email.setText(email);

        //Logout Function
        home_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Login.class);
                startActivity(intent);
            }
        });

    }
}