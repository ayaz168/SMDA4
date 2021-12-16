package com.i170014.i170014_i170161_a4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Home_Five extends AppCompatActivity {
    RecyclerView recycleView;
    newUserAdapter myAdapter;
    //List<userData> myFriends;
    //List<userData> listX=new ArrayList();
    ImageView callScreen5,cameraScreen5,chatScreen5,contactsScreen5,writeNewMessgeScreen5,searchScreen5;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    List<userData> allusers;
    List<userData> phoneContacts;
    List<userData> myFriends;
    userData currentUser;
    String Email,Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_five);
        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        Pass = extras.getString("Pass");
        SharedPreferences prefs = this.getSharedPreferences("com.ayazafzal.i170014_i170161_A4", Context.MODE_PRIVATE);
        String email = "email";
        prefs.edit().putString(email, Email).apply();
        allusers=new ArrayList<>();

        Log.d("LGG", String.valueOf(allusers.size()));

        phoneContacts=getPhoneContacts();
        myFriends=getCommonFriends();
        currentUser=getCurrentUser(Email);



        callScreen5=findViewById(R.id.callScreen5);
        cameraScreen5=findViewById(R.id.cameraScreen5);
        chatScreen5=findViewById(R.id.chatScreen5);
        contactsScreen5=findViewById(R.id.contactsScreen5);
        searchScreen5=findViewById(R.id.searchScreen5);
        searchScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_Five.this,
                        "No Search functionality yet.",
                        Toast.LENGTH_LONG).show();
            }
        });
        callScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent inX=new Intent(Home_Five.this,callScreen.class);
                //inX.putExtra("email",currentUser.getEmail());
                //startActivity(inX);
            }
        });
        cameraScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
            }
        });
        contactsScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent inX=new Intent(Home_Five.this,ContactScreen_Seven.class);
                //inX.putExtra("email",currentUser.getEmail());
                //startActivity(inX);
            }
        });
        writeNewMessgeScreen5=findViewById(R.id.writeNewMessgeScreen5);
        writeNewMessgeScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent inGX=new Intent(Home_Five.this,selectFriend.class);
                //inGX.putExtra("email",currentUser.getEmail());
                //startActivity(inGX);
            }
        });
        getAllUsers();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            //Intent inX=new Intent(Home_Five.this,SendImage_Four.class);
            //inX.putExtra("Image",imagePath);
            //inX.putExtra("email",this.value);
            //startActivity(inX);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getFilePath2(Uri selectedImage) {
        String imagePath;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }

    List<userData> getPhoneContacts(){
        return null;
    }
    List<userData> getCommonFriends(){
        return null;
    }
    userData getCurrentUser(String Email){
        //Get Current User Based on Email
        return null;

    }
    public List<userData> getAllUsers(){
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
                                }
                                recycleView=findViewById(R.id.recyclerViewScreen5);
                                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Home_Five.this);
                                recycleView.setLayoutManager(layoutManager);
                                myAdapter=new newUserAdapter(Home_Five.this,lX,Email,Pass);
                                recycleView.setAdapter(myAdapter);
                                //myAdapter.notifyDataSetChanged();
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
        Volley.newRequestQueue(Home_Five.this).add(request);
        return lX;
    }

    @Override
    protected void onResume(){
        super.onResume();
        //myAdapter=new newUserAdapter(Home_Five.this,getAllUsers(),Email,Pass);
        //recycleView.setAdapter(myAdapter);
        //myAdapter.notifyDataSetChanged();
        getAllUsers();

    }
}