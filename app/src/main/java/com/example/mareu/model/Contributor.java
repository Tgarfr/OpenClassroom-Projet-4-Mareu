package com.example.mareu.model;

public class Contributor {

    private int id;
    private String name;

    public Contributor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
