package com.example.plantapp;

public class HomeModel {
    private String name;
    private int image_id;

    public HomeModel()
    {

    }
    public HomeModel(String name, int image_id) {
        this.name = name;
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
