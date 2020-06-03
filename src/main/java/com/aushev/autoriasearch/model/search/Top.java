package com.aushev.autoriasearch.model.search;

public enum Top {
    TODAY("2", "За сегодня"),
    ONE_HOUR("1", "За час"),
    THREE_HOURS("8", "За 3 часа"),
    SIX_HOURS("9", "За 6 часов"),
    TWELVE_HOURS("14", "За 12 часов"),
    DAY("11", "За сутки"),
    TWO_DAYS("10", "За 2 дня"),
    THREE_DAYS("3", "За 3 дня"),
    WEEK("4", "За неделю"),
    MONTH("5", "За месяц"),
    THREE_MONTHS("6", "За 3 месяца");

    private String top;
    private String title;

    Top(String top, String title) {
        this.top = top;
        this.title = title;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
