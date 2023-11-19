package com.example.badminton_court;

public class User {
    private String email;
    private String name;

    private int age, id;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    // Add getter and setter methods for other user details as needed
}