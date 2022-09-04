package com.ujjman.money.expensetracker;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class User {
    public String email;
    public String password;
    public String name;
    public String phoneNumber;
    public User()
    {}
    public User(String email,String password,String name,String phoneNumber)
    {
        this.email=email.trim();
        this.password=password.trim();
        this.name=name.trim();
        this.phoneNumber=phoneNumber.trim();
    }
}
