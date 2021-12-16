package com.i170014.i170014_i170161_a4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StaticFunctions {
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    public static List<userData>  getAllUsers(Context cX){
        Log.d("listData","Here0");
        String URL="http://192.168.18.12/A4/getUsers.php";
        List<userData> lX=new ArrayList<>();
        StringRequest request=new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj= null;
                        try {
                            Log.d("listData","Here01");
                            obj = new JSONObject(response);
                            Log.d("listData","Here02");
                            if(obj.getInt("success")==1){
                                Log.d("listData","Here1");
                                //checks if success flag in response json is true/1
                                JSONArray allUsers=obj.getJSONArray("users");
                                List<userData> lX=new ArrayList<>();
                                for(int i=0;i<allUsers.length();i++){
                                    Log.d("listData",allUsers.getJSONObject(i).getString("Email"));
                                    lX.add(new userData(
                                            allUsers.getJSONObject(i).getString("ID"),
                                            allUsers.getJSONObject(i).getString("Email"),
                                            allUsers.getJSONObject(i).getString("Pass"),
                                            allUsers.getJSONObject(i).getString("FirstName"),
                                            allUsers.getJSONObject(i).getString("LastName"),
                                            allUsers.getJSONObject(i).getString("Gender"),
                                            allUsers.getJSONObject(i).getString("Bio"),
                                            allUsers.getJSONObject(i).getString("Status"),
                                            allUsers.getJSONObject(i).getString("Phone"),
                                            allUsers.getJSONObject(i).getString("dp")

                                    ));
                                    Log.d("Lg",String.valueOf(lX.size()));
                                }
                            }else{
                                Log.d("Lg","User Load Error");
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Lg",error.toString());
                    }
                }
        );
        Volley.newRequestQueue(cX).add(request);
        Log.d("listData", String.valueOf(lX.size()));
        return lX;
    }
}
