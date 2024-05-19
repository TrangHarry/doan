package com.example.ulesa.model;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String Name;
    private String BirthDay;
    private String Phone;
    private String Gmail;
    private String Gender;
    private String IDUser;
    public String getIDUser(){
        return IDUser;
    }
    public  void setIDUser(String IDUser){
        this.IDUser = IDUser;
    }
 
    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }
    

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String Gmail) {
        this.Gmail = Gmail;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }
    public UserModel() {
    }

    public UserModel(String ID, DataSnapshot data) {
        this.IDUser = ID;
        this.BirthDay = data.child("BirthDay").getValue(String.class);
        this.Name = data.child("Name").getValue(String.class);
        this.Gmail = data.child("Gmail").getValue(String.class);
        this.Gender = data.child("Gender").getValue(String.class);
        this.Phone = data.child("Phone").getValue(String.class);

    }


    @Override
    public String toString() {
        return "UserModel{" +
                "ID='" + IDUser + '\'' +
                "BirthDay='" + BirthDay + '\'' +
                "Name='" + Name + '\'' +
                ", Gmail='" + Gmail + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Phone=" + Phone +
                '}';
    }
}
