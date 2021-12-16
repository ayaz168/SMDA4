package com.i170014.i170014_i170161_a4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class chatMessage extends AppCompatActivity {
    String SenderEmail,SenderID,RecieverName,RecieverID;
    userData sender,reciever;
    String Message;
    String ImagePath;
    EditText newMessageTyped;
    RecyclerView recyclerViewShowChat;
    showChatAdapter myAdapter;
    EditText typedMessage;
    ImageView openCamera,sendMessage,currentImage7,openGallery;
    userData currentUser;
    List<messageData> converationTotal;
    TextView currentUserName7;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_FROM_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        Bundle extras = getIntent().getExtras();
        currentImage7=findViewById(R.id.currentUserImage7);
        openGallery=findViewById(R.id.openGallery);


        SenderID = extras.getString("SenderID");
        RecieverID = extras.getString("RecieverID");
        SenderEmail = extras.getString("SenderEmail");
        RecieverName = extras.getString("RecieverName");
        //Getting Intent Data



        currentUserName7=findViewById(R.id.currentUserName7);
        currentUserName7.setText(reciever.getFirstName()+" "+reciever.getLastName());
        Toast.makeText(chatMessage.this,
                sender.getFirstName()+" --  "+reciever.getFirstName(),
                Toast.LENGTH_LONG).show();
        //converationTotal=getallMessagesBetweenThem(sender.getId(),reciever.getId());
        //List<messageData> RecieverToSender=getallMessagesBetweenThem(reciever.getId(),sender.getId());
        currentUser=sender;
        currentImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inGX=new Intent(chatMessage.this,Home_Five.class);
                inGX.putExtra("UserId",extras.getString("UserId"));
                startActivity(inGX);
            }
        });
        //for(messageData conV: RecieverToSender){
          //  converationTotal.add(conV);
        //}
        Collections.sort(converationTotal);// All The Messages
        sendMessage=findViewById(R.id.sendMessage);
        typedMessage=findViewById(R.id.typedMessage);
        RecyclerView recyclerViewShowChat;
        showChatAdapter myAdapter;
        recyclerViewShowChat=findViewById(R.id.recyclerViewShowChat);
        RecyclerView.LayoutManager myManager=new LinearLayoutManager(chatMessage.this);
        recyclerViewShowChat.setLayoutManager(myManager);
        //myAdapter=new showChatAdapter(chatMessage.this,converationTotal,currentUser,reciever.getImage());
        //recyclerViewShowChat.setAdapter(myAdapter);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message=typedMessage.getText().toString();
                Log.d("msg",typedMessage.getText().toString());
                //putMessageinDB(Message);
                //myAdapter.updateChat(converationTotal);
                //myAdapter.notifyDataSetChanged();

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
                if (ActivityCompat.checkSelfPermission(chatMessage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(chatMessage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }

            }
        });

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
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            putPictuterMessageinDB(imagePath);
            updateAdapter();
            //myAdapter.updateChat(converationTotal);
        }else if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            String imagePath = getFilePath(data);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            putPictuterMessageinDB(imagePath);
            updateAdapter();
        }

    }
    public void updateAdapter(){
        showChatAdapter myAdapter;
        recyclerViewShowChat=findViewById(R.id.recyclerViewShowChat);
        RecyclerView.LayoutManager myManager=new LinearLayoutManager(chatMessage.this);
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

}
