package com.myinnovation.smartirrigationsystem.Modals;

public class Notification {
    String nId, nText, nType;

    public Notification(String nId, String nText, String nType) {
        this.nId = nId;
        this.nText = nText;
        this.nType = nType;
    }

    public Notification() {
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getnText() {
        return nText;
    }

    public void setnText(String nText) {
        this.nText = nText;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }
}
