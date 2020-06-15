package com.aushev.autoriasearch.model.search;

public enum State {

    VINNYTSIA("1", "Винницкая"),
    LVIV("5", "Львовская"),
    KIEV("10", "Киевская"),
    ODESSA("12", "Одесская"),
    KHERSON("23", "Херсонская");

    private String value;
    private String name;

    State(String value, String name) {
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
