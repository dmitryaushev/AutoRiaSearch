package com.aushev.autoriasearch.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "config")
@Component
public class Config {

    private int id;
    private String title;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
