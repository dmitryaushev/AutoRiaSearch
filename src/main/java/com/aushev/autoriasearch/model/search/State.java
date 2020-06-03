package com.aushev.autoriasearch.model.search;

public enum State {

    VINNYTSIA("1", "Винницкая"),
    LVIV("5", "Львовская"),
    KIEV("10", "Киевская"),
    ODESSA("12", "Одесская"),
    KHERSON("23", "Херсонская");

    private String state;
    private String title;

    State(String state, String title) {
        this.state = state;
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }
}
