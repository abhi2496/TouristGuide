package com.example.abhishekkoranne.guide;

public class Places {
    //Name of place
    private String name;

    //Description about place
    private String description;

    //Image of place
    private int image;

    //Distance from current location
    private String distance;

    public Places(String name, String description, int image, String distance) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
