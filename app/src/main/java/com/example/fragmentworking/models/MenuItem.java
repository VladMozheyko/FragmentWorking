package com.example.fragmentworking.models;

public class MenuItem {  // Класс модели
    private int img;
    private String title;
    private String description;
    private int price;

    public MenuItem(String title, int img, String description, int price) {
        this.title = title;
        this.img = img;
        this.description = description;
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
