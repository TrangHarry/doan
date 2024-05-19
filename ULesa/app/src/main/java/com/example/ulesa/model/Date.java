package com.example.ulesa.model;

import androidx.annotation.NonNull;

public class Date {
    private int date;
    private int month;
    private int year;

    public Date(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public Date(String data) {
        String[] dataDate = data.split("/");
        this.date = Integer.parseInt(dataDate[0]);
        this.month = Integer.parseInt(dataDate[1]);
        this.year = Integer.parseInt(dataDate[2]);
    }

    public int soSanh(Date i) {
        int result = 0;
        if (this.year == i.year) {
            if (this.month == i.month) {
                return this.date - i.date;
            }
            return this.month - i.month;
        } else return this.year - i.year;
    }

    @NonNull
    @Override
    public String toString() {
        return this.date + "/" + this.month + "/" + this.year;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
