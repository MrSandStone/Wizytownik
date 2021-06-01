package com.bielskiAdam.wizytownik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.AlteredCharSequence;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bielskiAdam.wizytownik.DataBaseModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataBaseModel.DataConverter;
import com.bielskiAdam.wizytownik.DataBaseModel.DatabaseFactory;

import java.util.ArrayList;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {
    private DatabaseFactory mDatabase;


    ImageView selectedImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView cardView = findViewById(R.id.BusinessCardListActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        cardView.setLayoutManager(linearLayoutManager);
        cardView.setHasFixedSize(true);


        mDatabase = new DatabaseFactory(this);

        ArrayList<BusinessCard> allCards = mDatabase.listBusinessCards();
        if( allCards.size() > 0) {
            cardView.setVisibility(View.VISIBLE);
            BusinessCardRecycler mRecycler = new BusinessCardRecycler(this, allCards);
            cardView.setAdapter(mRecycler);
        } else {
            cardView.setVisibility(View.GONE);
            Toast.makeText(
                    this,
                    "Brak wizytówek. Dodaj nową.",
                    Toast.LENGTH_SHORT
            ).show();
        }
        Button buttonAdd = findViewById(R.id.ButtonCreateNewCard);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if(resultCode == RESULT_OK){
                    try {
                        Uri selectedImageUri = data.getData();
                        selectedImageView.setImageURI(selectedImageUri);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
    }

    private void addDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_create_new, null);
        final EditText titleField = subView.findViewById(R.id.editTextTitleBusinessCard);
        final EditText phoneField = subView.findViewById(R.id.editTextPhone);
        final EditText addressField = subView.findViewById(R.id.editTextTextAddress);
        final EditText descritpionField = subView.findViewById(R.id.editTextTextDescription);
        final ImageView imageField = subView.findViewById(R.id.selectedImage);

        final Button takePicture = subView.findViewById(R.id.takePicture);

        selectedImageView = imageField;
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodawanie wizytówki");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String title = titleField.getText().toString();
                final String phone = phoneField.getText().toString();
                final String address = addressField.getText().toString();
                final String description = descritpionField.getText().toString();
                final byte[] image = DataConverter.convertImageViewToByteArray(imageField);
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(MainActivity.this, "Coś jest nie tak. Sprawdź wartości w polach.", Toast.LENGTH_SHORT).show();
                } else {
                    BusinessCard newBusinessCard = new BusinessCard(title, phone, address, description, image);
                    mDatabase.addCard(newBusinessCard);
                    finish();
                    startActivity(getIntent());;
                }

            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Anulowano", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }



    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
    public void openCreateNewActivity(View view) {
        Intent intent = new Intent(this, CreateNewActivity.class);
        startActivity(intent);
    }
}