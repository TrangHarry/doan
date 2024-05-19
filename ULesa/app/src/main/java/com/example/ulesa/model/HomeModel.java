package com.example.ulesa.model;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class HomeModel implements Serializable {
    private String Review;
    private String nameID;
    private int evaluate;
    private String pictureMain;
    private int price;
    private int cus;
    private String roomName;
    private  int viewBooking;
    private int viewEvaluate;
    private String location;
    private String[] listImage;
    public int getCus() {
        return cus;
    }

    public void setCus(int cus) {
        this.cus = cus;
    }

    public int getViewEvaluate() {
        return viewEvaluate;
    }

    public void setViewEvaluate(int viewEvaluate) {
        this.viewEvaluate = viewEvaluate;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String Review) {
        this.Review = Review;
    }
    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPictureMain() {
        return pictureMain;
    }

    public void setPictureMain(String pictureMain) {
        this.pictureMain = pictureMain;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getViewBooking() {
        return viewBooking;
    }

    public void setViewBooking(int viewBooking) {
        this.viewBooking = viewBooking;
    }

    public void setViewEvalute(int viewEvalute) {
        this.viewEvaluate = viewEvalute;
    }

    public HomeModel() {
    }

    public HomeModel(String name, DataSnapshot data) {
        this.nameID = name;
        this.cus = data.child("Cus").getValue(Integer.class);
        this.Review = data.child("Review").getValue(String.class);
        if(this.Review != null) {
            this.Review = this.Review.replace("\\\n", System.getProperty("line.separator"));
        } else this.Review = "";
        this.evaluate = data.child("Evaluate").getValue(Integer.class);
        this.location = data.child("Location").getValue(String.class);
        this.pictureMain = data.child("PictureMain").getValue(String.class);
        this.price = data.child("Price").getValue(Integer.class);
        this.roomName = data.child("RoomName").getValue(String.class);
        this.viewBooking = data.child("ViewBooking").getValue(Integer.class);
        this.viewEvaluate = data.child("ViewEvaluate").getValue(Integer.class);
        String list = data.child("AnyPic").getValue(String.class);
        String[] listImage = list.split("~~~");
        this.listImage = listImage;

    }

    public String[] getListImage() {
        return listImage;
    }

    public void setListImage(String[] listImage) {
        this.listImage = listImage;
    }

    @Override
    public String toString() {
        return "HomeModel{" +
                "name='" + nameID + '\'' +
                "review='" + Review + '\'' +
                "Cus='" + cus + '\'' +
                ", evaluate=" + evaluate  + '\'' +
                ", location='" + location + '\'' +
                ", pictureMain='" + pictureMain + '\'' +
                ", price='" + price + '\'' +
                ", roomName='" + roomName + '\'' +
                ", viewBooking=" + viewBooking +
                ", viewEvalute=" + viewEvaluate +
                '}';
    }
}
