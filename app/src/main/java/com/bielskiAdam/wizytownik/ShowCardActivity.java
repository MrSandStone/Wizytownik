package com.bielskiAdam.wizytownik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bielskiAdam.wizytownik.DataModel.BusinessCardDatabase;
import com.bielskiAdam.wizytownik.DataModel.DataBaseDAO;

public class ShowCardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBaseDAO dataBaseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        recyclerView = findViewById(R.id.businessCardRecyclerView);
        dataBaseDAO = BusinessCardDatabase.getDBInstance(this).databaseDao();

        BusinessCardRecycler businessCardRecycler = new BusinessCardRecycler(dataBaseDAO.getAllBusinessCard());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(businessCardRecycler);

    }
}