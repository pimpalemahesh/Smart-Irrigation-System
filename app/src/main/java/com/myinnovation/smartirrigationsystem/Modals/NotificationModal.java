package com.myinnovation.smartirrigationsystem.Modals;

public class NotificationModal {
    String nId, nText, nType;

    public NotificationModal(String nId, String nText, String nType) {
        this.nId = nId;
        this.nText = nText;
        this.nType = nType;
    }

    public NotificationModal() {
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
