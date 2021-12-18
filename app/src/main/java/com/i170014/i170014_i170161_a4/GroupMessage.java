package com.i170014.i170014_i170161_a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class GroupMessage {
    String MessageType,GroupMessageID,	GroupID,	UserID,	MessageText,	MessageImage,	MessageRead,	MessageHour,	MessageMin;
    Bitmap GroupMessageImage;

    public GroupMessage(String Messagetype,String groupMessageID, String groupID, String userID, String messageText, String messageRead, String messageHour, String messageMin) {
        GroupMessageID = groupMessageID;
        GroupID = groupID;
        UserID = userID;
        MessageText = messageText;
        MessageRead = messageRead;
        MessageHour = messageHour;
        MessageMin = messageMin;
        this.MessageType=Messagetype;
    }

    public GroupMessage(String Messagetype,String groupMessageID, String groupID, String userID,  String messageImage, String messageRead, String messageHour, String messageMin,int a) {
        GroupMessageID = groupMessageID;
        GroupID = groupID;
        UserID = userID;
        byte[] decodedByte = Base64.decode(messageImage, 0);
        GroupMessageImage= BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        MessageRead = messageRead;
        MessageHour = messageHour;
        MessageMin = messageMin;
        this.MessageType=Messagetype;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getGroupMessageID() {
        return GroupMessageID;
    }

    public void setGroupMessageID(String groupMessageID) {
        GroupMessageID = groupMessageID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public String getMessageImage() {
        return MessageImage;
    }

    public void setMessageImage(String messageImage) {
        MessageImage = messageImage;
    }

    public String getMessageRead() {
        return MessageRead;
    }

    public void setMessageRead(String messageRead) {
        MessageRead = messageRead;
    }

    public String getMessageHour() {
        return MessageHour;
    }

    public void setMessageHour(String messageHour) {
        MessageHour = messageHour;
    }

    public String getMessageMin() {
        return MessageMin;
    }

    public void setMessageMin(String messageMin) {
        MessageMin = messageMin;
    }

    public Bitmap getGroupMessageImage() {
        return GroupMessageImage;
    }

    public void setGroupMessageImage(Bitmap groupMessageImage) {
        GroupMessageImage = groupMessageImage;
    }
}
