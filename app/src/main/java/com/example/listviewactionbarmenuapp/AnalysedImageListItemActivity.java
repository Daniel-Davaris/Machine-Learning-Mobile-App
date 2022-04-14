package com.example.listviewactionbarmenuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AnalysedImageListItemActivity extends AppCompatActivity {
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
        firebase.downloadImageFilefromStorage(AnalysedImageListItemActivity.this, imageView, imageRes);

        String dates = extras.getString("dates");
        textView = (TextView) findViewById(R.id.textViewSmall);
        textView.setText(dates);
    }
}