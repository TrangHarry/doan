package com.example.ulesa.model;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class OrderModel implements Serializable {
    private String billID;
    private String nameID;
    private int total;
    private String pictureMain;
    private String roomName;

    private int status;
    private String idUser;
    private String date;
    private String timeIn;
    private String timeOut;
    private int wc;

    public int getCus() {
        return cus;
    }

    public void setCus(int cus) {
        this.cus = cus;
    }

    private int cus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public OrderModel(String billID, String nameID, int total, String pictureMain, String roomName, int status, String idUser, String date, int cus
            , String timeIn, String timeOut, int wc) {
        this.billID = billID;
        this.nameID = nameID;
        this.total = total;
        this.pictureMain = pictureMain;
        this.roomName = roomName;
        this.status = status;
        this.idUser = idUser;
        this.date = date;
        this.cus = cus;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.wc = wc;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public void setStatus(int billID) {
        this.status = status;
    }
    public String getbillID() {
        return billID;
    }

    public void setbillID(String billID) {
        this.billID = billID;
    }
    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public int gettotal() {
        return total;
    }

    public void settotal(int total) {
        this.total = total;
    }

    public String getPictureMain() {
        return pictureMain;
    }

    public void setPictureMain(String pictureMain) {
        this.pictureMain = pictureMain;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    public OrderModel() {

    }

    public OrderModel(String IDBill, DataSnapshot data) {
        this.billID = IDBill;
        this.status = data.child("Status").getValue(Integer.class);
        this.total = data.child("Total").getValue(Integer.class);
        this.pictureMain = data.child("PictureMain").getValue(String.class);
        this.nameID = data.child("IDRoom").getValue(String.class);
        this.idUser = data.child("IDUser").getValue(String.class);
        this.date = data.child("Date").getValue(String.class);
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "billID='" + billID + '\'' +
                ", nameID='" + nameID + '\'' +
                ", total=" + total +
                ", pictureMain='" + pictureMain + '\'' +
                ", roomName='" + roomName + '\'' +
                ", status=" + status +
                ", idUser='" + idUser + '\'' +
                ", date='" + date + '\'' +
                ", timeIn='" + timeIn + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", wc=" + wc +
                ", cus=" + cus +
                '}';
    }
}
