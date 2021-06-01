package com.bielskiAdam.wizytownik;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bielskiAdam.wizytownik.DataBaseModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataBaseModel.DatabaseFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;

import android.widget.Button;


import java.io.ByteArrayOutputStream;



public class CreateNewActivity extends AppCompatActivity {

    private DatabaseFactory mDatabase;


    ImageView selectedImageView;
    EditText title, phone, address, description;

    Button takePicture, saveBusinessCardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        takePicture = findViewById(R.id.takePicture);


        selectedImageView = findViewById(R.id.selectedImage);
        title = findViewById(R.id.editTextTitleBusinessCard);
        phone = findViewById(R.id.editTextPhone);
        address = findViewById(R.id.editTextTextAddress);
        description = findViewById(R.id.editTextTextDescription);

        takePicture.setOnClickListener(view -> takePictureFromGallery());

//        saveBusinessCardButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                saveBusinessCard();
//            }
//        });
    }
//
//    public void saveBusinessCard(){
//        Toast.makeText(
//                CreateNewActivity.this,
//                "zjebało się",
//                Toast.LENGTH_SHORT
//        ).show();

//        byte[] image = convertImageViewToByteArray(selectedImageView);
//        factory = new DatabaseFactory(this);
//        BusinessCard businessCard = factory.getBusinessCard("SELECT * FROM businesscards WHERE id=1;");
//        if(businessCard == null){
//            if(factory.save(1, title.toString(), phone.toString(), address.toString(), description.toString(), image))
//                Toast.makeText(
//                        this,
//                        "successfully saved",
//                        Toast.LENGTH_SHORT
//                ).show();
//        }
//        else
//        {
//            if(factory.update(1, title.toString(), phone.toString(), address.toString(), description.toString(), image))
//                Toast.makeText(this, "successfully updated", Toast.LENGTH_SHORT).show();
//        }
//    }





    public void takePictureFromGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }
//
//    private void takePictureFromCamera(){
//        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(takePicture.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(takePicture, 2);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        Uri selectedImageUri = data.getData();
                        selectedImageView.setImageURI(selectedImageUri);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    try {
                        Bundle bundle = data.getExtras();
                        Bitmap bitmapImage = (Bitmap) bundle.get("data");
                        selectedImageView.setImageBitmap(bitmapImage);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

//    private boolean checkAndRequestPermissions(){
//        if(Build.VERSION.SDK_INT >= 23){
//            int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//            if(cameraPermission == PackageManager.PERMISSION_DENIED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 20);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            takePictureFromCamera();
//        }
//        else
//            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
//    }
}
