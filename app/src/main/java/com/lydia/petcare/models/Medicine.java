package com.lydia.petcare.models;

public class Medicine {
    private String name;
    private String time;
    private String petName;

    public Medicine(String name, String time, String petName) {
        this.name = name;
        this.time = time;
        this.petName = petName;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getPetName() { return petName; }
}

