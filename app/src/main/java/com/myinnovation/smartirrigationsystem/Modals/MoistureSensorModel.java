package com.myinnovation.smartirrigationsystem.Modals;

public class MoistureSensorModel {
    private String sensorId, sensorValue;
    private Boolean state;

    public MoistureSensorModel(String sensorId, String sensorValue, Boolean state) {
        this.sensorId = sensorId;
        this.sensorValue = sensorValue;
        this.state = state;
    }

    public MoistureSensorModel() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
