package com.aushev.autoriasearch.model.search;

public enum BodyStyle {

    WAGON("2", "Универсал"),
    SEDAN("3", "Седан"),
    HATCHBACK("4", "Хэтчбек"),
    SUV("5", "Внедорожник"),
    COUPE("6", "Купе");

    private String value;
    private String name;

    BodyStyle(String value, String name) {
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
