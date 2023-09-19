package com.example.myapplication;



public class imageData{
    private String imageURL;

    public imageData(String toString) {
        this.imageURL = toString;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "imageData{" +
                "imageURL='" + imageURL + '\'' +
                '}';
    }
}