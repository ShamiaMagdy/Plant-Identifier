package com.example.plantapp;

import android.graphics.Bitmap;

public class AdapterModel {
    private String name,description;
    private Bitmap image;
   private int id;

    public AdapterModel()
    {

    }
    public AdapterModel(String name, String description, Bitmap image , int id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage_id() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_id(Bitmap image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
