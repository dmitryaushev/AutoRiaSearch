package com.aushev.autoriasearch.model.search;

public enum Currency {
    USD("1", "$"),
    EUR("2", "€"),
    UAH("3", "₴");


    private String value;
    private String name;

    Currency(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
