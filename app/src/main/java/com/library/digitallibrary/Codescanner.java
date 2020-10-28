package com.library.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Codescanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String databaseUrl = "https://digital-library-290309.firebaseio.com/";

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference rootmyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(Codescanner.this);
        setContentView(scannerView);


    }

    @Override
    public void handleResult(final Result result) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(result.getText());
        dialog.setTitle("Book Name");
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        final String scan_book_name = result.getText();
                        DatabaseReference reference = FirebaseDatabase.getInstance(databaseUrl).getReference("books");
                        Query Book_name = reference.orderByChild("Name").equalTo(scan_book_name);
                        Book_name.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    final String finalbookName = snapshot.child(scan_book_name).child("Name").getValue(String.class);
                                    Intent intent = getIntent();
                                    final String username = intent.getStringExtra("username");

                                    //to store book name into Firebase database
                                    database = FirebaseDatabase.getInstance(databaseUrl);
                                    rootmyRef = database.getReference("Issue_Books");

                                    //Adding into BookClass.java
                                    BookClass bookClass = new BookClass(username, finalbookName);
                                    rootmyRef.child(finalbookName).push().setValue(bookClass);

                                    Toast.makeText(Codescanner.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(getApplicationContext(),"Issued Successfull",Toast.LENGTH_LONG).show();


                        //Notification manager after book Issed
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MINUTE,5);

                        Intent intent1 = new Intent()
                        PendingIntent.getBroadcast(Codescanner.this, 100, intent1,PendingIntent.FLAG_UPDATE_CURRENT)
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),);
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(Codescanner.this);
        scannerView.startCamera();
    }
}