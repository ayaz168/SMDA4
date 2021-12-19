package com.i170014.i170014_i170161_a4;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class groupChatMessage extends AppCompatActivity {
    String SenderEmail,SenderID,GroupId,GroupName;
    userData sender,reciever;
    String Message;
    String ImagePath;
    EditText newMessageTyped;
    RecyclerView recyclerViewShowChat;
    showChatAdapter myAdapter;
    EditText typedMessage;
    ImageView openCamera,sendMessage,currentImage7,openGallery;
    List<messageData> converationTotal;
    TextView currentUserName7;
    String encodedImageString;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_FROM_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        //Connections
        currentUserName7=findViewById(R.id.currentUserName7);
        sendMessage=findViewById(R.id.sendMessage);
        currentImage7=findViewById(R.id.currentUserImage7);
        openGallery=findViewById(R.id.openGallery);
        typedMessage=findViewById(R.id.typedMessage);


        //Getting Intent Data
        Bundle extras = getIntent().getExtras();
        SenderID = extras.getString("SenderID");
        SenderEmail = extras.getString("SenderEmail");
        GroupId = extras.getString("GroupID");
        GroupName = extras.getString("GroupName");

        //Calling Async Message Logic
        getAllUsers();



        currentImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inGX=new Intent(groupChatMessage.this,Home_Five.class);
                inGX.putExtra("UserId",extras.getString("UserId"));
                startActivity(inGX);
            }
        });
    }
    public void putMessageTextInDB(messageData  MSG, userData sender,String GroupID){
        String url="http://192.168.18.12/A4/putGroupMessage.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(groupChatMessage.this, " "+response+" Message Sent", Toast.LENGTH_LONG).show();
                        postNotifications(new notificationModel(sender.getEmail(), "1708c8f4-4894-40b6","You Recieved a New Message From :  "+sender.getFirstName()+" ",MSG.getHour()+" : "+MSG.getMinute()));
                        startActivity(getIntent());

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(groupChatMessage.this, "yeet "+error.toString(), Toast.LENGTH_LONG).show();
                    }}
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                data.put("GroupID",GroupID);
                data.put("UserID",MSG.getSender());
                data.put("MessageHour",MSG.getHour());
                data.put("MessageMin",MSG.getMinute());
                data.put("MessageRead",MSG.getRead());
                if (MSG.getMessageType().equals("TextMessage")) {
                    data.put("MessageType","TextMessage");
                    data.put("MessageText",MSG.getText());

                }else{
                    encodeBitmapImage(MSG.getMessageImage());
                    if(encodedImageString!=null){
                        data.put("MessageType","ImageMessage");
                        data.put("MessageImage",encodedImageString);
                    }
                }
                return data;
            }
        };
        Volley.newRequestQueue(groupChatMessage.this).add(request);


    }
    private String getFilePath(Intent data) {
        String imagePath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] byteOFImage=byteArrayOutputStream.toByteArray();
            String myImaageMessage= Base64.encodeToString(byteOFImage, Base64.DEFAULT);
            Date currentTime = Calendar.getInstance().getTime();
            int hour=currentTime.getHours();
            int min = currentTime.getMinutes();
            messageData MSG=new messageData(
                    "ImageMessage",
                    "slug",
                    sender.Id,
                    sender.Id,
                    String.valueOf(hour),
                    String.valueOf(min),
                    myImaageMessage,
                    "1",
                    "1",
                    1
            );
            Log.d("msg",typedMessage.getText().toString());
            putMessageTextInDB(MSG,sender,"1");
        }else if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Log.d("TAGG","imageUri.getPath()");
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    Log.d("TAGG",imageUri.getPath());
                    // your code for multiple image selection
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        Log.d("TAGG",photo.toString());
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] byteOFImage=byteArrayOutputStream.toByteArray();
                        String myImaageMessage= Base64.encodeToString(byteOFImage, Base64.DEFAULT);
                        Date currentTime = Calendar.getInstance().getTime();
                        int hour=currentTime.getHours();
                        int min = currentTime.getMinutes();
                        messageData MSG=new messageData(
                                "ImageMessage",
                                "slug",
                                sender.Id,
                                sender.Id,
                                String.valueOf(hour),
                                String.valueOf(min),
                                myImaageMessage,
                                "1",
                                "1",
                                1
                        );
                        Log.d("msg",typedMessage.getText().toString());
                        putMessageTextInDB(MSG,sender,GroupId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri uri = data.getData();
                Bitmap photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] byteOFImage=byteArrayOutputStream.toByteArray();
                    String myImaageMessage= Base64.encodeToString(byteOFImage, Base64.DEFAULT);
                    Date currentTime = Calendar.getInstance().getTime();
                    int hour=currentTime.getHours();
                    int min = currentTime.getMinutes();
                    messageData MSG=new messageData(
                            "ImageMessage",
                            "slug",
                            sender.Id,
                            sender.Id,
                            String.valueOf(hour),
                            String.valueOf(min),
                            myImaageMessage,
                            "1",
                            "1",
                            1
                    );
                    Log.d("msg",typedMessage.getText().toString());
                    putMessageTextInDB(MSG,sender,GroupId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    public void postNotifications(notificationModel obj){
        String url="http://192.168.18.12/SMDProject/putMessage.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(groupChatMessage.this, " "+response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(groupChatMessage.this, " "+error.toString(), Toast.LENGTH_LONG).show();
                    }}
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                data.put("Email",obj.getUserEmail());
                data.put("DeviceID",obj.getDeviceID());
                data.put("Message",obj.getMessage());
                data.put("TimeSlot",obj.getTimeSlot());
                return data;
            }
        };
        Volley.newRequestQueue(groupChatMessage.this).add(request);


    }
    public void updateAdapter(){
        showChatAdapter myAdapter;
        recyclerViewShowChat=findViewById(R.id.recyclerViewShowChat);
        RecyclerView.LayoutManager myManager=new LinearLayoutManager(groupChatMessage.this);
        recyclerViewShowChat.setLayoutManager(myManager);
        //myAdapter=new showChatAdapter(chatMessage.this,converationTotal,currentUser,reciever.getImage());
        //recyclerViewShowChat.setAdapter(myAdapter);
    }
    void putPictuterMessageinDB(String ImageMsg){

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
        }else if(requestCode == PICK_FROM_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);

    }
    messageData compareTime(messageData msg1,messageData msg2){

        Integer.parseInt("200");
        int hour1,hour2;
        int minute1,minute2;
        hour1=Integer.parseInt(msg1.getHour());
        hour2=Integer.parseInt(msg2.getHour());
        minute1=Integer.parseInt(msg1.getMinute());
        minute2=Integer.parseInt(msg1.getMinute());
        if(hour1<hour2){
            return msg1;
        }else if(hour1==hour2 && minute1<minute2){
            return  msg1;
        }else{
            return  msg2;
        }

    }
    public void getMessageData(String GroupID){
        String url="http://192.168.18.12/A4/getAllGroupMessasges.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.d("nope",response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(response);
                            if(obj.getInt("success")==1){
                                List<messageData> myMessages=new ArrayList<>();
                                JSONArray allMessages=obj.getJSONArray("messages");
                                for(int i=0;i<allMessages.length();i++){
                                    String MessageType=allMessages.getJSONObject(i).getString("MessageType");
                                    messageData indMessage;
                                    if(MessageType.equals("TextMessage")){
                                        indMessage=new messageData(
                                                "TextMessage",
                                                allMessages.getJSONObject(i).getString("GroupMessageID"),
                                                allMessages.getJSONObject(i).getString("UserID"),
                                                GroupID,
                                                allMessages.getJSONObject(i).getString("MessageHour"),
                                                allMessages.getJSONObject(i).getString("MessageMin"),
                                                allMessages.getJSONObject(i).getString("MessageText"),
                                                allMessages.getJSONObject(i).getString("MessageRead"),
                                                allMessages.getJSONObject(i).getString("MessageRead")
                                        );
                                    }else{
                                        indMessage=new messageData(
                                                "ImageMessage",
                                                allMessages.getJSONObject(i).getString("GroupMessageID"),
                                                allMessages.getJSONObject(i).getString("UserID"),
                                                GroupID,
                                                allMessages.getJSONObject(i).getString("MessageHour"),
                                                allMessages.getJSONObject(i).getString("MessageMin"),
                                                allMessages.getJSONObject(i).getString("MessageImage"),
                                                allMessages.getJSONObject(i).getString("MessageRead"),
                                                allMessages.getJSONObject(i).getString("MessageRead"),
                                                1
                                        );
                                    }
                                    if(indMessage!=null){
                                        myMessages.add(indMessage);
                                    }
                                    Log.d("TAG", String.valueOf(myMessages.size()));
                                    //Print all Messages
                                    RecyclerView recyclerViewShowChat;
                                    showChatAdapter myAdapter;
                                    recyclerViewShowChat=findViewById(R.id.recyclerViewShowChat);
                                    RecyclerView.LayoutManager myManager=new LinearLayoutManager(groupChatMessage.this);
                                    recyclerViewShowChat.setLayoutManager(myManager);
                                    Collections.sort(myMessages);
                                    myAdapter=new showChatAdapter(groupChatMessage.this,myMessages,sender,sender.getImage());
                                    recyclerViewShowChat.setAdapter(myAdapter);
                                    sendMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Message=typedMessage.getText().toString();
                                            Date currentTime = Calendar.getInstance().getTime();
                                            int hour=currentTime.getHours();
                                            int min = currentTime.getMinutes();
                                            messageData MSG=new messageData(
                                                    "TextMessage",
                                                    "slug",
                                                    sender.Id,
                                                    sender.Id,
                                                    String.valueOf(hour),
                                                    String.valueOf(min),
                                                    Message,
                                                    "1",
                                                    "1"
                                            );
                                            Log.d("msg",typedMessage.getText().toString());
                                            putMessageTextInDB(MSG,sender,GroupID);

                                        }
                                    });
                                    openCamera=findViewById(R.id.openCamera);
                                    openCamera.setOnClickListener(new View.OnClickListener() {
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
                                    openGallery.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (ActivityCompat.checkSelfPermission(groupChatMessage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(groupChatMessage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                                            } else {
                                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                                                galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
//                                                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                                                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                                            }

                                        }
                                    });

                                }


                            }
                            else{
                                Log.d("res","No Message Recieved");
                            }
                        } catch (JSONException e) {
                            Log.d("nope",e.toString());

                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(groupChatMessage.this, " "+error.toString(), Toast.LENGTH_LONG).show();
                    }}
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                data.put("GroupID",GroupID);
                return data;
            }
        };
        Volley.newRequestQueue(groupChatMessage.this).add(request);


    }
    public void getAllUsers(){
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
                            obj = new JSONObject(response);
                            if(obj.getInt("success")==1){
                                //checks if success flag in response json is true/1
                                JSONArray allUsers=obj.getJSONArray("users");
                                List<userData> lX=new ArrayList<>();
                                for(int i=0;i<allUsers.length();i++){
                                    userData temp=new userData(
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
                                    if(temp.getEmail().equals(SenderEmail)){
                                        Log.d("SenderX",temp.getFirstName());
                                        sender=temp;
                                    }
                                    currentUserName7.setText(GroupName);

                                }
                                getMessageData(GroupId);

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
        Volley.newRequestQueue(groupChatMessage.this).add(request);
    }
    private void encodeBitmapImage(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] byteOFImage=byteArrayOutputStream.toByteArray();
        encodedImageString= Base64.encodeToString(byteOFImage, Base64.DEFAULT);

    }

}
