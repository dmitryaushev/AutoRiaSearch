package com.aushev.autoriasearch.model.search;

public enum Type {

    PETROL("1", "Бензин"),
    DIESEL("2", "Дизель"),
    ELECTRO("6", "Электро");

    private String type;
    private String title;

    Type(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
