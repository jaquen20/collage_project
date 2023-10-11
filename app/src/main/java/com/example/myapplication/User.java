package com.example.myapplication;

public class User {
    private String name, email, password, city;
    private String phone;
    private String UserID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", UserID='" + UserID + '\'' +
                '}';
    }

    public User(String userID, String name, String email, String password, String city, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone = phone;
        UserID = userID;
    }
    public User(){

    }
}