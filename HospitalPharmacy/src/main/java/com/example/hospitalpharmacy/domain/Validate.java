package com.example.hospitalpharmacy.domain;

public class Validate {

    public boolean validName(String name){
        return !name.isBlank() && name.length() >= 3;
    }
    public boolean validCode(int code){
        return code >= 1000 && code <= 9999;
    }

    public boolean validQuantity(int quantity){
        return quantity > 0 && quantity < 1000;
    }
}

