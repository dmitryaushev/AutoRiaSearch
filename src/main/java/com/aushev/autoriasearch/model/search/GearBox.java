package com.aushev.autoriasearch.model.search;

public enum GearBox {

    MANUAL("1", "Ручная / Механика"),
    AUTOMATIC("2", "Автомат");

    private String value;
    private String name;

    GearBox(String value, String name) {
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
