package com.example.listviewactionbarmenuapp;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.sql.DataSource;

public class FirebaseRealtimeDbStorage {
    String reference = "mobiletech";
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(reference);
    StorageReference storageRef = FirebaseStorage.getInstance().getReference(reference);

    public void uploadDataToRealtimeDatabase(CanberraEvent cbrEvent) {
        String key = dbRef.push().getKey();
        dbRef.child(key).child("title").setValue(cbrEvent.getTitle());
        dbRef.child(key).child("imageResource").setValue(cbrEvent.getImageResource());
        dbRef.child(key).child("dates").setValue(cbrEvent.getDates());
    }

    public void downloadDataFromRealtimeDB(ArrayAdapter<CanberraEvent> adapter) {
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                if(!snapshot.hasChildren() || snapshot.child("imageResource").getValue() == null) return;
                CanberraEvent cbrEvent = new CanberraEvent(
                        (String) snapshot.child("title").getValue(),
                        Integer.parseInt(snapshot.child("imageResource").getValue().toString()),
                        (String) snapshot.child("dates").getValue()
                );
                adapter.add(cbrEvent);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void uploadImageResouceFileToStorage(Context context, int resourceId) {
        if (resourceId != 0) {
            Uri uri = Uri.parse("android.resource://" +
                    R.class.getPackage().getName() + "/" + resourceId);
            // Get filename from its image resource
            TypedValue value = new TypedValue();
            context.getResources().getValue(resourceId, value, true);
            // Assume image files are in the drawable folder
            String filename = value.string.toString().substring(13, value.string.toString().length());

            storageRef.child(filename).putFile(uri);
        } else {
            Toast.makeText(context, "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void downloadImageFilefromStorage(Context context, ImageView imageView, int resourceId) {
        // Set local file for downloading image file to it
        File localFile = null;
        try {
            localFile = File.createTempFile("temp", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get filename from its image resource
        TypedValue value = new TypedValue();
        context.getResources().getValue(resourceId, value, true);
        String filePath = value.string.toString();
        // Assume image files are in the drawable folder
        String filename = filePath.substring(13, filePath.length());
        // Get reference to file in storage with this filename
        StorageReference fileRef = storageRef.child(filename);

        // Download image file with filename to localFile
        // and use localFile to display image on Image View
        if (localFile != null) {
            File finalLocalFile = localFile;
            fileRef.getFile(localFile) // get file from storage & put to localFile
                    .addOnSuccessListener(
                            new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Uri uri = Uri.fromFile(finalLocalFile); // get file path
                                    imageView.setImageURI(uri); // display on image view
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    Toast.makeText(context, "Unable to download", Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }

}
