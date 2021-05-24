package com.bielskiAdam.wizytownik.DataModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataBaseDAO {
    @Query("Select * from BusinessCard")
    List<BusinessCard> getAllBusinessCard();

    @Insert
    void insertBusinessCard(BusinessCard businessCard);

    @Update
    void updateBusinessCard(BusinessCard businessCard);

    @Delete
    void deleteBusinessCard(BusinessCard businessCard);
}
