package com.example.listviewactionbarmenuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    FirebaseRealtimeDbStorage firebase = new FirebaseRealtimeDbStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // Test Realtime database: upload text "it works!" to data table "test"

        // Step 1: Create a data table "test" in the database and set a reference to it
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("test");
        // Step 2: Create a data location "data_uploaded" in the "test" table
        // This is done with dbRef.child("data_uploaded")
        // Step 3: Upload the text "it works!" using setValue method
//        dbRef.child("data_uploaded").setValue("it works!");


        // Test storage: copy file "canberra" in the "drawable" folder
        // to "file_uploaded" in folder "test" in Storage

        // Step 1: Create a folder "test" in the storage and set a reference to it
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference("test");
        // Step 2: Create a file location "file_uploaded" in the "test" folder
        // This is done with storageRef.child("file_uploaded")
        // Step 3: Use uri to get file path including filename "canberra" and the "drawable" folder
//        Uri uri = Uri.parse("android.resource://" +
//                R.class.getPackage().getName() + "/" + R.drawable.canberra);
//        // Step 4: Copy this file to the storage using putFile method
//        storageRef.child("file_uploaded").putFile(uri);
    }

    public void gotolistview(View view) {
        Intent intent = new Intent(this, AnalysedImageListViewActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_listview:
                Intent intent = new Intent(this, AnalysedImageListViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void uploadToRealtimeDB(View view) {
        /*AnalysedImage cbrEvent = new AnalysedImage("Canberra Balloon Spectacular ", R.drawable.balloon_canberra, "12 Mar 2022 - 20 Mar 2022");
        firebase.uploadDataToRealtimeDatabase(cbrEvent);
        cbrEvent = new AnalysedImage("Enlighten Festival", R.drawable.enlighten_festival, "26 Feb 2022 - 14 Mar 2022");
        firebase.uploadDataToRealtimeDatabase(cbrEvent);
        cbrEvent = new AnalysedImage("Racing Carnival Canberra", R.drawable.racing_canberra, "13-14 Mar 2022");
        firebase.uploadDataToRealtimeDatabase(cbrEvent);
        cbrEvent = new AnalysedImage("Sunset Cinema Canberra", R.drawable.sunset_canberra, "25 Nov 2021 - 26 Feb 2022");
        firebase.uploadDataToRealtimeDatabase(cbrEvent);*/
    }

    public void uploadToStorage(View view) {
/*        firebase.uploadImageResouceFileToStorage(MainActivity.this, R.drawable.balloon_canberra);
        firebase.uploadImageResouceFileToStorage(MainActivity.this, R.drawable.enlighten_festival);
        firebase.uploadImageResouceFileToStorage(MainActivity.this, R.drawable.racing_canberra);
        firebase.uploadImageResouceFileToStorage(MainActivity.this, R.drawable.sunset_canberra);*/
    }

    public void startMLKit1(View view) {
        Intent intent = new Intent(this, MLKitActivity.class);
        intent.putExtra("type","first");
        startActivity(intent);
    }
    public void startMLKit2(View view) {
        Intent intent = new Intent(this, MLKitActivity.class);
        intent.putExtra("type","second");
        startActivity(intent);
    }
    public void startMLKit3(View view) {
        Intent intent = new Intent(this, MLKitActivity.class);
        intent.putExtra("type","third");
        startActivity(intent);
    }





}