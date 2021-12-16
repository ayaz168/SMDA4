package com.i170014.i170014_i170161_a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class userData {
    String Id,Email,Pass,FirstName,LastName,Gender,Bio,Status,Phone;
    Bitmap Image;

    public String getPhone() {
        return Phone;
    }
    public void DisplayUser(){
        Log.d("User",this.Email);
        Log.d("User",this.Pass);
        Log.d("User",this.FirstName);
        Log.d("User",this.LastName);
        Log.d("User",this.Phone);

    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return Id;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public void setId(String id) {
        Id = id;
    }

    public userData(String id, String email, String pass, String firstName, String lastName, String gender, String bio, String status, String phone, Bitmap image) {
        Id=id;
        Email = email;
        Pass = pass;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Bio = bio;
        Status = status;
        Phone=phone;
        Image = image;
    }public userData(String id, String email, String pass, String firstName, String lastName, String gender, String bio, String status, String phone) {
        Id=id;
        Email = email;
        Pass = pass;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Bio = bio;
        Status = status;
        Phone=phone;
        Image = null;
    }public userData(String email, String pass) {
        Email = email;
        Pass = pass;
    }
    public userData(String id, String email, String pass, String firstName, String lastName, String gender, String bio, String status, String phone,String Img) {
        Id=id;
        Email = email;
        Pass = pass;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Bio = bio;
        Status = status;
        Phone=phone;
        byte[] decodedByte = Base64.decode(Img, 0);
        Image= BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
