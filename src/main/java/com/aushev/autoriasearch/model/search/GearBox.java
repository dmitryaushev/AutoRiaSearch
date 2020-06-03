package com.aushev.autoriasearch.model.search;

public enum GearBox {

    MANUAL("1", "Ручная / Механика"),
    AUTOMATIC("2", "Автомат");

    private String gearBox;
    private String title;

    GearBox(String gearBox, String title) {
        this.gearBox = gearBox;
        this.title = title;
    }

    public String getGearBox() {
        return gearBox;
    }

    public String getTitle() {
        return title;
    }
}
