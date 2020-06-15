package com.aushev.autoriasearch.model.search;

public enum Model {

    MODELS("31567", "Model S"), MODEL3("47858", "Model 3"),
    MODELX("37711", "Model X"),

    I3("44838", "I3"), I8("44537", "I8"), X3("1866", "X3"),
    X5("96", "X5"), X6("2153", "X6"),

    A_CLASS("423", "A Class"), B_CLASS("2622", "B class"),
    C_CLASS("424", "C Class"), E_CLASS("428", "E Class"), S_CLASS("431", "S Class"),

    AVENSIS("696", "Avensis"), CAMRY("698", "Camry"), COROLLA("702", "Corolla"),
    SUPRA("718", "Supra"), YARIS("720", "Yaris"),

    ACCORD("262", "Accord"), CIVIC("265", "Civic"), CR_V("269", "CR-V"),
    JAZZ("274", "Jazz"), LEGEND("275", "Legend");

    private String value;
    private String name;

    Model(String value, String name) {
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
