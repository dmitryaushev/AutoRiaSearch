package com.aushev.autoriasearch.model.search;

public enum City {

    VINNYTSIA("1", "Винница"), ZHMERYNKA("27", "Жмеринка"),
    LVIV("5", "Львов"), TRUSKAVETS("307", "Трускавец"),
    ODESSA("12", "Одесса"), YUZHNE("643", "Южный"),
    KIEV("10", "Киев"), BORYSPIL("212", "Борисполь"),
    KHERSON("23", "Херсон"), SKADOVSK("487", "Скадовск");

    private String value;
    private String name;

    City(String value, String name) {
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
