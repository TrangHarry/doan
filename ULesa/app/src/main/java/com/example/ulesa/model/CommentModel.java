package com.example.ulesa.model;

import com.google.firebase.database.DataSnapshot;

public class CommentModel {
    UserModel userModel;
    String cmt;
    float evaluate;
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public CommentModel(String cmt, float evaluate, String userID) {
        this.cmt = cmt;
        this.evaluate = evaluate;
        this.userID = userID;
    }

    public CommentModel(String name, DataSnapshot data) {
        this.evaluate = data.child("Evaluate").getValue(Float.class);
        this.cmt = data.child("Value").getValue(String.class);
        this.userID = data.child("IDUser").getValue(String.class);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public float getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "userModel=" + userModel +
                ", cmt='" + cmt + '\'' +
                ", evaluate=" + evaluate +
                ", userID='" + userID + '\'' +
                '}';
    }
}
