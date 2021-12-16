package com.i170014.i170014_i170161_a4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_one);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.ayazafzal.i170014_i170161_A4", Context.MODE_PRIVATE);
        String email = "email";
        String ifDefault= prefs.getString(email, "None");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(ifDefault.equals("None")){
                    Intent inX=new Intent(MainActivity.this,SignUp_Two.class);
                    startActivity(inX);
                }else{
                    //Intent inX=new Intent(MainActivity.this,Home_Five.class);
                    //inX.putExtra("UserId",ifDefault);
                    //startActivity(inX);
                }

            }
        }, 5000);
    }
}