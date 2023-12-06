package com.example.garage;
public class ListItem {
    private final String brand;
    private String model;
    private String year;
    private final String price;
    int image;

    public ListItem(String brand, String model, String year, String price, Integer image) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.image = image;
    }

    public ListItem(String brand, String price, Integer image) {
        this.brand = brand;
        this.price = price;
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
