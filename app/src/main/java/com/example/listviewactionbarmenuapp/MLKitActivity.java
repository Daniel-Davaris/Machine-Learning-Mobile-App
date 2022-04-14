package com.example.listviewactionbarmenuapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MLKitActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 3000;
    private Uri imageFileUri;
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewOutput;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlkit);

        imageView = findViewById(R.id.imageViewMLKit);
        textViewOutput = findViewById(R.id.textViewMLKit);
        textViewTitle = findViewById(R.id.textViewTitleMLKit);
    }

    public void openCamera(View view) {
        if (checkPermissions() == false)
            return;
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFileUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        activityResultLauncher.launch(takePhotoIntent);
    }

    public void loadImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(galleryIntent);
    }

    private boolean checkPermissions() {
        String permissions[] = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean grantCamera =
                ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED;
        boolean grantExternal =
                ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED;

        if (!grantCamera && !grantExternal) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        } else if (!grantCamera) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, REQUEST_PERMISSION);
        } else if (!grantExternal) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[1]}, REQUEST_PERMISSION);
        }
        return grantCamera && grantExternal;
    }

    // Create launcher variable inside onAttach or onCreate or global
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                       // Add code
                        if (result.getData() != null && result.getData().getData() != null)
                            imageFileUri = result.getData().getData();
                        imageView.setImageURI(imageFileUri);
                        // Prepare to show ML kit results
                        textViewOutput.setText("");
                        textViewTitle.setText("ML Kit Results");
                        // Create an InputImage object for ML kit
                        InputImage image = null;
                        try {
                            image = InputImage.fromFilePath(getBaseContext(), imageFileUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Call barcode reader method
                        if (image != null) {
                            //runBarcodeReader(image);
                            //runTextReader(image);
                            runImageContentReader(image);
                        }
                    }
                }
            });

    public void runBarcodeReader(InputImage image) {
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build();
        BarcodeScanner scanner = BarcodeScanning.getClient(options);

        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        textViewOutput.append("Detected barcode: \n");
                        String result = null;
                        for (Barcode barcode : barcodes) {
                            result = barcode.getRawValue();
                            textViewOutput.append("  " + result + "\n");
                        }
                        if (result == null) {
                            textViewOutput.append("  No barcode found\n");
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        textViewOutput.setText("Failed");
                    }
                });
    }

    public void runTextReader(InputImage image) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                String result = visionText.getText();
                                if (result.length() > 0)
                                    textViewOutput.append("Detected text:\n  " + result + "\n");
                                else {
                                    textViewOutput.append("Detected text:\n  No text found.\n");
                                }
                            }

                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        textViewOutput.append("Failed\n");
                                    }
                                });

    }

    public void runImageContentReader(InputImage image) {
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        labeler.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<ImageLabel>>() {
                            @Override
                            public void onSuccess(List<ImageLabel> labels) {
                                textViewOutput.append("Detected image objects: \n");
                                String result = null;
                                int counter = 0;
                                for (ImageLabel label : labels) {
                                    //result = barcode.getRawValue()
                                    result = label.getText();
                                    float confidence = label.getConfidence();
                                    textViewOutput.append(counter+". "+result +" ("+ confidence+"% confidence)" + "\n");
                                    counter++;
                                }
                                if (result == null) {
                                    textViewOutput.append("  No image objects found\n");
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        textViewOutput.setText("Failed");
                    }
                });
    }
}