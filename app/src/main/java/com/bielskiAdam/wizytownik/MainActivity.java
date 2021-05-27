package com.bielskiAdam.wizytownik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;




public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openCreateNewActivity(View view) {
        Intent intent = new Intent(this, CreateNewActivity.class);
        startActivity(intent);
    }

    public void showBusinessCard(View view) {
        Intent intent = new Intent(this, ShowCardActivity.class);
        startActivity(intent);
    }
}