package com.i170014.i170014_i170161_a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class messageData implements Comparable<messageData> {
    String ID,Sender,Reciever,Hour,Minute,Call,Text,Read,Screenshot;
    String messageType;
    Bitmap messageImage;


    public String getRead() {
        return Read;
    }

    public void setRead(String read) {
        Read = read;
    }

    public messageData(String MesTy,String id, String sender, String reciever, String hour, String minute, String text, String read, String screenshot) {
        ID=id;
        messageType=MesTy;
        Sender = sender;
        Reciever = reciever;
        Hour = hour;
        Minute = minute;
        Text = text;
        Read=read;
        Screenshot = screenshot;
    }
    public messageData(String MesTy,String id, String sender, String reciever, String hour, String minute, String image, String read, String screenshot,int a) {
        ID=id;
        messageType=MesTy;
        Sender = sender;
        Reciever = reciever;
        Hour = hour;
        Minute = minute;
        Text = "0";
        byte[] decodedByte = Base64.decode(image, 0);
        this.messageImage= BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        Read=read;
        Screenshot = screenshot;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
    }

    public String getCall() {
        return Call;
    }

    public void setCall(String call) {
        Call = call;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Bitmap getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(Bitmap messageImage) {
        this.messageImage = messageImage;
    }

    public String getScreenshot() {
        return Screenshot;
    }

    public void setScreenshot(String screenshot) {
        Screenshot = screenshot;
    }

    @Override
    public int compareTo(messageData messageData) {
        Integer.parseInt("200");
        int hour1,hour2;
        int minute1,minute2;
        hour1=Integer.parseInt(this.Hour);
        hour2=Integer.parseInt(messageData.getHour());
        minute1=Integer.parseInt(this.Minute);
        minute2=Integer.parseInt(messageData.getMinute());
        if(hour1<hour2){
            return -1;
        }else if(hour1==hour2 && minute1<minute2){
            return  -1;
        }else{
            return  1;
        }
    }
}

