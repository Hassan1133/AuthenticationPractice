package com.example.authentication;

public class User {
    private String name;
    private String email;
    private String password;
    private String cla_ss;
    private String rollNo;
    private String phone;
    private String id;
    private String gender;

    public User(String name, String email, String password, String cla_ss, String rollNo, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cla_ss = cla_ss;
        this.rollNo = rollNo;
        this.phone = phone;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCla_ss() {
        return cla_ss;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }
}
