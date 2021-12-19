package com.i170014.i170014_i170161_a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallingScreen extends AppCompatActivity {
    String imagePath;
    CircleImageView personPic;
    ImageView goBack;
    String image,name;
    TextView contactName;
    String recieverEmail,myEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_screen);
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        image = extras.getString("image");
        name = extras.getString("name");
        recieverEmail = extras.getString("email");
        myEmail = extras.getString("myemail");

        contactName=findViewById(R.id.contactName);
        Log.d("name",name);
        contactName.setText(name);
        Bitmap bitmap = BitmapFactory.decodeFile(image);
        personPic=findViewById(R.id.contactPictureRV8);
        personPic.setImageBitmap(bitmap);

        makeCall(recieverEmail,myEmail);


    }

    private void makeCall(String recieverEmail,String callerEmail) {
        //Write Calling code here
    }
    private void makeVideoCall(String recieverEmail,String callerEmail) {
        //Write Video Calling code here
    }
}