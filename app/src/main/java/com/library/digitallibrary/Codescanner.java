package com.library.digitallibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Codescanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String databaseUrl = "https://digital-library-290309.firebaseio.com/";

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
        dialog.setTitle("please Confirm");
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = getIntent();
                        String username = intent.getStringExtra("username");
                        DatabaseReference reference = FirebaseDatabase.getInstance(databaseUrl).getReference("users").child(username);
                        reference.child("Book").setValue(result.getText());
                        Toast.makeText(getApplicationContext(),"Issued Successfull",Toast.LENGTH_LONG).show();
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