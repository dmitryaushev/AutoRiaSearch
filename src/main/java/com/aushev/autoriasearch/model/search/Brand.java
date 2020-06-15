package com.aushev.autoriasearch.model.search;

public enum Brand {

    BMW("9", "BMW"),
    HONDA("28", "Honda"),
    MERCEDES("48", "Mercedes-Benz"),
    TOYOTA("79", "Toyota"),
    TESLA("2233", "Tesla");

    private String value;
    private String name;

    Brand(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
