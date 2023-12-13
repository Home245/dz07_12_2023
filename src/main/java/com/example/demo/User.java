package com.example.demo;
public class User {
    private int age;
    private String username;
    private String password;
    private String id;

    public User(String name, String password, String id, int age) {
        this.age = age;
        this.username = name;
        this.password = password;
        this.id=id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
