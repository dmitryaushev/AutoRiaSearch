package com.aushev.autoriasearch.model.search;

public enum Currency {
    USD("1", "$"),
    EUR("2", "€"),
    UAH("3", "₴");


    private String currency;
    private String title;

    Currency(String currency, String title) {
        this.currency = currency;
        this.title = title;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
