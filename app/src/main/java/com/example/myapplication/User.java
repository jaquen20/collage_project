package com.example.myapplication;

public class User {
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private String name, email, password, city;
    private Integer phone;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getPhone() {
        return phone;
    }



        public User() {

        }

    public User(String name, String email, String password,String city,Integer phone) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.city=city;
            this.phone=phone;
        }
    }
