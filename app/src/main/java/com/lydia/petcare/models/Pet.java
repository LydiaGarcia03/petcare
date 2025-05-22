package com.lydia.petcare.models;

public class Pet {
    private String name;
    private String species;
    private String breed;

    public Pet(String name, String species, String breed) {
        this.name = name;
        this.species = species;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }
}
