package com.i170014.i170014_i170161_a4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogIn_Three extends AppCompatActivity {
    EditText newEmailScreenThree,newPasswordScreenThree;
    TextView forgotPasswordScreenThree,registerNowThree;
    Button logInScreenThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_three);
        newEmailScreenThree=findViewById(R.id.newEmailScreenThree);
        newPasswordScreenThree=findViewById(R.id.newPasswordScreenThree);
        forgotPasswordScreenThree=findViewById(R.id.forgotPasswordScreenThree);
        forgotPasswordScreenThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogIn_Three.this,
                        "Please Contact Admin.",
                        Toast.LENGTH_LONG).show();
            }
        });
        logInScreenThree=findViewById(R.id.logInScreenThree);
        logInScreenThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=newEmailScreenThree.getText().toString();
                String pass=newPasswordScreenThree.getText().toString();
                postData(email,pass);
                //userData usX=getCurrentUser(email);
                /*if(usX.getPass().equals(pass)){
                    //Intent inXX=new Intent(LogIn_Three.this,Home_Five.class);
                    //inXX.putExtra("UserId",email);
                    //startActivity(inXX);
                }
                else{
                    Toast.makeText(LogIn_Three.this,
                            "Invalid Credentials, try again..",
                            Toast.LENGTH_LONG).show();
                }*/

            }
        });
        registerNowThree=findViewById(R.id.registerNowThree);
        registerNowThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(LogIn_Three.this,SignUp_Two.class);
                startActivity(inX);
            }
        });

    }
    public void postData(String email, String Pass){
        String url="http://192.168.18.12/A4/login.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Login Success")){
                            Toast.makeText(LogIn_Three.this,
                                    "Login Success! ",
                                    Toast.LENGTH_LONG).show();
                            Intent inX=new Intent(LogIn_Three.this,Home_Five.class);
                            inX.putExtra("Email",email);
                            inX.putExtra("Pass",Pass);
                            startActivity(inX);
                        }else{
                            Toast.makeText(LogIn_Three.this,
                                    "Login Failed, Invalid Credentials !!! ",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LogIn_Three.this, " "+error.toString(), Toast.LENGTH_LONG).show();
                    }}
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                data.put("Email",email);
                data.put("Pass",Pass);
                return data;
            }
        };
        Volley.newRequestQueue(LogIn_Three.this).add(request);


    }
}