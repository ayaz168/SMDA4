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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactScreen_Seven extends AppCompatActivity {
    userData currentUser;
    CircleImageView currentUserImage7;
    ImageView callScreen5,cameraScreen5,chatScreen5,contactsScreen5;
    TextView currentUserName7,editButtonScreen7;
    LinearLayout newContact7,newGroup7;
    List<userData> myFriends;
    RecyclerView recyclerViewScreen7;
    newFriendAdapter myAdapter;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    String Email,Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen_seven);
        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        Pass = extras.getString("Pass");
        chatScreen5=findViewById(R.id.chatScreen5);
        chatScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent inGX=new Intent(ContactScreen_Seven.this,Home_Five.class);
//                inGX.putExtra("UserId",extras.getString("email"));
//                startActivity(inGX);
            }
        });
        callScreen5=findViewById(R.id.callScreen5);
        callScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent inGX=new Intent(ContactScreen_Seven.this,callScreen.class);
//                inGX.putExtra("email",extras.getString("email"));
//                startActivity(inGX);
            }
        });
//        userData currentUser=getCurrentUser(currenntEmail);
//        currentUserImage7=findViewById(R.id.currentUserImage7);
//        Bitmap bitmap = BitmapFactory.decodeFile(currentUser.getImage());
//        currentUserImage7.setImageBitmap(bitmap);
//        currentUserName7=findViewById(R.id.currentUserName7);
//        currentUserName7.setText((currentUser.getFirstName()+" "+currentUser.getLastName()));
//        editButtonScreen7=findViewById(R.id.editButtonScreen7);
//        editButtonScreen7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ContactScreen_Seven.this,
//                        "Edit Profile Functionality wasn't a requirement, RN this is a Logout button",
//                        Toast.LENGTH_LONG).show();
//                logOut();
//                Intent iNX=new Intent(ContactScreen_Seven.this,SplashScreen_One.class);
//                startActivity(iNX);
//            }
//        });
//        myFriends=getAllfriends(currentUser.getId());
//        recyclerViewScreen7=findViewById(R.id.recyclerViewScreen7);
//        RecyclerView.LayoutManager myManager=new LinearLayoutManager(ContactScreen_Seven.this);
//        recyclerViewScreen7.setLayoutManager(myManager);
//        myAdapter=new newFriendAdapter(ContactScreen_Seven.this,myFriends);
//        recyclerViewScreen7.setAdapter(myAdapter);
//        newContact7=findViewById(R.id.newContact7);
//        newContact7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent inG=new Intent(ContactScreen_Seven.this,addFriends_Eight.class);
//                inG.putExtra("ID",currentUser.getId());
//                startActivity(inG);
//            }
//        });
//        cameraScreen5=findViewById(R.id.cameraScreen5);
//        cameraScreen5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
//                } else {
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//        });
        getAllUsers();
    }

    private void logOut() {
        SharedPreferences prefs = this.getSharedPreferences(
                "com.ayazafzal.i170014_i170161", Context.MODE_PRIVATE);
        String email = "email";
        prefs.edit().putString(email, "None").apply();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            //Intent inX=new Intent(ContactScreen_Seven.this,SendImage_Four.class);
            //inX.putExtra("Image",imagePath);
            //inX.putExtra("email",currenntEmail);
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
    public void getAllUsers(){
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
                                    userData Temp=new userData(
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

                                    );
                                    if(Temp.getEmail().equals(Email)){
                                        currentUser=Temp;
                                        break;
                                    }
                                }
                                getAllGroups(currentUser);
                                Log.d("Tagg","Going");

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
        Volley.newRequestQueue(ContactScreen_Seven.this).add(request);
    }
    public void getAllGroups(userData obJ){
        String url="http://192.168.18.12/A4/GetAllGroups.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ContactScreen_Seven.this, " "+response+" Groups Retrieved", Toast.LENGTH_LONG).show();
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(response);
                            if(obj.getInt("success")==1){
                                Log.d("Tagg","hers");
                                List<Group> myGroup=new ArrayList<>();
                                JSONArray allGroups=obj.getJSONArray("groups");
                                for(int i=0;i<allGroups.length();i++){
                                    myGroup.add(new Group(
                                            allGroups.getJSONObject(i).getString("GroupId"),
                                            allGroups.getJSONObject(i).getString("GroupName")
                                            ));
                                }
                                recyclerViewScreen7=findViewById(R.id.recyclerViewScreen7);
                                RecyclerView.LayoutManager myManager=new LinearLayoutManager(ContactScreen_Seven.this);
                                recyclerViewScreen7.setLayoutManager(myManager);
                                myAdapter=new newFriendAdapter(ContactScreen_Seven.this,myGroup,currentUser);
                                recyclerViewScreen7.setAdapter(myAdapter);
                               Log.d("TagGroup", String.valueOf(myGroup.size()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactScreen_Seven.this, " "+error.toString(), Toast.LENGTH_LONG).show();
                    }}
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                data.put("UserID",obJ.getId());
                return data;
            }
        };
        Volley.newRequestQueue(ContactScreen_Seven.this).add(request);

    }
    @Override
    protected void onResume(){
        super.onResume();
//        userData currentUser=getCurrentUser(currenntEmail);
//        myFriends=getAllfriends(currentUser.getId());
//        recyclerViewScreen7=findViewById(R.id.recyclerViewScreen7);
//        RecyclerView.LayoutManager myManager=new LinearLayoutManager(ContactScreen_Seven.this);
//        recyclerViewScreen7.setLayoutManager(myManager);
//        myAdapter=new newFriendAdapter(ContactScreen_Seven.this,myFriends);
//        recyclerViewScreen7.setAdapter(myAdapter);

    }
}