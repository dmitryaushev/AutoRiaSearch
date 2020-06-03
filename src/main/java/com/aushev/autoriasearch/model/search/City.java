package com.aushev.autoriasearch.model.search;

import java.util.Arrays;

public enum City {

    VINNYTSIA("1", "Винница", State.VINNYTSIA), ZHMERYNKA("27", "Жмеринка", State.VINNYTSIA),
    LVIV("5", "Львов", State.LVIV), TRUSKAVETS("307", "Трускавец", State.LVIV),
    ODESSA("12", "Одесса", State.ODESSA), YUZHNE("643", "Южный", State.ODESSA),
    KIEV("10", "Киев", State.KIEV), BORYSPIL("212", "Борисполь", State.KIEV),
    KHERSON("23", "Херсон", State.KHERSON), SKADOVSK("487", "Скадовск", State.KHERSON);

    private String city;
    private String title;
    private State state;

    City(String city, String title, State state) {
        this.city = city;
        this.title = title;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getTitle() {
        return title;
    }

    public State getState() {
        return state;
    }
}
