package com.lydia.petcare.models;

public class Event {
    private String date;
    private String type;
    private String petName;

    public Event(String date, String type, String petName) {
        this.date = date;
        this.type = type;
        this.petName = petName;
    }

    public String getDate() { return date; }
    public String getType() { return type; }
    public String getPetName() { return petName; }
}
