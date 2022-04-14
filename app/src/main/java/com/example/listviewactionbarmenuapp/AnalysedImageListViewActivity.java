package com.example.listviewactionbarmenuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AnalysedImageListViewActivity extends AppCompatActivity {
    ArrayList<AnalysedImage> events = new ArrayList<AnalysedImage>();
    FirebaseRealtimeDbStorage firebase = new FirebaseRealtimeDbStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        AnalysedImageAdapter cbrEventAdapter = new AnalysedImageAdapter(
                this, R.layout.listview_item, events);

        firebase.downloadDataFromRealtimeDB(cbrEventAdapter);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cbrEventAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AnalysedImage cbrevent = events.get(position);
                        Intent intent = new Intent(view.getContext(), AnalysedImageListItemActivity.class);
                        intent.putExtra("title", cbrevent.getTitle());
                        intent.putExtra("imageResource", cbrevent.getImageResource());
                       /* intent.putExtra("dates", cbrevent.getDates());*/
                        AnalysedImageListViewActivity.this.startActivity(intent);
                    }
                });
    }

}