package com.example.listviewactionbarmenuapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    ArrayList<CanberraEvent> events = new ArrayList<CanberraEvent>();
    FirebaseRealtimeDbStorage firebase = new FirebaseRealtimeDbStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        CanberraEventAdapter cbrEventAdapter = new CanberraEventAdapter(
                this, R.layout.listview_item, events);

        firebase.downloadDataFromRealtimeDB(cbrEventAdapter);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cbrEventAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CanberraEvent cbrevent = events.get(position);
                        Intent intent = new Intent(view.getContext(), EventActivity.class);
                        intent.putExtra("title", cbrevent.getTitle());
                        intent.putExtra("imageResource", cbrevent.getImageResource());
                        intent.putExtra("dates", cbrevent.getDates());
                        ListViewActivity.this.startActivity(intent);
                    }
                });
    }

}