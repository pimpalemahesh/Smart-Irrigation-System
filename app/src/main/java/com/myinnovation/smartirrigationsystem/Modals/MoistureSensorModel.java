package com.myinnovation.smartirrigationsystem.Modals;

public class MoistureSensorModel {
    private String sensorId;
    int sensorValue;
    private Boolean state;

    public MoistureSensorModel(String sensorId, int sensorValue, Boolean state) {
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

    public int getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(int sensorValue) {
        this.sensorValue = sensorValue;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
