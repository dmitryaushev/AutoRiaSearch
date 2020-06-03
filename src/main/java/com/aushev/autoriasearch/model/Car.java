package com.aushev.autoriasearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {

    private String title;
    private String locationCityName;
    private int USD;
    private int UAH;
    private int EUR;
    private AutoData autoData;
    private PhotoData photoData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public int getUSD() {
        return USD;
    }

    public void setUSD(int USD) {
        this.USD = USD;
    }

    public int getUAH() {
        return UAH;
    }

    public void setUAH(int UAH) {
        this.UAH = UAH;
    }

    public int getEUR() {
        return EUR;
    }

    public void setEUR(int EUR) {
        this.EUR = EUR;
    }

    public AutoData getAutoData() {
        return autoData;
    }

    public void setAutoData(AutoData autoData) {
        this.autoData = autoData;
    }

    public PhotoData getPhotoData() {
        return photoData;
    }

    public void setPhotoData(PhotoData photoData) {
        this.photoData = photoData;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutoData {

        private int autoId;
        private int year;
        private String description;
        private String race;
        private String gearboxName;
        private String fuelName;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getGearboxName() {
            return gearboxName;
        }

        public void setGearboxName(String gearboxName) {
            this.gearboxName = gearboxName;
        }

        public String getFuelName() {
            return fuelName;
        }

        public void setFuelName(String fuelName) {
            this.fuelName = fuelName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhotoData {

        private String seoLinkSX;

        public String getSeoLinkSX() {
            return seoLinkSX;
        }

        public void setSeoLinkSX(String seoLinkSX) {
            this.seoLinkSX = seoLinkSX;
        }
    }
}
