package com.bielskiAdam.wizytownik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bielskiAdam.wizytownik.DataModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataModel.BusinessCardDatabase;
import com.bielskiAdam.wizytownik.DataModel.DataBaseDAO;
import com.bielskiAdam.wizytownik.DataModel.DataConverter;

import java.io.IOException;

public class CreateNewActivity extends AppCompatActivity {

    ImageView selectedImageView;
    Uri imageUri;
    Bitmap bmpImage = null;
    EditText title, phone, address, description;

    DataBaseDAO dataBaseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new);

        selectedImageView = findViewById(R.id.selectedImage);
        title = findViewById(R.id.editTextTitleBusinessCard);
        phone = findViewById(R.id.editTextPhone);
        address = findViewById(R.id.editTextTextAddress);
        description = findViewById(R.id.editTextTextDescription);
        dataBaseDAO = BusinessCardDatabase.getDBInstance(this).databaseDao();
    }

    private final int PICK_IMAGE = 100;

    public void takePicture(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            try {
                imageUri = data.getData();
                bmpImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImageView.setImageBitmap(bmpImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveBusinessCard(View view) {
        if(title.getText().toString().isEmpty() || bmpImage == null){
            Toast.makeText(
                    this,
                    "Brakuje tytułu lub obrazu",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            BusinessCard businessCard = new BusinessCard();
            businessCard.setTitle(title.getText().toString());
            businessCard.setPhoneNumber(phone.getText().toString());
            businessCard.setAddress(address.getText().toString());
            businessCard.setDescription(description.getText().toString());
            businessCard.setImage(DataConverter.convertImage2byteArray(bmpImage));

            dataBaseDAO.insertBusinessCard(businessCard);
            Toast.makeText(
                    this,
                    "Wizytówka została zapisana",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


}