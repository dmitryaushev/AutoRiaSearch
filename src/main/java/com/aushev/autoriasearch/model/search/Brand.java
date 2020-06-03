package com.aushev.autoriasearch.model.search;

public enum Brand {

    BMW("9", "BMW"),
    HONDA("28", "Honda"),
    MERCEDES("48", "Mercedes-Benz"),
    TOYOTA("79", "Toyota"),
    TESLA("2233", "Tesla");

    private String brand;
    private String title;

    Brand(String brand, String title) {
        this.brand = brand;
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public String getTitle() {
        return title;
    }
}
