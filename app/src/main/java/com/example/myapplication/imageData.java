package com.example.myapplication;



public class imageData {
    private String imageURL, caption;

    public imageData(){

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public imageData(String imageURL, String caption) {
        this.imageURL = imageURL;
        this.caption = caption;
    }
}