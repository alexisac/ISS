package com.example.hospitalpharmacy.services;

public class ServiceException extends Exception{
    String MyMessage;

    public ServiceException(String myMessage) {
        this.MyMessage = myMessage;
    }

    public String getMyMessage() {
        return MyMessage;
    }
}
