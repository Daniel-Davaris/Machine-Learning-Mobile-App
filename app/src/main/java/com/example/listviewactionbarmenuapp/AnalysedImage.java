package com.example.listviewactionbarmenuapp;

import android.net.Uri;

public class AnalysedImage {
    /*String title;
    int imageResource;
    String dates;*/

    String reader; // variable for which ml fuction to run
    String result; // variable to store the ml result text
    Uri imageUri; // to replace 'imageResource' gets the image location
    String imageFileName;

    /*public AnalysedImage(String title, int imageResource, String dates) {
        this.title = title;
        this.imageResource = imageResource;
        this.dates = dates;
    }*/
    public AnalysedImage(String reader, String result, Uri imageUri, String imageFileName) {
        this.reader = reader;
        this.result = result;
        this.imageUri = imageUri;
        this.imageFileName = imageFileName;
    }

   /* public String getTitle(){
        return this.title;
    }*/
     public String getTitle(){
        return this.imageFileName;
    }

   /* public int getImageResource(){
        return this.imageResource;
    }*/
    public Uri getImageResource(){
        return this.imageUri;
    }

   /* public String getDates(){
        return this.dates;
    }*/

   /* @Override
    public String toString(){
        return this.title;
    }*/
}
