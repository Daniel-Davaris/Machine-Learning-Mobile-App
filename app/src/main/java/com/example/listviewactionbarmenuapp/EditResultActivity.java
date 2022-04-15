package com.example.listviewactionbarmenuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EditResultActivity extends AppCompatActivity {
    private Uri imageFileUri;
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_result);

        imageView = findViewById(R.id.imageViewMLKit2);
        textViewOutput = findViewById(R.id.textViewMLKit2);
        textViewTitle = findViewById(R.id.textViewTitleMLKit2);

        Bundle extras = getIntent().getExtras();
        String type2 = extras.getString("type");
        String textViewOutput2 = extras.getString("textViewOutput");
        String textViewTitle2 = extras.getString("textViewTitle");
        String imageFileUri2= extras.getString("imageFileUri");
        Uri imageFileUri3 = Uri.parse(imageFileUri2);

        textViewOutput.setText(textViewOutput2.toString());
        textViewTitle.setText(textViewTitle2.toString());
        imageView.setImageURI(imageFileUri3);

       // imageView.setImageResource();
        setTitle("Edit Image Content");

        if(type2.equals("first")) {

        } else if (type2.equals("second")){

        } else if (type2.equals("third")){

        }
    }
}