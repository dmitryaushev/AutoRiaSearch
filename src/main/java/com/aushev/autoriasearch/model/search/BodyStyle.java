package com.aushev.autoriasearch.model.search;

public enum BodyStyle {

    WAGON("2", "Универсал"),
    SEDAN("3", "Седан"),
    HATCHBACK("4", "Хэтчбек"),
    SUV("5", "Внедорожник"),
    COUPE("6", "Купе");

    private String bodyStyle;
    private String title;

    BodyStyle(String bodyStyle, String title) {
        this.bodyStyle = bodyStyle;
        this.title = title;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public String getTitle() {
        return title;
    }
}
