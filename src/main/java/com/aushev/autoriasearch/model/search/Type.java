package com.aushev.autoriasearch.model.search;

public enum Type {

    PETROL("1", "Бензин"),
    DIESEL("2", "Дизель"),
    ELECTRO("6", "Электро");

    private String value;
    private String name;

    Type(String value, String name) {
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
