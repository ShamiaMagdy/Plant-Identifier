package com.example.plantapp;

import android.graphics.Bitmap;

public class FavouriteModel {
    private String name,description;
    private Bitmap image_id;
   private int id;

    public FavouriteModel()
    {

    }
    public FavouriteModel(String name, String description, Bitmap image_id , int id) {
        this.name = name;
        this.description = description;
        this.image_id = image_id;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage_id() {
        return image_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_id(Bitmap image_id) {
        this.image_id = image_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
