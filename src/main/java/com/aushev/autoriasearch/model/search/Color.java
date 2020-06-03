package com.aushev.autoriasearch.model.search;

public enum Color {

    BLACK("2", "Черный"),
    BLUE("3", "Синий"),
    GREY("8", "Серый"),
    RED("13", "Красный"),
    WHITE("15", "Белый");

    private String color;
    private String title;

    Color(String color, String title) {
        this.color = color;
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
