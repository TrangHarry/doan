package com.example.ulesa.model;

public class PriceVND {
    public static String changeToVND(int price) {
        String result = " VND";
        int x = price;
        while (x> 1000) {
            int y = x%1000;
            String A = "";
            if(y < 100 && y >= 10) A += ("0" + y);
            else if(y < 10) A += ("00" + y);
            else A += y;
            result = A + result;
            x = x/1000;
            if(x > 0) result = "." + result;
        }
        if(x >0) result = x + result;
        return result;
    }
}
