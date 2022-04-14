package com.example.listviewactionbarmenuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class EventActivity extends AppCompatActivity {
    FirebaseRealtimeDbStorage firebase = new FirebaseRealtimeDbStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();

        String title = extras.getString("title");
        TextView textView = (TextView) findViewById(R.id.textViewLarge);
        textView.setText(title);

        int imageRes = extras.getInt("imageResource");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        firebase.downloadImageFilefromStorage(EventActivity.this, imageView, imageRes);

        String dates = extras.getString("dates");
        textView = (TextView) findViewById(R.id.textViewSmall);
        textView.setText(dates);
    }
}