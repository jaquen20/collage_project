package com.example.myapplication;

public class garbageClass {

    private String imageURL;


    public garbageClass( String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageURL) {
        this.imageURL = imageURL;
    }


    public  garbageClass(){

    }

    @Override
    public String toString() {
        return "garbageClass{" +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}


