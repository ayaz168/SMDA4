package com.i170014.i170014_i170161_a4;

public class notificationModel {
    String userEmail,DeviceID,Message,TimeSlot;

    public notificationModel() {
    }

    public notificationModel(String userEmail, String deviceID, String message, String timeSlot) {
        this.userEmail = userEmail;
        DeviceID = deviceID;
        Message = message;
        TimeSlot = timeSlot;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }
}
