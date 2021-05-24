package com.bielskiAdam.wizytownik.DataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "BusinessCard")
public class BusinessCard {
    @PrimaryKey(autoGenerate = true)
    int uid;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "phoneNumber")
    String phoneNumber;
    @ColumnInfo(name = "address")
    String address;
    @ColumnInfo(name = "description")
    String description;
//    Date dateCreate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] image;

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

//    public Date getDateCreate() {
//        return dateCreate;
//    }

    public byte[] getImage() {
        return image;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void setDateCreate(Date dateCreate) {
//        this.dateCreate = dateCreate;
//    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
