package com.example.listviewactionbarmenuapp;

//import android.content.Context;
//import android.content.Context;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
// comment added for test commit
public class CanberraEventAdapter extends ArrayAdapter<CanberraEvent> {
    ArrayList<CanberraEvent> events;
    public CanberraEventAdapter(Context context, int resource, ArrayList<CanberraEvent> objects) {
        super(context, resource, objects);
        events = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.listview_item, parent, false);
        }

        // change background colour for even items, the odd items are unchanged
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }

        CanberraEvent event = events.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        icon.setImageResource(event.getImageResource());

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(event.getTitle());

        TextView textViewDates = (TextView) convertView.findViewById(R.id.textViewDates);
        textViewDates.setText(event.getDates());

        return convertView;
    }

}
