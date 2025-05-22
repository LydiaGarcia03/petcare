package com.lydia.petcare.models;

public class Mood {
    private String date;
    private String mood;
    private String petName;

    public Mood(String date, String mood, String petName) {
        this.date = date;
        this.mood = mood;
        this.petName = petName;
    }

    public String getDate() { return date; }
    public String getMood() { return mood; }
    public String getPetName() { return petName; }
}
