package com.bielskiAdam.wizytownik.DataBaseModel;


public class BusinessCard {
    private int id;
    private String title;
    private String phoneNumber;
    private String address;
    private String description;
    private byte[] image;


    public BusinessCard(String title, String phoneNumber, String address, String description, byte[] image){
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    public BusinessCard(int id, String title, String phoneNumber, String address, String description, byte[] image) {
        this.id = id;
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}