package com.aushev.autoriasearch.model.search;

public enum Color {

    BLACK("2", "Черный"),
    BLUE("3", "Синий"),
    GREY("8", "Серый"),
    RED("13", "Красный"),
    WHITE("15", "Белый");

    private String value;
    private String name;

    Color(String value, String name) {
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
