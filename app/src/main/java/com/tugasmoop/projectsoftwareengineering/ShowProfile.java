package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.MainActivity.currentUsername;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class ShowProfile extends AppCompatActivity implements View.OnClickListener {

    Button bEditPic;
    ImageView picProfile;
    public Uri imageUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        bEditPic = findViewById(R.id.bEditPic);

        picProfile = findViewById(R.id.picProfile);

        storageReference = storage.getReference();

        try {
            final File localFile = File.createTempFile(currentUsername,"jpg");
            storageReference.child("images/" + currentUsername + "/profile.jpg").getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Uri userUri = Uri.fromFile(localFile);
                            picProfile.setImageURI(userUri);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        bEditPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.bEditPic)){
            Intent getImage = new Intent();
            getImage.setType("image/*");//image diambil dari directory manapun
            getImage.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(getImage, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            picProfile.setImageURI(imageUri);

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageReference.child("images/" + currentUsername + "/profile.jpg");

            mountainImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ShowProfile.this, "Profile picture successfully changed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ShowProfile.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}